package com.mighty.kora.controller;

import com.mighty.kora.config.auth.dto.SessionUser;
import com.mighty.kora.domain.user.RegistrationId;
import com.mighty.kora.domain.user.Role;
import com.mighty.kora.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    private final User user_GUEST_GOOGLE = User.builder()
            .authKey("authKey")
            .email("email@email.com")
            .nickname("nickName")
            .password("password")
            .picture("picture")
            .registrationId(RegistrationId.GOOGLE)
            .role(Role.GUEST)
            .build();

    private final User user_GUEST_KORA = User.builder()
            .authKey("authKey")
            .email("email@email.com")
            .nickname("nickName")
            .password("password")
            .picture("picture")
            .registrationId(RegistrationId.KORA)
            .role(Role.GUEST)
            .build();

    private final User user_USER = User.builder()
            .authKey("authKey")
            .email("email@email.com")
            .nickname("nickName")
            .password("password")
            .picture("picture")
            .registrationId(RegistrationId.GOOGLE)
            .role(Role.USER)
            .build();

    @BeforeEach
    public void cleanup() {
    }

    @Test
    @DisplayName("로그인페이지 정상 조회")
    public void login_page_success() throws Exception{

        //then

        mockMvc.perform(get("/signin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Kora에 로그인하세요!")));
    }



    @Test
    @DisplayName("로그인페이지 리다이렉트")
    @WithMockUser(roles="GUEST")
    public void login_page_redirect() throws Exception{

        //when
        ResultActions actions = mockMvc.perform(get("/signin").sessionAttr("user", new SessionUser(user_GUEST_GOOGLE)));

        //then
        actions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("KORA유저 2차인증 페이지")
    @WithMockUser(roles="GUEST")
    public void signup_auth_kora() throws Exception {

        //when
        ResultActions actions = mockMvc.perform(get("/signup_auth").sessionAttr("user", new SessionUser(user_GUEST_KORA)));

        //then
        actions.andExpect(status().isOk())
                .andExpect(content().string(containsString("아직 이메일인증이 완료되지 않았습니다")));
    }

    @Test
    @DisplayName("GOOGLE유저 2차인증 페이지")
    @WithMockUser(roles="GUEST")
    public void signup_auth_google() throws Exception {

        //when
        ResultActions actions = mockMvc.perform(get("/signup_auth").sessionAttr("user", new SessionUser(user_GUEST_GOOGLE)));

        //then
        actions.andExpect(status().isOk())
                .andExpect(content().string(containsString("과정이 얼마 남지 않았어요!")));
    }
}