package com.nodo.retotecnico.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Order(-1)
public class    AuthRateLimitFilter extends OncePerRequestFilter implements Ordered {

    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final int MAX_REGISTER_ATTEMPTS = 3;
    private static final long LOGIN_WINDOW_MS = 5 * 60 * 1000L;
    private static final long REGISTER_WINDOW_MS = 10 * 60 * 1000L;
    private static final int MAX_FORGOT_PASSWORD_ATTEMPTS = 3;
    private static final long FORGOT_PASSWORD_WINDOW_MS = 10 * 60 * 1000L;

    @Value("${rate-limit.enabled:true}")
    private boolean enabled;

    private final ConcurrentHashMap<String, RateLimitEntry> loginAttempts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, RateLimitEntry> registerAttempts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, RateLimitEntry> forgotPasswordAttempts = new ConcurrentHashMap<>();

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (!enabled) {
            filterChain.doFilter(request, response);
            return;
        }

        String path = request.getServletPath();
        String clientIp = getClientIp(request);

        if (path.equals("/api/auth/login")) {
            if (isRateLimited(clientIp, loginAttempts, MAX_LOGIN_ATTEMPTS, LOGIN_WINDOW_MS)) {
                writeTooManyRequests(response, "Demasiados intentos de login. Intenta de nuevo en 5 minutos.");
                return;
            }
            recordAttempt(clientIp, loginAttempts);
        } else if (path.equals("/api/auth/register") || path.equals("/api/auth/beta/register")) {
            if (isRateLimited(clientIp, registerAttempts, MAX_REGISTER_ATTEMPTS, REGISTER_WINDOW_MS)) {
                writeTooManyRequests(response, "Demasiados intentos de registro. Intenta de nuevo en 10 minutos.");
                return;
            }
            recordAttempt(clientIp, registerAttempts);
        } else if (path.equals("/api/auth/forgot-password")) {
            if (isRateLimited(clientIp, forgotPasswordAttempts, MAX_FORGOT_PASSWORD_ATTEMPTS, FORGOT_PASSWORD_WINDOW_MS)) {
                writeTooManyRequests(response, "Demasiadas solicitudes de recuperación. Intenta de nuevo en 10 minutos.");
                return;
            }
            recordAttempt(clientIp, forgotPasswordAttempts);
        }
        cleanupExpiredEntries(loginAttempts, LOGIN_WINDOW_MS);
        cleanupExpiredEntries(registerAttempts, REGISTER_WINDOW_MS);
        cleanupExpiredEntries(forgotPasswordAttempts, FORGOT_PASSWORD_WINDOW_MS);
        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    private boolean isRateLimited(String clientIp,
                                  ConcurrentHashMap<String, RateLimitEntry> store,
                                  int maxAttempts,
                                  long windowMs) {
        RateLimitEntry entry = store.get(clientIp);
        if (entry == null) {
            return false;
        }
        if (System.currentTimeMillis() - entry.windowStart > windowMs) {
            store.remove(clientIp);
            return false;
        }
        return entry.count.get() >= maxAttempts;
    }

    private void recordAttempt(String clientIp, ConcurrentHashMap<String, RateLimitEntry> store) {
        store.compute(clientIp, (key, existing) -> {
            if (existing == null || System.currentTimeMillis() - existing.windowStart > LOGIN_WINDOW_MS) {
                return new RateLimitEntry();
            }
            existing.count.incrementAndGet();
            return existing;
        });
    }

    private void cleanupExpiredEntries(ConcurrentHashMap<String, RateLimitEntry> store, long windowMs) {
        long now = System.currentTimeMillis();
        store.entrySet().removeIf(entry -> now - entry.getValue().windowStart > windowMs);
    }

    private void writeTooManyRequests(HttpServletResponse response, String message) throws IOException {
        response.setStatus(429);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(String.format(
                "{\"status\":429,\"error\":\"Too Many Requests\",\"message\":\"%s\",\"timestamp\":\"%s\"}",
                message, Instant.now().toString()));
    }

    private static class RateLimitEntry {
        final AtomicInteger count = new AtomicInteger(1);
        final long windowStart = System.currentTimeMillis();
    }
}
