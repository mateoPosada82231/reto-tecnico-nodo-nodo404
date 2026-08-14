package com.nodo.retotecnico.serviceImpl;

import com.nodo.retotecnico.dto.ExtensionPurchaseStatsDTO;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.repositories.BuysRepository;
import com.nodo.retotecnico.repositories.ExtensionsRepository;
import com.nodo.retotecnico.repositories.UsersRepository;
import com.nodo.retotecnico.services.AdminService;
import com.nodo.retotecnico.services.EmailService;
import com.nodo.retotecnico.services.ExtensionsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UsersRepository usersRepository;
    private final BuysRepository buysRepository;
    private final ExtensionsRepository extensionsRepository;
    private final EmailService emailService;

    public AdminServiceImpl(UsersRepository usersRepository,
                            BuysRepository buysRepository,
                            ExtensionsRepository extensionsRepository,
                            EmailService emailService) {
        this.usersRepository = usersRepository;
        this.buysRepository = buysRepository;
        this.extensionsRepository = extensionsRepository;
        this.emailService = emailService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Users> getBetaUsers() {
        return usersRepository.findByBetaTesterTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExtensionPurchaseStatsDTO> getExtensionPurchaseStats() {
        return buysRepository.countByExtension().stream()
                .map(row -> {
                    Integer extId = ((Number) row[0]).intValue();
                    long count = ((Number) row[1]).longValue();
                    Optional<Extensions> ext = extensionsRepository.findById(extId);
                    return ext.map(e -> new ExtensionPurchaseStatsDTO(
                            e.getId(),
                            e.getName(),
                            e.getImage(),
                            e.isPublic(),
                            count,
                            ExtensionsService.buildSearchText(e)
                    )).orElse(null);
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Users promoteUser(String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        user.setAdmin(true);
        return usersRepository.save(user);
    }

    @Override
    @Transactional
    public void broadcastToBetaTesters(String subject, String body) {
        List<Users> betas = usersRepository.findByBetaTesterTrue();
        for (Users u : betas) {
            emailService.sendBroadcastEmail(u.getEmail(), subject, body);
        }
    }
}