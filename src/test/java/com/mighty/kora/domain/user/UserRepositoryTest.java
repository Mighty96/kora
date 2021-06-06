package com.mighty.kora.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void User_등록_정상() {

        //given
        String email = "email@email.com";
        String password = "pwd123456";

        userRepository.save(User.builder()
                .email(email)
                .password(password)
                .familyName("abc")
                .givenName("def")
                .birthday("123123")
                .nickname("mighty")
                .build());

        //when
        List<User> userList = userRepository.findAll();

        //then
        User user = userList.get(0);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
    }

    @Test
    public void User_등록_이메일_유효성검사_실패() {
        //given
        String email = "email.com";
        String password = "pwd123456";



        //when, then
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                userRepository.save(User.builder()
                        .email(email)
                        .password(password)
                        .familyName("abc")
                        .givenName("def")
                        .birthday("123123")
                        .nickname("mighty")
                        .build()));

    }

    @Test
    public void User_등록_비밀번호_유효성검사_실패() {
        //given
        String email = "email@email.com";
        String password = "pwdpwd";



        //when, then
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                userRepository.save(User.builder()
                        .email(email)
                        .password(password)
                        .familyName("abc")
                        .givenName("def")
                        .birthday("123123")
                        .nickname("mighty")
                        .build()));
    }
}
