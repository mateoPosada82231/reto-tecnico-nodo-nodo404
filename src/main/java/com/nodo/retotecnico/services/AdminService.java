package com.nodo.retotecnico.services;

import com.nodo.retotecnico.dto.ExtensionPurchaseStatsDTO;
import com.nodo.retotecnico.models.Users;
import java.util.List;

public interface AdminService {
    List<Users> getBetaUsers();
    List<ExtensionPurchaseStatsDTO> getExtensionPurchaseStats();
    Users promoteUser(String email);
    void broadcastToBetaTesters(String subject, String body);
}