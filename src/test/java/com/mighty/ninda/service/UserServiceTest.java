package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.user.SaveUser;
import com.mighty.ninda.dto.user.UserEmail;
import com.mighty.ninda.dto.user.UserNickname;
import com.mighty.ninda.exception.InvalidValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private MailSendService mailSendService;

    private User user;

    private final String EMAIL = "test@test.test";
    private final String NICKNAME = "nickname";
    private final String INTRODUCTION = "안녕하세요.";
    private final String PASSWORD = "password1234";
    private final Role ROLE_ADMIN = Role.ADMIN;
    private final Role ROLE_GUEST = Role.GUEST;
    private final Role ROLE_USER = Role.USER;
    private final RegistrationId REGISTRATION_ID_NINDA = RegistrationId.NINDA;
    private final RegistrationId REGISTRATION_ID_GOOGLE = RegistrationId.GOOGLE;
    private final RegistrationId REGISTRATION_ID_KAKAO = RegistrationId.KAKAO;
    private final String AUTH_KEY = "asdf1234";
    private final LocalDate REGISTRATION_DATE = LocalDate.now();

    @BeforeEach
    void setup() throws Exception {
        user = User.builder()
                .email(EMAIL)
                .nickname(NICKNAME)
                .introduction(INTRODUCTION)
                .password(passwordEncoder.encode(PASSWORD))
                .role(ROLE_USER)
                .registrationId(REGISTRATION_ID_NINDA)
                .authKey(AUTH_KEY)
                .registrationDate(REGISTRATION_DATE)
                .build();
    }

    @DisplayName("유저 저장 - 성공")
    @Test
    void save_Success() {

        //given
        SaveUser saveUser = SaveUser.builder()
                .nickname(NICKNAME)
                .password(PASSWORD)
                .email(EMAIL)
                .build();

        //when
        userService.save(saveUser);


        //then
        verify(mailSendService).sendAuthMail(any(String.class));
        verify(userRepository).save(any(User.class));
    }

    @DisplayName("이메일 중복체크 - 통과")
    @Test
    void emailDuplicateChk_Success() {
        //given
        UserEmail userEmail = UserEmail.builder()
                .email("fake@test.test")
                .build();

        String savedEmail = "test@test.test";

        if (userEmail.getEmail().equals(savedEmail)) {
            given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        } else {
            given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        }

        //when
        String result = userService.emailDuplicateChk(userEmail);

        //then
        assertThat(result).isEqualTo("success");
    }

    @DisplayName("이메일 중복체크 - 실패")
    @Test
    void emailDuplicateChk_Failure() {
        //given
        UserEmail userEmail = UserEmail.builder()
                .email("test@test.test")
                .build();

        String savedEmail = "test@test.test";

        if (userEmail.getEmail().equals(savedEmail)) {
            given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        } else {
            given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        }



        //when
        String result = userService.emailDuplicateChk(userEmail);

        //then
        assertThat(result).isEqualTo("fail");
    }

    @DisplayName("닉네임 중복체크 - 통과")
    @Test
    void nicknameDuplicateChk_Success() {
        //given
        UserNickname userNickname = UserNickname.builder()
                .nickname("fakeNickname")
                .build();

        String savedNickname = "nickname";

        if (userNickname.getNickname().equals(savedNickname)) {
            given(userRepository.findByNickname(anyString())).willReturn(Optional.of(user));
        } else {
            given(userRepository.findByNickname(anyString())).willReturn(Optional.empty());
        }

        //when
        String result = userService.nicknameDuplicateChk(userNickname);

        //then
        assertThat(result).isEqualTo("success");
    }

    @DisplayName("닉네임 중복체크 - 실패")
    @Test
    void nicknameDuplicateChk_Failure() {
        //given
        UserNickname userNickname = UserNickname.builder()
                .nickname("nickname")
                .build();

        String savedNickname = "nickname";

        if (userNickname.getNickname().equals(savedNickname)) {
            given(userRepository.findByNickname(anyString())).willReturn(Optional.of(user));
        } else {
            given(userRepository.findByNickname(anyString())).willReturn(Optional.empty());
        }

        //when
        String result = userService.nicknameDuplicateChk(userNickname);

        //then
        assertThat(result).isEqualTo("fail");
    }

    @DisplayName("이메일 인증 - 유효하지 않은 주소")
    @Test
    void authConfirm_NotCorrect() {
        //given
        String email = "test@test.test";
        String authKey = "fakeAuthKey";
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        //when
        String message = userService.authConfirm(email, authKey);

        //then
        assertThat(message).isEqualTo("유효하지 않은 주소입니다.");

    }

    @DisplayName("이메일 인증 - 이미 완료된 계정")
    @Test
    void authConfirm_Already() {
        //given
        String email = "test@test.test";
        String authKey = AUTH_KEY;
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        //when
        String message = userService.authConfirm(email, authKey);

        //then
        assertThat(message).isEqualTo("이미 인증이 완료된 계정입니다.");

    }

    @DisplayName("이메일 인증 - 성공")
    @Test
    void authConfirm_Success() {
        //given
        User newUser = User.builder()
                .email(EMAIL)
                .nickname(NICKNAME)
                .introduction(INTRODUCTION)
                .password(passwordEncoder.encode(PASSWORD))
                .role(ROLE_GUEST)
                .registrationId(REGISTRATION_ID_NINDA)
                .authKey(AUTH_KEY)
                .registrationDate(REGISTRATION_DATE)
                .build();

        String email = "test@test.test";
        String authKey = AUTH_KEY;
        given(userRepository.findByEmail(email)).willReturn(Optional.of(newUser));

        //when
        String message = userService.authConfirm(email, authKey);

        //then
        assertThat(message).isEqualTo("이제 모든 서비스를 이용하실 수 있습니다. 다시 로그인해주세요.");

    }

    @DisplayName("이메일 인증메일 재발송 - 성공")
    @Test
    void resendAuthMail_Success() {
        //given
        CurrentUser currentUser = CurrentUser.builder()
                .id(1L)
                .email(EMAIL)
                .nickname(NICKNAME)
                .registrationId(REGISTRATION_ID_NINDA)
                .role(ROLE_GUEST)
                .build();

        String email = "test@test.test";
        String newAuthKey = "newAuthKey";

        given(mailSendService.sendAuthMail(email)).willReturn(newAuthKey);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        //when
        userService.resendAuthMail(currentUser);

        //then
        assertThat(user.getAuthKey()).isNotEqualTo(AUTH_KEY);
    }

    @DisplayName("비밀번호 찾기 실패 - Ninda유저가 아님")
    @Test
    void sendNewPassword_NotNindaUser() {
        //given
        User googleUser = User.builder()
                .email(EMAIL)
                .nickname(NICKNAME)
                .introduction(INTRODUCTION)
                .password(passwordEncoder.encode(PASSWORD))
                .role(ROLE_USER)
                .registrationId(REGISTRATION_ID_GOOGLE)
                .authKey(AUTH_KEY)
                .registrationDate(REGISTRATION_DATE)
                .build();

        String googleEmail = "test@test.test";

        given(userRepository.findByEmail(googleEmail)).willReturn(Optional.of(googleUser));

        //when, then
        assertThrows(InvalidValueException.class, () ->
                userService.sendNewPassword(googleEmail));
    }

    @DisplayName("비밀번호 찾기 - 성공")
    @Test
    void sendNewPassword_Success() {
        //given
        String email = "test@test.test";

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(mailSendService.sendNewPassword(email)).willReturn("newPassword");

        //when
        userService.sendNewPassword(email);

        //then
        assertThat(passwordEncoder.matches("newPassword", user.getPassword())).isTrue();
    }

    @DisplayName("비밀번호 재설정 - 비밀번호 틀림")
    @Test
    void changePassword_Failure() {
        //given
        CurrentUser currentUser = CurrentUser.builder()
                .id(1L)
                .email(EMAIL)
                .nickname(NICKNAME)
                .registrationId(REGISTRATION_ID_NINDA)
                .role(ROLE_GUEST)
                .build();
        String incorrectPassword = "incorrectPassword";
        String newPassword = "newPassword";

        given(userRepository.findById(any())).willReturn(Optional.of(user));

        //when, then
        assertThrows(InvalidValueException.class, () ->
                userService.changePassword(currentUser, incorrectPassword, newPassword));

    }

    @DisplayName("비밀번호 재설정 - 성공")
    @Test
    void changePassword_Success() {
        //given
        CurrentUser currentUser = CurrentUser.builder()
                .id(1L)
                .email(EMAIL)
                .nickname(NICKNAME)
                .registrationId(REGISTRATION_ID_NINDA)
                .role(ROLE_USER)
                .build();
        String oldPassword = PASSWORD;
        String newPassword = "newPassword";

        given(userRepository.findById(any())).willReturn(Optional.of(user));

        //when
        userService.changePassword(currentUser, oldPassword, newPassword);

        //then
        assertThat(passwordEncoder.matches(newPassword, user.getPassword())).isTrue();

    }
}
