package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.SessionUser;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.user.*;
import com.mighty.ninda.exception.EntityNotFoundException;
import com.mighty.ninda.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
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
    public void save(SaveUser requestDto) {

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .nickname(requestDto.getNickname())
                .registrationId(RegistrationId.NINDA)
                .introduction("안녕하세요.")
                .registrationDate(LocalDate.now())
                .role(Role.GUEST)
                .authKey(null)
                .build();
        userRepository.save(user);

        //인증메일발송
        String authKey = mailSendService.sendAuthMail(user.getEmail());
        user.updateAuthKey(authKey);
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
    public void update(SessionUser sessionUser, UpdateUser requestDto) {
        User user = findById(sessionUser.getId());

        user.update(requestDto.getNickname(), requestDto.getIntroduction());
        httpSession.setAttribute("user", new SessionUser(user));
    }

    @Transactional
    public void oauthUpdate(SessionUser sessionUser, SaveUserOauth requestDto) {
        User user = findByEmail(sessionUser.getEmail());

        user.update(requestDto.getNickname(), user.getIntroduction());
        user.updateRoleToUser();
        httpSession.setAttribute("user", new SessionUser(user));
    }

    public User findById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User가 존재하지 않습니다. id = " + userId));
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User가 존재하지 않습니다. id = " + email));
    }

    @Transactional
    public void authConfirm(String email, String authKey, Model model) {
        User user = findByEmail(email);

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
    public void resendAuthMail(SessionUser sessionUser) {

        User user = findByEmail(sessionUser.getEmail());

        String newAuthKey = mailSendService.sendAuthMail(user.getEmail());
        user.updateAuthKey(newAuthKey);
    }

    @Transactional
    public void sendNewPassword(String email) {
        User user = findByEmail(email);

        if (user.getRegistrationId() != RegistrationId.NINDA) {
            throw new InvalidValueException("Ninda의 계정이 아닙니다. 가입하신 연동 계정으로 로그인해주세요.");
        }

        String newPassword = mailSendService.sendNewPassword(email);

        user.updatePassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void changePassword(SessionUser sessionUser, String oldPassword, String newPassword) {
        User user = findById(sessionUser.getId());

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidValueException("비밀번호가 올바르지 않습니다.");
        } else {
            user.updatePassword(passwordEncoder.encode(newPassword));
            httpSession.removeAttribute("user");
        }
    }
}
