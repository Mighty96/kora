package com.mighty.kora.domain.post;

import com.mighty.kora.domain.Language;
import com.mighty.kora.domain.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @AfterEach
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    public void 게시글_저장_불러오기() {

        //given
        String title = "제목";
        String content = "내용";

        postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .user(null)
                .myLanguage(Language.KOREAN)
                .yourLanguage(Language.JAPANESE)
                .build());

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

}
