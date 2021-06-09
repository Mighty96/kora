package com.mighty.kora.service;

import com.mighty.kora.config.auth.dto.SessionUser;
import com.mighty.kora.domain.user.RegistrationId;
import com.mighty.kora.domain.user.Role;
import com.mighty.kora.domain.user.User;
import com.mighty.kora.domain.user.UserRepository;
import com.mighty.kora.dto.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final PasswordEncoder passwordEncoder;
    private final MailSendService mailSendService;

    @Transactional
    public Long save(UserSaveRequestDto requestDto) {

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            return 0L;
        }

        //비밀번호 암호화
        User user = UserSaveRequestDto.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .birthday(requestDto.getBirthday())
                .familyName(requestDto.getFamilyName())
                .givenName(requestDto.getGivenName())
                .nickname(requestDto.getNickname())
                .picture(requestDto.getPicture())
                .registrationId(RegistrationId.KORA)
                .authKey(null)
                .build()
                .toEntity();

        userRepository.save(user);

        //인증메일발송
        String authKey = mailSendService.sendAuthMail(user.getEmail());
        user.updateAuthKey(authKey);

        //세션저장
        httpSession.setAttribute("user", new SessionUser(user));

        return user.getId();
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

    @Transactional
    public void authConfirm(String email, String authKey, Model model) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. email = " + email));

        if (user == null) {
            model.addAttribute("message", "유효하지 않은 주소입니다.");
            return;
        } else if (!user.getAuthKey().equals(authKey)) {
            model.addAttribute("message", "유효하지 않은 주소입니다.");
            return;
        } else if (user.getRole() == Role.USER) {
            model.addAttribute("message", "이미 인증이 완료된 계정입니다.");
            return;
        }

        user.updateRoleToUser();

        model.addAttribute("title", "인증 성공!");
        model.addAttribute("message", "이제 모든 서비스를 이용하실 수 있습니다. 다시 로그인해주세요.");
    }

    @Transactional
    public Long resendAuthMail(SessionUser sessionUser) {

        User user = userRepository.findByEmail(sessionUser.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. email = " + sessionUser.getEmail()));

        String newAuthKey = mailSendService.sendAuthMail(user.getEmail());
        user.updateAuthKey(newAuthKey);

        return user.getId();
    }

}
