package com.mighty.ninda.config.auth;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.config.auth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Optional<User> findUser = userRepository.findByEmail(attributes.getEmail());

        User user = findUser.orElseGet(() -> save(attributes));

        Map<String, Object> newAttributes = new HashMap<>();
        newAttributes.put("sub", attributes.getAttributes().get("sub"));
        newAttributes.put("id", user.getId());
        newAttributes.put("email", user.getEmail());
        newAttributes.put("registrationId", user.getRegistrationId());
        newAttributes.put("nickname", user.getNickname());

        httpSession.setAttribute("name", user.getNickname());
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                newAttributes,
                attributes.getNameAttributeKey());
    }

    private User save(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
