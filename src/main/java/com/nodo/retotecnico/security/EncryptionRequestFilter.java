package com.nodo.retotecnico.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@Order(0)
@RequiredArgsConstructor
public class EncryptionRequestFilter extends OncePerRequestFilter implements Ordered {

    private static final Logger log = LoggerFactory.getLogger(EncryptionRequestFilter.class);
    private static final String ENCRYPTED_PAYLOAD_HEADER = "X-Encrypted-Payload";

    private final EncryptionUtils encryptionUtils;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/oauth2/")
                || path.startsWith("/login/oauth2/")
                || path.equals("/error");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String encryptedPayload = request.getHeader(ENCRYPTED_PAYLOAD_HEADER);

        if (encryptedPayload != null && !encryptedPayload.isBlank()) {
            try {
                String decryptedBody = encryptionUtils.decrypt(encryptedPayload);
                encryptionUtils.validatePayloadSize(decryptedBody.length());

                CachedBodyHttpServletRequest wrappedRequest =
                        new CachedBodyHttpServletRequest(request, decryptedBody.getBytes(StandardCharsets.UTF_8));

                filterChain.doFilter(wrappedRequest, response);
            } catch (Exception e) {
                String preview = encryptedPayload.length() > 8
                        ? encryptedPayload.substring(0, 8) + "..."
                        : encryptedPayload;
                log.warn("Failed to decrypt request payload for path: {} - headerLen={} preview={} - {}",
                        request.getServletPath(), encryptedPayload.length(), preview, e.getMessage());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"message\":\"Invalid encrypted payload\"}");
                return;
            }
        } else {
            log.warn("Missing X-Encrypted-Payload header for path: {} (method: {}), proxied request forwarded without decrypted body",
                    request.getServletPath(), request.getMethod());
            filterChain.doFilter(request, response);
        }
    }

    private static class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

        private final byte[] cachedBody;

        public CachedBodyHttpServletRequest(HttpServletRequest request, byte[] cachedBody) {
            super(request);
            this.cachedBody = cachedBody;
        }

        @Override
        public int getContentLength() {
            return cachedBody.length;
        }

        @Override
        public long getContentLengthLong() {
            return cachedBody.length;
        }

        @Override
        public String getContentType() {
            return "application/json";
        }

        @Override
        public ServletInputStream getInputStream() {
            return new ServletInputStream() {
                private final ByteArrayInputStream inputStream = new ByteArrayInputStream(cachedBody);

                @Override
                public int read() {
                    return inputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return inputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                    // Not supported
                }
            };
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }
    }
}
