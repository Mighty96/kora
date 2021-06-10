package com.mighty.kora.service;

import com.mighty.kora.config.auth.dto.SessionUser;
import com.mighty.kora.domain.user.RegistrationId;
import com.mighty.kora.domain.user.Role;
import com.mighty.kora.domain.user.User;
import com.mighty.kora.domain.user.UserRepository;
import com.mighty.kora.dto.user.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpSession httpSession;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailSendService mailSendService;

    @BeforeEach
    public void repositoryCleanup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 저장")
    void save() {

        //given
        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .email("email@email.com")
                .password("password123")
                .nickname("nickname")
                .picture("picture")
                .givenName("givenName")
                .familyName("familyName")
                .authKey(null)
                .birthday("birthday")
                .registrationId(RegistrationId.KORA)
                .build();

        //mocking
        given(passwordEncoder.encode("password123"))
                .willReturn("password456");
        given(userRepository.save(any()))
                .willReturn(requestDto.toEntity());
        given(userRepository.findById(1L))
                .willReturn(Optional.ofNullable(UserSaveRequestDto.builder()
                        .email("email@email.com")
                        .password("password456")
                        .nickname("nickname")
                        .picture("picture")
                        .givenName("givenName")
                        .familyName("familyName")
                        .authKey("authKey")
                        .birthday("birthday")
                        .registrationId(RegistrationId.KORA)
                        .build()
                        .toEntity()));
        //when
        userService.save(requestDto);
        Optional<User> findUser = userRepository.findById(1L);

        //then
        assertThat(findUser.get().getPassword()).isNotEqualTo("password123");
        assertThat(findUser.get().getAuthKey()).isNotNull();
        assertThat(findUser.get().getEmail()).isEqualTo("email@email.com");
        assertThat(findUser.get().getNickname()).isEqualTo("nickname");
    }

    @Test
    @DisplayName("중복 이메일이 있다")
    void emailDuplicateChk_fail() {
        //given
        UserEmailRequestDto requestDto = UserEmailRequestDto.builder()
                .email("email@email.com")
                .build();

        userRepository.save(User.builder()
        .email("email@email.com")
        .build());

        //when
        String response = userService.emailDuplicateChk(requestDto);

        //then
        assertThat(response).isEqualTo("fail");
    }

    @Test
    @DisplayName("유저 정보 업데이트")
    void update() {

        //given
        User savedUser = userRepository.save(User.builder()
                .email("email@email.com")
                .password("password123")
                .nickname("nickname")
                .picture("picture")
                .givenName("givenName")
                .familyName("familyName")
                .authKey(null)
                .birthday("birthday")
                .registrationId(RegistrationId.KORA)
                .build());

        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
                .nickname("newNickname")
                .password("newPassword123")
                .picture("newPicture")
                .build();

        //when
        userService.update(savedUser.getId(), requestDto);
        Optional<User> updatedUser = userRepository.findById(savedUser.getId());

        //then
        assertThat(updatedUser).isPresent();
        assertThat(savedUser.getNickname()).isNotEqualTo(updatedUser.get().getNickname());
        assertThat(savedUser.getPassword()).isNotEqualTo(updatedUser.get().getPassword());
        assertThat(savedUser.getPicture()).isNotEqualTo(updatedUser.get().getPicture());
    }

    @Test
    @DisplayName("Oauth유저 프로필작성 업데이트")
    void oauthUpdate() {

        //given
        User savedUser = userRepository.save(User.builder()
                .email("email@email.com")
                .password("password123")
                .nickname(null)
                .picture("picture")
                .givenName("givenName")
                .familyName("familyName")
                .authKey(null)
                .birthday(null)
                .registrationId(RegistrationId.KORA)
                .build());

        UserOauthSaveRequestDto requestDto = UserOauthSaveRequestDto.builder()
                .birthday("birthday")
                .nickname("nickname")
                .build();

        //when
        Long updatedId = userService.oauthUpdate(savedUser.getEmail(), requestDto);
        Optional<User> updatedUser = userRepository.findById(updatedId);

        //then
        assertThat(updatedUser.get().getBirthday()).isEqualTo("birthday");
        assertThat(updatedUser.get().getNickname()).isEqualTo("nickname");
    }

    @Test
    @DisplayName("Id로 유저 조회")
    void findById() {

        //given
        User savedUser = userRepository.save(User.builder()
                .email("email@email.com")
                .password("password123")
                .nickname("nickname")
                .picture("picture")
                .givenName("givenName")
                .familyName("familyName")
                .authKey(null)
                .birthday("birthday")
                .registrationId(RegistrationId.KORA)
                .build());

        //when
        UserResponseDto responseDto = userService.findById(savedUser.getId());

        //then
        assertThat(savedUser.getEmail()).isEqualTo(responseDto.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(responseDto.getPassword());
        assertThat(savedUser.getNickname()).isEqualTo(responseDto.getNickname());
    }

    @Test
    @DisplayName("로그인 : 존재하지 않는 이메일")
    void login_email_not_exist() {
        //given
        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .email("email@email.com")
                .password("password123")
                .build();

        //when, then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.login(requestDto);
        });
    }

    @Test
    @DisplayName("로그인 : 비밀번호가 틀리다")
    void login_password_not_correct() {
        //given
        userService.save(UserSaveRequestDto.builder()
                .email("email@email.com")
                .password("password123")
                .build());

        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .email("email@email.com")
                .password("password123123")
                .build();

        //when, then
        assertThrows(IllegalAccessException.class, () -> {
            userService.login(requestDto);
        });
    }

    @Test
    @DisplayName("로그인 : 성공")
    void login_success() {
        //given
        userService.save(UserSaveRequestDto.builder()
                .email("email@email.com")
                .password("password123")
                .build());

        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .email("email@email.com")
                .password("password123")
                .build();

        //when
        Long loginId = userService.login(requestDto);

        //then
        assertThat(loginId).isGreaterThan(0L);
    }

    @Test
    @DisplayName("2차인증 - 존재하지 않는 이메일")
    void authConfirm_email_not_exist(Model model) {

        //given
        String email = "email@email.com";
        String authKey = "authKey";

        //when, then
        assertThrows(IllegalStateException.class, () -> {
            userService.authConfirm(email, authKey, model);
        });
    }

    @Test
    @DisplayName("2차인증 - authKey가 다르다")
    void authConfirm_authKey_not_correct(Model model) {

        //given
        userRepository.save(User.builder()
                .email("email@email.com")
                .password("password123")
                .authKey("authKey")
                .build());


        String email = "email@email.com";
        String authKey = "notCorrectAuthKey";

        //when
        userService.authConfirm(email, authKey, model);

        //then
        assertThat(model.getAttribute("message")).isEqualTo("유효하지 않은 주소입니다.");
    }

    @Test
    @DisplayName("2차인증 - 이미 인증이 완료됐다")
    void authConfirm_not_need(Model model) {

        //given
        userRepository.save(User.builder()
                .email("email@email.com")
                .password("password123")
                .authKey("authKey")
                .build());


        String email = "email@email.com";
        String authKey = "authKey";

        //when
        userService.authConfirm(email, authKey, model);
        userService.authConfirm(email, authKey, model);
        //then
        assertThat(model.getAttribute("message")).isEqualTo("이미 인증이 완료된 계정입니다.");
    }

    @Test
    @DisplayName("2차인증 - 성공")
    void authConfirm_success(Model model) {

        //given
        User savedUser = userRepository.save(User.builder()
                .email("email@email.com")
                .password("password123")
                .authKey("authKey")
                .build());


        String email = "email@email.com";
        String authKey = "authKey";

        //when
        userService.authConfirm(email, authKey, model);
        Optional<User> updatedUser = userRepository.findById(savedUser.getId());

        //then
        assertThat(model.getAttribute("title")).isEqualTo("인증 성공!");
        assertThat(model.getAttribute("message")).isEqualTo("이제 모든 서비스를 이용하실 수 있습니다. 다시 로그인해주세요.");
        assertThat(updatedUser.get().getRole()).isEqualTo(Role.USER);
    }


    @Test
    @DisplayName("인증메일 재전송")
    void resendAuthMail() {

        //given
        User savedUser = userRepository.save(User.builder()
                .email("email@email.com")
                .password("password123")
                .authKey("authKey")
                .nickname("nickname")
                .registrationId(RegistrationId.KORA)
                .role(Role.GUEST)
                .build());

        SessionUser sessionUser = new SessionUser(savedUser);

        //when
        userService.resendAuthMail(sessionUser);
        Optional<User> updatedUser = userRepository.findById(savedUser.getId());

        //then
        assertThat(updatedUser.get().getAuthKey()).isNotEqualTo(savedUser.getAuthKey());
    }
}