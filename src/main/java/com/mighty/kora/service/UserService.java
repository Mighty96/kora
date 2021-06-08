package com.mighty.kora.service;

import com.mighty.kora.config.auth.dto.SessionUser;
import com.mighty.kora.domain.user.RegistrationId;
import com.mighty.kora.domain.user.User;
import com.mighty.kora.domain.user.UserRepository;
import com.mighty.kora.dto.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserSaveRequestDto requestDto) {
        requestDto = UserSaveRequestDto.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .birthday(requestDto.getBirthday())
                .familyName(requestDto.getFamilyName())
                .givenName(requestDto.getGivenName())
                .nickname(requestDto.getNickname())
                .picture(requestDto.getPicture())
                .registrationId(RegistrationId.KORA)
                .build();
        return userRepository.save(requestDto.toEntity()).getId();
    }

    public String emailDuplicateChk(UserEmailRequestDto requestDto) {
        Optional<User> user = userRepository.findByEmail(requestDto.getEmail());

        if (user.isPresent()) {
            return "fail";
        } else {
            return "success";
        }
    }

    @Transactional
    public Long update(Long id, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id = " + id));

        user.update(requestDto.getPassword(), requestDto.getNickname(), requestDto.getUserImg());

        return id;
    }

    @Transactional
    public Long oauthUpdate(String email, UserOauthSaveRequestDto requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. email = " + email));

        user.oauthUpdate(requestDto.getBirthday(), requestDto.getNickname());
        httpSession.setAttribute("user", new SessionUser(user));
        return user.getId();
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id =" + id));

        return new UserResponseDto(user);
    }

    public Long login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. email = " + requestDto.getEmail()));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        } else {
            httpSession.setAttribute("user", new SessionUser(user));
            return user.getId();
        }
    }


}
