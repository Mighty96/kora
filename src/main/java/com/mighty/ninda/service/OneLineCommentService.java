package com.mighty.ninda.service;

import com.mighty.ninda.domain.comment.OneLineComment;
import com.mighty.ninda.domain.comment.OneLineCommentRepository;
import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.game.GameRepository;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.oneLineComment.OneLineCommentSaveRequestDto;
import com.mighty.ninda.dto.oneLineComment.OneLineCommentUpdateRequestDto;
import com.mighty.ninda.dto.user.UserOneLineCommentDto;
import com.mighty.ninda.exception.comment.CommentAlreadyHateException;
import com.mighty.ninda.exception.comment.CommentAlreadyLikeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Long save(Long userId, OneLineCommentSaveRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다. id = " + userId));

        Game game = gameRepository.findById(requestDto.getGameId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다. id = " + requestDto.getGameId()));

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

        return oneLineComment.getId();
    }

    @Transactional
    public List<OneLineComment> findAllOneLineCommentByGameId(Long gameId) {
        return oneLineCommentRepository.findByGame_Id(gameId);
    }

    @Transactional
    public List<UserOneLineCommentDto> findOneLineCommentByUserId(Long userId) {

        return new UserOneLineCommentDto().toList(oneLineCommentRepository.findByUser_IdOrderByIdDesc(userId));
    }


    @Transactional
    public List<OneLineComment> findTop5ByOrderByCreatedDateDesc() {
        return oneLineCommentRepository.findTop5ByOrderByCreatedDateDesc();
    }

    @Transactional
    public Long update(Long userId, Long commentId, OneLineCommentUpdateRequestDto requestDto) {
        OneLineComment comment = oneLineCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("한줄평이 존재하지 않습니다. id = " + commentId));

        if (!userId.equals(comment.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 변경할 수 있습니다.");
        }

        comment.update(requestDto.getContext());

        return commentId;
    }

    @Transactional
    public Long deleteOneLineComment(Long id) {
        OneLineComment comment = oneLineCommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("한줄평이 존재하지 않습니다. id = " + id));

        oneLineCommentRepository.delete(comment);

        return id;
    }

    @Transactional
    public Long reLikeUp(Long userId, Long commentId) {
        OneLineComment comment = oneLineCommentRepository.findById(commentId)
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
        OneLineComment comment = oneLineCommentRepository.findById(commentId)
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
