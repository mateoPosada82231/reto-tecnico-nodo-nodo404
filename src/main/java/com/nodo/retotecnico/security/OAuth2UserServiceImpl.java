package com.nodo.retotecnico.security;

import com.nodo.retotecnico.Models.Users;
import com.nodo.retotecnico.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String providerId = (String) attributes.get("id");

        Users user = usersRepository.findByEmail(email);

        if (user == null) {
            user = new Users();
            user.setEmail(email);
            user.setNombre(name); // ⚠️ puede variar segun el modelo
            user.setProvider("FACEBOOK");
            user.setProviderId(providerId);

            usersRepository.save(user);
        }

        return oAuth2User;
    }
}
