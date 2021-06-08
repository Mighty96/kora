package com.mighty.kora.config.auth.dto;

import com.mighty.kora.domain.user.RegistrationId;
import com.mighty.kora.domain.user.Role;
import com.mighty.kora.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String familyName;
    private String givenName;
    private String email;
    private String picture;
    private RegistrationId registrationId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String familyName, String givenName, String email, String picture, RegistrationId registrationId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.familyName = familyName;
        this.givenName = givenName;
        this.email = email;
        this.picture = picture;
        this.registrationId = registrationId;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .familyName((String) attributes.get("family_name"))
                .givenName((String) attributes.get("given_name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(RegistrationId.GOOGLE)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(null)
                .familyName(familyName)
                .givenName(givenName)
                .picture(picture)
                .birthday(null)
                .nickname(null)
                .picture(picture)
                .role(Role.GUEST)
                .registrationId(registrationId)
                .build();
    }
}
