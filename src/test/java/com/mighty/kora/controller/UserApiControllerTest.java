package com.mighty.kora.controller;

import com.mighty.kora.domain.user.User;
import com.mighty.kora.domain.user.UserRepository;
import com.mighty.kora.dto.user.UserEmailRequestDto;
import com.mighty.kora.dto.user.UserSaveRequestDto;
import com.mighty.kora.dto.user.UserUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void User_등록된다() throws Exception{
        //given
        String email = "email@email.com";
        String password = "pwdpwd123";
        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .email(email)
                .password(password)
                .familyName("abc")
                .givenName("def")
                .birthday("birthday")
                .nickname("mighty")
                .build();

        String url = "http://localhost:" + port + "/api/user";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<User> userList = userRepository.findAll();
        User user = userList.get(0);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);

    }

    @Test
    public void User_수정된다() throws Exception {

        //given
        User savedUser = userRepository.save(User.builder()
                .email("email@email.com")
                .password("pwdpwd123")
                .familyName("abc")
                .givenName("def")
                .birthday("birthday")
                .nickname("nickname")
                .build());

        Long updateId = savedUser.getId();
        String newPassword = "newPassword";
        String newNickname = "newNickname";

        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
                .password(newPassword)
                .nickname(newNickname)
                .build();

        String url = "http://localhost:" + port + "/api/user/" + updateId;

        HttpEntity<UserUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<User> userList = userRepository.findAll();
        User user = userList.get(0);
        assertThat(user.getPassword()).isEqualTo(newPassword);
        assertThat(user.getNickname()).isEqualTo(newNickname);

    }

    @Test
    public void User_Email_중복있음() {
        //given
        User savedUser = userRepository.save(User.builder()
                .email("email@email.com")
                .password("pwdpwd123")
                .familyName("abc")
                .givenName("def")
                .birthday("birthday")
                .nickname("nickname")
                .build());

        String newEmail = "email@email.com";

        UserEmailRequestDto requestDto = UserEmailRequestDto.builder()
                .email(newEmail)
                .build();

        String url = "http://localhost:" + port + "/api/userEmailChk";

        //when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestDto, String.class);

        //given
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("fail");


    }
}