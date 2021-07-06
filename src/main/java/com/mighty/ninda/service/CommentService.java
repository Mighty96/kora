package com.mighty.ninda.service;

import com.mighty.ninda.domain.comment.Comment;
import com.mighty.ninda.domain.comment.CommentRepository;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.post.PostRepository;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.comment.SaveComment;
import com.mighty.ninda.dto.comment.UpdateComment;
import com.mighty.ninda.exception.comment.CommentAlreadyHateException;
import com.mighty.ninda.exception.comment.CommentAlreadyLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long saveComment(Long userId, SaveComment requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다. id = " + userId));

        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다. id = " + requestDto.getPostId()));

        Comment comment = Comment.builder()
                .user(user)
                .context(requestDto.getContext())
                .post(post)
                .reLike(0)
                .reHate(0)
                .likeList("")
                .hateList("")
                .build();

        commentRepository.save(comment);

        return comment.getId();
    }

    @Transactional
    public List<Comment> findAllCommentByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public List<Comment> findCommentByUserIdDesc(Long userId) {

        return commentRepository.findByUserIdOrderByIdDesc(userId);
    }

    @Transactional
    public Long updateComment(Long userId, Long commentId, UpdateComment requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("한줄평이 존재하지 않습니다. id = " + commentId));

        if (!userId.equals(comment.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 변경할 수 있습니다.");
        }

        comment.update(requestDto.getContext());

        return commentId;
    }

    @Transactional
    public Long deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("한줄평이 존재하지 않습니다. id = " + id));

        commentRepository.delete(comment);

        return id;
    }

    @Transactional
    public Long reLikeUp(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("한줄평이 존재하지 않습니다. id = " + commentId));

        String _userId = "[" + userId.toString() + "]";

        if (comment.getLikeList().contains(_userId)) {
            throw new CommentAlreadyLikeException("이미 추천했습니다.");
        } else {
            comment.reLikeUp();
            comment.updateLikeList(_userId);
        }

        return comment.getId();
    }

    @Transactional
    public Long reHateUp(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("한줄평이 존재하지 않습니다. id = " + commentId));

        String _userId = "[" + userId.toString() + "]";

        if (comment.getHateList().contains(_userId)) {
            throw new CommentAlreadyHateException("이미 비추천했습니다.");
        } else {
            comment.reHateUp();
            comment.updateHateList(_userId);
        }

        return comment.getId();
    }
}
