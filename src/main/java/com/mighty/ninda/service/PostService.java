package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.file.Photo;
import com.mighty.ninda.domain.file.PhotoRepository;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.post.PostRepository;
import com.mighty.ninda.domain.post.PostSpecs;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.dto.post.SavePost;
import com.mighty.ninda.dto.post.UpdatePost;
import com.mighty.ninda.exception.EntityNotFoundException;
import com.mighty.ninda.exception.common.HandleAccessDenied;
import com.mighty.ninda.exception.post.PostAlreadyHateException;
import com.mighty.ninda.exception.post.PostAlreadyLikeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;
    private final S3Service s3Service;

    @Transactional
    public Long save(SavePost requestDto, User user) {
        Post post = requestDto.toEntity(user);
        post = parseContextAndMoveImages(post);
        return postRepository.save(post).getId();
    }

    public Post parseContextAndMoveImages(Post post) {
        Document doc = Jsoup.parse(post.getContext());
        String context = post.getContext();
        Elements images = doc.getElementsByTag("img");

        if (images.size() > 0) {
            for (Element image : images) {
                String source = image.attr("src");

                if (!source.contains("/temp/")) {
                    continue;
                }

                source = source.replace("https://ninda-file.s3.ap-northeast-2.amazonaws.com/", "");
                String newSource = LocalDate.now().toString() + "/" + source.split("/")[2];

                context = context.replace(source, newSource);

                s3Service.update(source, newSource);

                try {
                    Photo photo = Photo.builder()
                            .UUID(newSource.split("/")[1].split("_")[0])
                            .fileName(URLDecoder.decode(newSource.split("/")[1].split("_")[1], "UTF-8"))
                            .filePath(newSource.split("/")[0] + "/")
                            .post(post)
                            .build();
                    photoRepository.save(photo);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }

        post.update(post.getTitle(), context);
        return post;
    }



    @Transactional
    public void update(Long postId, UpdatePost requestDto) {
        Post post = findById(postId);
        post.update(requestDto.getTitle(), requestDto.getContext());
        parseContextAndMoveImages(post);
    }

    @Transactional
    public void delete(Long postId) {
        parseContextAndDeleteImages(findById(postId));
        postRepository.deleteById(postId);
    }

    public void parseContextAndDeleteImages(Post post) {
        Document doc = Jsoup.parse(post.getContext());
        Elements images = doc.getElementsByTag("img");
        String source = "";

        if (images.size() > 0) {
            for (Element image : images) {
                try {
                    source = URLDecoder.decode(image.attr("src").replace("https://ninda-file.s3.ap-northeast-2.amazonaws.com/", ""), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                s3Service.delete(source);
            }
        }
    }

    @Transactional
    public Post findById(Long id) {

        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post가 존재하지 않습니다. id = " + id));
    }

    @Transactional
    public List<Post> findTop10ByBoardOrderByCreatedDateDesc(String board) {
        return postRepository.findTop10ByBoardOrderByCreatedDateDesc(board);
    }

    @Transactional
    public Page<Post> findAll(Map<String, Object> searchKeyword, Pageable pageable) {
        return postRepository.findAll(PostSpecs.searchPost(searchKeyword), pageable);
    }

    @Transactional
    public List<Post> findTop5DailyPost(String board) {
        return postRepository.findTop5ByBoardAndCreatedDateAfterOrderByReLikeDesc(board, LocalDateTime.now().minusDays(1));
    }

    @Transactional
    public List<Post> findTop5WeeklyPost(String board) {
        return postRepository.findTop5ByBoardAndCreatedDateAfterOrderByReLikeDesc(board, LocalDateTime.now().minusWeeks(1));
    }

    @Transactional
    public List<Post> findTop5MonthlyPost(String board) {
        return postRepository.findTop5ByBoardAndCreatedDateAfterOrderByReLikeDesc(board, LocalDateTime.now().minusMonths(1));
    }

    @Transactional
    public void viewCountUp(Long postId) {
        Post post = findById(postId);
        post.viewCountUp();
    }

    @Transactional
    public void reLikeUp(CurrentUser currentUser, Long postId) {
        Post post = findById(postId);

        checkAuth(currentUser);

        Long userId = currentUser.getId();

        String _userId = "[" + userId.toString() + "]";

        if (post.getLikeList().contains(_userId)) {
            throw new PostAlreadyLikeException("이미 추천했습니다.");
        } else {
            post.reLikeUp();
            post.updateLikeList(_userId);
        }
    }

    @Transactional
    public void reHateUp(CurrentUser currentUser, Long postId) {
        Post post = findById(postId);

        checkAuth(currentUser);

        Long userId = currentUser.getId();

        String _userId = "[" + userId.toString() + "]";

        if (post.getHateList().contains(_userId)) {
            throw new PostAlreadyHateException("이미 비추천했습니다.");
        } else {
            post.reHateUp();
            post.updateHateList(_userId);
        }
    }

    public void checkAuth(CurrentUser currentUser) {
        if (currentUser == null) {
            throw new HandleAccessDenied("로그인이 필요한 서비스에요.");
        } else if (currentUser.getRole() == Role.GUEST) {
            if (currentUser.getRegistrationId() == RegistrationId.NINDA) {
                throw new HandleAccessDenied("메일인증을 완료해주세요.");
            } else {
                throw new HandleAccessDenied("닉네임을 설정해주세요.");
            }
        }
    }
}
