package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.comment.Comment;
import com.mighty.ninda.domain.comment.CommentRepository;
import com.mighty.ninda.domain.post.Post;
import com.mighty.ninda.domain.post.PostRepository;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.comment.SaveComment;
import com.mighty.ninda.dto.comment.UpdateComment;
import com.mighty.ninda.exception.EntityNotFoundException;
import com.mighty.ninda.exception.comment.CommentAlreadyHateException;
import com.mighty.ninda.exception.comment.CommentAlreadyLikeException;
import com.mighty.ninda.exception.common.HandleAccessDenied;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public void saveComment(CurrentUser currentUser, SaveComment requestDto) {

        checkAuth(currentUser);

        Long userId = currentUser.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User가 존재하지 않습니다. id = " + userId));

        Post post = postRepository.findById(requestDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post가 존재하지 않습니다. id = " + requestDto.getPostId()));


        Comment comment;

        if (requestDto.getCommentId() == 0)
        {
            comment = Comment.builder()
                    .user(user)
                    .context(requestDto.getContext())
                    .post(post)
                    .orders(1)
                    .parent(null)
                    .children(new ArrayList<>())
                    .reLike(0)
                    .reHate(0)
                    .likeList("")
                    .hateList("")
                    .build();
        } else {

            Comment parentComment = findById(requestDto.getCommentId());

            comment = Comment.builder()
                    .user(user)
                    .context(requestDto.getContext())
                    .post(post)
                    .orders(parentComment.getChildren().size() + 1)
                    .parent(parentComment)
                    .children(null)
                    .reLike(0)
                    .reHate(0)
                    .likeList("")
                    .hateList("")
                    .build();
        }

        commentRepository.save(comment);
    }

    @Transactional
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("Comment가 존재하지 않습니다. id = " + commentId));
    }

    @Transactional
    public List<Comment> findByPostIdOrderByParentIdAscOrdersAsc(Long postId) {
        return commentRepository.findByPostIdOrderByParentIdAscOrdersAsc(postId);
    }

    @Transactional
    public List<Comment> findCommentByUserIdDesc(Long userId) {

        return commentRepository.findByUserIdOrderByIdDesc(userId);
    }

    @Transactional
    public void updateComment(CurrentUser currentUser, Long commentId, UpdateComment requestDto) {

        checkAuth(currentUser);

        Long userId = currentUser.getId();
        Comment comment = findById(commentId);

        if (!userId.equals(comment.getUser().getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new HandleAccessDenied("작성자만 변경할 수 있습니다.");
        }

        comment.update(requestDto.getContext());
    }

    @Transactional
    public void deleteComment(CurrentUser currentUser, Long commentId) {

        checkAuth(currentUser);

        Long userId = currentUser.getId();
        Comment comment = findById(commentId);
        if (!userId.equals(comment.getUser().getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new HandleAccessDenied("작성자만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }

    @Transactional
    public void reLikeUp(CurrentUser currentUser, Long commentId) {
        Comment comment = findById(commentId);

        checkAuth(currentUser);

        Long userId = currentUser.getId();

        String _userId = "[" + userId.toString() + "]";

        if (comment.getLikeList().contains(_userId)) {
            throw new CommentAlreadyLikeException("이미 추천했습니다.");
        } else {
            comment.reLikeUp();
            comment.updateLikeList(_userId);
        }
    }

    @Transactional
    public void reHateUp(CurrentUser currentUser, Long commentId) {
        Comment comment = findById(commentId);

        checkAuth(currentUser);

        Long userId = currentUser.getId();

        String _userId = "[" + userId.toString() + "]";

        if (comment.getHateList().contains(_userId)) {
            throw new CommentAlreadyHateException("이미 비추천했습니다.");
        } else {
            comment.reHateUp();
            comment.updateHateList(_userId);
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
