package com.nodo.retotecnico.services;

import java.math.BigDecimal;
import java.util.List;

public interface EmailService {
    void sendWelcomeEmail(String toEmail, String fullName, String type);
    void sendPasswordChangedEmail(String toEmail, String fullName);
    void sendPurchaseEmail(String toEmail, String fullName, List<String> extensionNames, BigDecimal totalPrice);
}