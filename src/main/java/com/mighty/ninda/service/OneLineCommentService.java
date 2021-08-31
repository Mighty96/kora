package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.comment.OneLineComment;
import com.mighty.ninda.domain.comment.OneLineCommentRepository;
import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.game.GameRepository;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.oneLineComment.SaveOneLineComment;
import com.mighty.ninda.dto.oneLineComment.UpdateOneLineComment;
import com.mighty.ninda.exception.EntityNotFoundException;
import com.mighty.ninda.exception.comment.CommentAlreadyHateException;
import com.mighty.ninda.exception.comment.CommentAlreadyLikeException;
import com.mighty.ninda.exception.common.HandleAccessDenied;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OneLineCommentService {

    private final OneLineCommentRepository oneLineCommentRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Transactional
    public void save(CurrentUser currentUser, SaveOneLineComment requestDto) {

        checkAuth(currentUser);

        Long userId = currentUser.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User가 존재하지 않습니다. id = " + userId));

        Game game = gameRepository.findById(requestDto.getGameId())
                .orElseThrow(() -> new EntityNotFoundException("Game이 존재하지 않습니다. id = " + requestDto.getGameId()));

        OneLineComment oneLineComment = OneLineComment.builder()
                .user(user)
                .context(requestDto.getContext())
                .game(game)
                .reLike(0)
                .reHate(0)
                .likeList("")
                .hateList("")
                .build();

        oneLineCommentRepository.save(oneLineComment);
    }

    @Transactional
    public OneLineComment findById(Long oneLineCommentId) {
        return oneLineCommentRepository.findById(oneLineCommentId)
                .orElseThrow(() -> new EntityNotFoundException("OneLineComment가 존재하지 않습니다. id = " + oneLineCommentId));
    }

    @Transactional
    public List<OneLineComment> findAllOneLineCommentByGameId(Long gameId) {
        return oneLineCommentRepository.findByGameId(gameId);
    }

    @Transactional
    public Page<OneLineComment> findOneLineCommentByUserIdDesc(Long userId, Pageable pageable) {

        return oneLineCommentRepository.findByUserIdOrderByIdDesc(userId, pageable);
    }

    @Transactional
    public List<OneLineComment> findTop5ByOrderByCreatedDateDesc() {
        return oneLineCommentRepository.findTop5ByOrderByCreatedDateDesc();
    }

    @Transactional
    public void update(CurrentUser currentUser, Long oneLineCommentId, UpdateOneLineComment requestDto) {

        checkAuth(currentUser);

        Long userId = currentUser.getId();
        OneLineComment oneLineComment = findById(oneLineCommentId);

        if (!userId.equals(oneLineComment.getUser().getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new HandleAccessDenied("작성자만 변경할 수 있습니다.");
        }

        oneLineComment.update(requestDto.getContext());
    }

    @Transactional
    public void deleteOneLineComment(CurrentUser currentUser, Long oneLineCommentId) {

        checkAuth(currentUser);

        Long userId = currentUser.getId();
        OneLineComment oneLineComment = findById(oneLineCommentId);

        if (!userId.equals(oneLineComment.getUser().getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new HandleAccessDenied("작성자만 삭제할 수 있습니다.");
        }

        oneLineCommentRepository.delete(oneLineComment);
    }

    @Transactional
    public void reLikeUp(CurrentUser currentUser, Long oneLineCommentId) {
        OneLineComment comment = findById(oneLineCommentId);

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
    public void reHateUp(CurrentUser currentUser, Long oneLineCommentId) {
        OneLineComment comment = findById(oneLineCommentId);

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
