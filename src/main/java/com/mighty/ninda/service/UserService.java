package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final PasswordEncoder passwordEncoder;
    private final MailSendService mailSendService;

    @Transactional
    public Long save(SaveUser requestDto) {

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .registrationId(RegistrationId.NINDA)
                .introduction("안녕하세요.")
                .role(Role.GUEST)
                .authKey(null)
                .build();
        userRepository.save(user);

        //인증메일발송
        String authKey = mailSendService.sendAuthMail(user.getEmail());
        user.updateAuthKey(authKey);

        //세션저장
        httpSession.setAttribute("user", new SessionUser(user));

        return user.getId();
    }

    public String emailDuplicateChk(UserEmail requestDto) {
        Optional<User> user = userRepository.findByEmail(requestDto.getEmail());

        if (user.isPresent()) {
            return "fail";
        } else {
            return "success";
        }
    }

    @Transactional
    public Long update(Long id, UpdateUser requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id = " + id));

        log.info(requestDto.getNickname());
        log.info(requestDto.getIntroduction());
        user.update(requestDto.getNickname(), requestDto.getIntroduction());
        httpSession.setAttribute("user", new SessionUser(user));
        return id;
    }

    @Transactional
    public Long oauthUpdate(String email, SaveUserOauth requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. email = " + email));

        user.update(requestDto.getNickname(), user.getIntroduction());
        user.updateRoleToUser();
        httpSession.setAttribute("user", new SessionUser(user));
        return user.getId();
    }

    public User findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id =" + id));

        return user;
    }

    @Transactional
    public Long login(UserLogin requestDto) {
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

        if (!user.getAuthKey().equals(authKey)) {
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

    @Transactional
    public Long sendNewPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. email = " + email));

        if (user.getRegistrationId() != RegistrationId.NINDA) {
            throw new IllegalArgumentException("Ninda의 계정이 아닙니다. 가입하신 연동 계정으로 로그인해주세요.");
        }

        String newPassword = mailSendService.sendNewPassword(email);

        user.updatePassword(passwordEncoder.encode(newPassword));

        return user.getId();
    }
}
