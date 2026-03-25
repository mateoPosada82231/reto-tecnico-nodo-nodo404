package com.nodo.retotecnico.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TokenRevocationService {

    private final JwtUtils jwtUtils;
    private final Map<String, Long> revokedTokens = new ConcurrentHashMap<>();

    public void revokeToken(String token) {
        cleanupExpiredRevocations();
        long expiresAtMillis = Instant.now().toEpochMilli() + jwtUtils.getRemainingValidityMillis(token);
        revokedTokens.put(token, expiresAtMillis);
    }

    public boolean isRevoked(String token) {
        cleanupExpiredRevocations();
        Long expiresAtMillis = revokedTokens.get(token);
        if (expiresAtMillis == null) {
            return false;
        }

        if (expiresAtMillis <= Instant.now().toEpochMilli()) {
            revokedTokens.remove(token);
            return false;
        }

        return true;
    }

    private void cleanupExpiredRevocations() {
        long now = Instant.now().toEpochMilli();
        revokedTokens.entrySet().removeIf(entry -> entry.getValue() <= now);
    }
}

