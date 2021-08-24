package com.mighty.ninda.config.auth.dto;

import com.mighty.ninda.config.auth.CustomUserDetails;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@ToString
public class CurrentUser implements Serializable {

    private Long id;
    private String email;
    private String nickname;
    private Role role;
    private RegistrationId registrationId;

    public CurrentUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.registrationId = user.getRegistrationId();
    }

    @Builder
    public CurrentUser(Long id, String email, String nickname, Role role, RegistrationId registrationId) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.registrationId = registrationId;
    }

    public static CurrentUser of(Object principal) {
        CurrentUser currentUser = null;

        if (principal instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) principal;
            currentUser = CurrentUser.builder()
                    .id((Long)customUserDetails.getAttributes().get("id"))
                    .email((String)customUserDetails.getAttributes().get("email"))
                    .nickname((String)customUserDetails.getAttributes().get("nickname"))
                    .registrationId((RegistrationId)customUserDetails.getAttributes().get("registrationId"))
                    .role(getRole(customUserDetails.getAuthorities()))
                    .build();
        }
        if (principal instanceof DefaultOAuth2User) {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) principal;
            currentUser = CurrentUser.builder()
                    .id((Long)defaultOAuth2User.getAttributes().get("id"))
                    .email((String)defaultOAuth2User.getAttributes().get("email"))
                    .nickname((String)defaultOAuth2User.getAttributes().get("nickname"))
                    .registrationId((RegistrationId)defaultOAuth2User.getAttributes().get("registrationId"))
                    .role(getRole(defaultOAuth2User.getAuthorities()))
                    .build();
        }

        return currentUser;
    }

    private static Role getRole(Collection<? extends GrantedAuthority> authorities) {
        String authority = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()).get(0);

        if (authority.equals("ROLE_GUEST")) {
            return Role.GUEST;
        } else if (authority.equals("ROLE_USER")) {
            return Role.USER;
        } else {
            return Role.ADMIN;
        }
    }
}