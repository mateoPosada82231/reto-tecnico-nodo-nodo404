package com.nodo.retotecnico.services;

public interface EmailService {
    void sendWelcomeEmail(String toEmail, String fullName, String type);
}
