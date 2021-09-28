package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.LoginUser;
import com.mighty.ninda.config.auth.dto.CurrentUser;
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

        //인증메일발송
        String authKey = mailSendService.sendAuthMail(user.getEmail());
        user.updateAuthKey(authKey);

        userRepository.save(user);
    }

    public String emailDuplicateChk(UserEmail requestDto) {
        Optional<User> user = userRepository.findByEmail(requestDto.getEmail());

        if (user.isPresent()) {
            return "fail";
        } else {
            return "success";
        }
    }

    public String nicknameDuplicateChk(UserNickname requestDto) {
        Optional<User> user = userRepository.findByNickname(requestDto.getNickname());

        if (user.isPresent()) {
            return "fail";
        } else {
            return "success";
        }
    }

    @Transactional
    public void update(@LoginUser CurrentUser currentUser, UpdateUser requestDto) {
        User user = findById(currentUser.getId());

        user.update(requestDto.getNickname(), requestDto.getIntroduction());
    }

    @Transactional
    public void oauthUpdate(@LoginUser CurrentUser currentUser, SaveUserOauth requestDto) {
        User user = findByEmail(currentUser.getEmail());

        user.update(requestDto.getNickname(), user.getIntroduction());
        user.updateRoleToUser();
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
    public String authConfirm(String email, String authKey) {
        User user = findByEmail(email);

        if (!user.getAuthKey().equals(authKey)) {
            return "유효하지 않은 주소입니다.";
        } else if (user.getRole() == Role.USER) {
            return "이미 인증이 완료된 계정입니다.";
        }
        user.updateRoleToUser();

        return "이제 모든 서비스를 이용하실 수 있습니다. 다시 로그인해주세요.";
    }

    @Transactional
    public void resendAuthMail(@LoginUser CurrentUser currentUser) {

        User user = findByEmail(currentUser.getEmail());

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
    public void changePassword(@LoginUser CurrentUser currentUser, String oldPassword, String newPassword) {
        User user = findById(currentUser.getId());

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidValueException("비밀번호가 올바르지 않습니다.");
        } else {
            user.updatePassword(passwordEncoder.encode(newPassword));
        }
    }
}
