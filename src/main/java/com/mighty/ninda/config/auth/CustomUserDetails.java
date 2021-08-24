package com.mighty.ninda.config.auth;

import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserDetails implements UserDetails {

    private Map<String, Object> attributes = new HashMap<>();
    private Long id;
    private String password;
    private Collection<GrantedAuthority> authorities;


    @Builder
    public CustomUserDetails(Long id, String password, String email, String nickname, Collection<GrantedAuthority> authorities, RegistrationId registrationId) {
        this.id = id;
        this.password = password;
        this.attributes.put("id", id);
        this.attributes.put("email", email);
        this.attributes.put("nickname", nickname);
        this.attributes.put("registrationId", registrationId);
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return (String)attributes.get("email");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
