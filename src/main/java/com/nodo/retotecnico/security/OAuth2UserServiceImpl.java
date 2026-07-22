package com.nodo.retotecnico.security;

import com.nodo.retotecnico.models.AuthProvider;
import com.nodo.retotecnico.models.BetaTester;
import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.repositories.BetaTesterRepository;
import com.nodo.retotecnico.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BetaTesterRepository betaTesterRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String email = readString(attributes, "email");
        if (isBlank(email)) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("missing_email"),
                    "El proveedor OAuth2 no devolvio email."
            );
        }

        String name = extractFullName(attributes);
        String providerId = extractProviderId(attributes, registrationId);
        AuthProvider provider = resolveProvider(registrationId);

        if (isBetaRegistration(registrationId)) {
            handleBetaTester(email, name, providerId, provider);
        } else {
            handleUser(email, name, providerId, provider);
        }

        return oAuth2User;
    }

    private boolean isBetaRegistration(String registrationId) {
        return registrationId != null && registrationId.endsWith("-beta");
    }

    private void handleUser(String email, String name, String providerId, AuthProvider provider) {
        Optional<Users> optionalUser = usersRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            Users user = new Users();
            user.setEmail(email);
            user.setFullName(name);
            user.setProvider(provider);
            user.setProviderId(providerId);
            usersRepository.save(user);
        } else {
            Users existingUser = optionalUser.get();
            if (isBlank(existingUser.getFullName()) && !isBlank(name)) {
                existingUser.setFullName(name);
            }
            if (existingUser.getProvider() == null) {
                existingUser.setProvider(provider);
            }
            if (isBlank(existingUser.getProviderId()) && !isBlank(providerId)) {
                existingUser.setProviderId(providerId);
            }
            usersRepository.save(existingUser);
        }
    }

    private void handleBetaTester(String email, String name, String providerId, AuthProvider provider) {
        Optional<BetaTester> optionalBeta = betaTesterRepository.findByEmail(email);

        if (optionalBeta.isEmpty()) {
            BetaTester betaTester = new BetaTester();
            betaTester.setEmail(email);
            betaTester.setFullName(name);
            betaTester.setProvider(provider);
            betaTester.setProviderId(providerId);
            betaTesterRepository.save(betaTester);
        } else {
            BetaTester existingBeta = optionalBeta.get();
            if (isBlank(existingBeta.getFullName()) && !isBlank(name)) {
                existingBeta.setFullName(name);
            }
            if (existingBeta.getProvider() == null) {
                existingBeta.setProvider(provider);
            }
            if (isBlank(existingBeta.getProviderId()) && !isBlank(providerId)) {
                existingBeta.setProviderId(providerId);
            }
            betaTesterRepository.save(existingBeta);
        }
    }

    private String extractFullName(Map<String, Object> attributes) {
        String fullName = readString(attributes, "name");
        if (!isBlank(fullName)) {
            return fullName;
        }

        String givenName = readString(attributes, "given_name");
        String familyName = readString(attributes, "family_name");
        String combined = ((givenName == null ? "" : givenName) + " " + (familyName == null ? "" : familyName)).trim();
        return isBlank(combined) ? null : combined;
    }

    private String extractProviderId(Map<String, Object> attributes, String registrationId) {
        if ("google".equalsIgnoreCase(registrationId) || "google-beta".equalsIgnoreCase(registrationId)) {
            String sub = readString(attributes, "sub");
            if (!isBlank(sub)) {
                return sub;
            }
        }
        return readString(attributes, "id");
    }


    private String readString(Map<String, Object> attributes, String key) {
        Object value = attributes.get(key);
        return value instanceof String str ? str : null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private AuthProvider resolveProvider(String registrationId) {
        if (registrationId == null) return AuthProvider.FORM;
        String lower = registrationId.toLowerCase();
        if (lower.startsWith("google")) return AuthProvider.GOOGLE;
        if (lower.startsWith("facebook")) return AuthProvider.FACEBOOK;
        return AuthProvider.FORM;
    }
}
