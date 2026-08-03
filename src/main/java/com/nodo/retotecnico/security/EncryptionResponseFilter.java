package com.nodo.retotecnico.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
@Order(3)
@RequiredArgsConstructor
public class EncryptionResponseFilter extends OncePerRequestFilter implements Ordered {

    private static final Logger log = LoggerFactory.getLogger(EncryptionResponseFilter.class);
    private static final String ENCRYPTED_HEADER = "X-Encrypted";
    private static final String ENCRYPTED_PAYLOAD_HEADER = "X-Encrypted-Payload";

    // Solo endpoints con datos sensibles
    private static final Set<String> SENSITIVE_PATH_PREFIXES = Set.of(
            "/api/auth/",
            "/api/users/",
            "/api/cart/",
            "/api/buys/"
    );

    private final EncryptionUtils encryptionUtils;

    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/oauth2/")
                || path.startsWith("/login/oauth2/")
                || path.equals("/error")
                || !isSensitivePath(path);
    }

    private boolean isSensitivePath(String path) {
        return SENSITIVE_PATH_PREFIXES.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String encryptedFlag = request.getHeader(ENCRYPTED_HEADER);

        if (!"true".equalsIgnoreCase(encryptedFlag) || !isSensitivePath(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        BufferedResponseWrapper wrappedResponse = new BufferedResponseWrapper(response);
        filterChain.doFilter(request, wrappedResponse);

        byte[] originalBody = wrappedResponse.getOutputStreamContent();
        int status = wrappedResponse.getStatus();

        // Don't encrypt error responses - return them as plain JSON so the frontend can read the error message
        if (status >= 400) {
            if (originalBody != null && originalBody.length > 0 && !response.isCommitted()) {
                response.reset();
                response.setStatus(status);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getOutputStream().write(originalBody);
                response.getOutputStream().flush();
            }
            return;
        }

        if (originalBody == null || originalBody.length == 0) {
            return;
        }

        try {
            String bodyString = new String(originalBody, StandardCharsets.UTF_8);
            String encryptedBody = encryptionUtils.encrypt(bodyString);

            if (!response.isCommitted()) {
                response.reset();
            }
            response.setHeader(ENCRYPTED_PAYLOAD_HEADER, encryptedBody);
            response.setContentType("application/octet-stream");
            response.setContentLength(encryptedBody.length());

            response.getOutputStream().write(encryptedBody.getBytes(StandardCharsets.UTF_8));
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("Failed to encrypt response for path: {}", request.getServletPath(), e);
            if (!response.isCommitted()) {
                response.reset();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json");
                response.getWriter().write("{\"status\":500,\"error\":\"Internal Server Error\",\"message\":\"Encryption failed\"}");
            }
        }
    }

    private static class BufferedResponseWrapper extends HttpServletResponseWrapper {

        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private PrintWriter writer;

        public BufferedResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return new ServletOutputStream() {
                @Override
                public void write(int b) {
                    outputStream.write(b);
                }

                @Override
                public void write(byte[] b, int off, int len) {
                    outputStream.write(b, off, len);
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener listener) {
                }

                @Override
                public void flush() throws IOException {
                    outputStream.flush();
                }

                @Override
                public void close() throws IOException {
                    outputStream.close();
                }
            };
        }

        @Override
        public PrintWriter getWriter() {
            if (writer == null) {
                writer = new PrintWriter(outputStream, true);
            }
            return writer;
        }

        @Override
        public void flushBuffer() {
        }

        public byte[] getOutputStreamContent() {
            if (writer != null) {
                writer.flush();
            }
            return outputStream.toByteArray();
        }
    }
}
