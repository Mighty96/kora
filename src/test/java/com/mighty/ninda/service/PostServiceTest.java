package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.file.PhotoRepository;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.post.PostRepository;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.dto.post.SavePost;
import com.mighty.ninda.dto.post.UpdatePost;
import com.mighty.ninda.exception.common.HandleAccessDenied;
import com.mighty.ninda.exception.post.PostAlreadyLikeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private S3Service s3Service;

    private Post post;
    private User user;


    private final String TITLE = "title";
    private final String CONTEXT = "context";
    private final String BOARD_FREE = "free";

    private final String EMAIL = "test@test.test";
    private final String NICKNAME = "nickname";
    private final String INTRODUCTION = "안녕하세요.";
    private final String PASSWORD = "password1234";
    private final Role ROLE_GUEST = Role.GUEST;
    private final Role ROLE_USER = Role.USER;
    private final RegistrationId REGISTRATION_ID_NINDA = RegistrationId.NINDA;
    private final RegistrationId REGISTRATION_ID_GOOGLE = RegistrationId.GOOGLE;
    private final String AUTH_KEY = "asdf1234";
    private final LocalDate REGISTRATION_DATE = LocalDate.now();

    @BeforeEach
    void setup() throws Exception {

        user = User.builder()
                .email(EMAIL)
                .nickname(NICKNAME)
                .introduction(INTRODUCTION)
                .password(PASSWORD)
                .authKey(AUTH_KEY)
                .registrationDate(REGISTRATION_DATE)
                .registrationId(REGISTRATION_ID_NINDA)
                .role(ROLE_USER)
                .build();

        post = Post.builder()
                    .board(BOARD_FREE)
                    .title(TITLE)
                    .context(CONTEXT)
                    .user(user)
                    .likeList("")
                    .hateList("")
                    .reHate(0)
                    .reLike(0)
                    .viewCount(0)
                    .build();

    }

    @DisplayName("게시글 저장 - 성공")
    @Test
    void save_Success() {
        //given
        SavePost savePost = SavePost.builder()
                .title(TITLE)
                .context(CONTEXT)
                .board(BOARD_FREE)
                .build();
        given(postRepository.save(any(Post.class))).willReturn(post);

        //when
        postService.save(savePost, user);

        //then
        verify(postRepository).save(any(Post.class));
    }

    @DisplayName("게시글 수정 - 성공")
    @Test
    void update_Success() {
        //given
        String newTitle = "newTitle";
        String newContext = "newContext";
        UpdatePost updatePost = UpdatePost.builder()
                .title(newTitle)
                .context(newContext)
                .build();
        Long postId = 1L;

        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        //when
        postService.update(postId, updatePost);

        //then
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContext()).isEqualTo(newContext);
    }

    @DisplayName("게시글 삭제 - 성공")
    @Test
    void delete_Success() {
        //given
        Long postId = 1L;
        given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));

        //when
        postService.delete(postId);

        //then
        verify(postRepository).deleteById(any(Long.class));
    }

    @DisplayName("조회수 증가 - 성공")
    @Test
    void viewCountUp_Success() {
        //given
        Long postId = 1L;
        given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));

        //when
        postService.viewCountUp(postId);

        //then
        assertThat(post.getViewCount()).isEqualTo(1L);
    }

    @DisplayName("추천 - 성공")
    @Test
    void reLikeUp_Success() {
        //given
        CurrentUser currentUser = CurrentUser.builder()
                .id(1L)
                .email(EMAIL)
                .nickname(NICKNAME)
                .registrationId(REGISTRATION_ID_NINDA)
                .role(ROLE_USER)
                .build();

        Long postId = 1L;

        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        //when
        postService.reLikeUp(currentUser, postId);

        //then
        assertThat(post.getReLike()).isEqualTo(1);
        assertThat(post.getLikeList()).contains("[1]");
    }

    @DisplayName("추천 - 비회원")
    @Test
    void reLikeUp_NotMember() {
        //given
        CurrentUser currentUser = null;

        Long postId = 1L;

        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        //when, then
        assertThrows(HandleAccessDenied.class, () ->
                postService.reLikeUp(currentUser, postId));
    }

    @DisplayName("추천 - 인증하지 않은 사용자")
    @Test
    void reLikeUp_NotAuthentication() {
        //given
        CurrentUser currentUser = CurrentUser.builder()
                .id(1L)
                .email(EMAIL)
                .nickname(NICKNAME)
                .registrationId(REGISTRATION_ID_NINDA)
                .role(ROLE_GUEST)
                .build();

        Long postId = 1L;

        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        //when, then
        assertThrows(HandleAccessDenied.class, () ->
                postService.reLikeUp(currentUser, postId));
    }

    @DisplayName("추천 - 이미 추천")
    @Test
    void reLikeUp_AlreadyLikeUp() {
        //given
        CurrentUser currentUser = CurrentUser.builder()
                .id(1L)
                .email(EMAIL)
                .nickname(NICKNAME)
                .registrationId(REGISTRATION_ID_NINDA)
                .role(ROLE_USER)
                .build();

        Long postId = 1L;

        Post likedPost = Post.builder()
                .board(BOARD_FREE)
                .title(TITLE)
                .context(CONTEXT)
                .user(user)
                .likeList("[1]")
                .hateList("")
                .reHate(0)
                .reLike(1)
                .viewCount(0)
                .build();

        given(postRepository.findById(postId)).willReturn(Optional.of(likedPost));

        //when, then
        assertThrows(PostAlreadyLikeException.class, () ->
                postService.reLikeUp(currentUser, postId));
    }
}
