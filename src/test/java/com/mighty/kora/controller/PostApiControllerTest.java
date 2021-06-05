package com.mighty.kora.controller;

import com.mighty.kora.domain.Language;
import com.mighty.kora.domain.post.Post;
import com.mighty.kora.domain.post.PostRepository;
import com.mighty.kora.dto.post.PostSaveRequestDto;
import com.mighty.kora.dto.post.PostUpdateRequestDto;
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
class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    public void Post_등록된다() throws Exception {
        //given
        String title = "제목";
        String content = "내용";
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("mighty")
                .myLanguage(Language.KOREAN)
                .yourLanguage(Language.JAPANESE)
                .build();

        String url = "http://localhost:" + port + "/api/post";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> post = postRepository.findAll();
        assertThat(post.get(0).getTitle()).isEqualTo(title);
        assertThat(post.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Post_수정된다() throws Exception {
        //given
        Post savedPost = postRepository.save(Post.builder()
                .title("title")
                .content("content")
                .author("author")
                .myLanguage(Language.KOREAN)
                .yourLanguage(Language.JAPANESE)
                .build());

        Long updateId = savedPost.getId();
        String newTitle = "newTitle";
        String newContent = "newContent";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(newTitle)
                .content(newContent)
                .build();

        String url = "http://localhost:" + port + "/api/post/" + updateId;

        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> post = postRepository.findAll();
        assertThat(post.get(0).getTitle()).isEqualTo(newTitle);
        assertThat(post.get(0).getContent()).isEqualTo(newContent);
    }
}