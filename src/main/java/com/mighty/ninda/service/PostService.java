package com.mighty.ninda.service;

import com.mighty.ninda.domain.file.Photo;
import com.mighty.ninda.domain.file.PhotoRepository;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.post.PostRepository;
import com.mighty.ninda.domain.post.PostSpecs;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.dto.post.SavePost;
import com.mighty.ninda.dto.post.UpdatePost;
import com.mighty.ninda.exception.onelinecomment.OneLineCommentAlreadyHateException;
import com.mighty.ninda.exception.onelinecomment.OneLineCommentAlreadyLikeException;
import com.mighty.ninda.utils.S3Uploader;
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
    private final S3Uploader s3Uploader;

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

                s3Uploader.move(source, newSource);

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
    public Long update(Long id, UpdatePost requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        post.update(requestDto.getTitle(), requestDto.getContext());

        parseContextAndMoveImages(post);

        return id;
    }

    @Transactional
    public Long delete(Long id) {

        parseContextAndDeleteImages(postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + "찾을 수 없습니다.")));
        postRepository.deleteById(id);



        return id;
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

                s3Uploader.delete(source);
            }
        }
    }

    @Transactional
    public Post findById (Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return post;
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
    public void viewCountUp(Long id) {
        Post post = findById(id);
        post.viewCountUp();
    }

    @Transactional
    public Long reLikeUp(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임이 없습니다. id = " + postId));

        String _userId = "[" + userId.toString() + "]";

        if (post.getLikeList().contains(_userId)) {
            throw new OneLineCommentAlreadyLikeException("이미 추천했습니다.");
        } else {
            post.reLikeUp();
            post.updateLikeList(_userId);
        }

        return post.getId();
    }

    @Transactional
    public Long reHateUp(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게임이 없습니다. id = " + postId));

        String _userId = "[" + userId.toString() + "]";

        if (post.getHateList().contains(_userId)) {
            throw new OneLineCommentAlreadyHateException("이미 비추천했습니다.");
        } else {
            post.reHateUp();
            post.updateHateList(_userId);
        }


        return post.getId();
    }
}
