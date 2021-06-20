package com.mighty.ninda.service;

import com.mighty.ninda.domain.comment.OneLineComment;
import com.mighty.ninda.domain.comment.OneLineCommentRepository;
import com.mighty.ninda.domain.game.Game;
import com.mighty.ninda.domain.game.GameRepository;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.oneLineComment.OneLineCommentSaveRequestDto;
import com.mighty.ninda.dto.oneLineComment.OneLineCommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OneLineCommentService {

    private final OneLineCommentRepository oneLineCommentRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    @Transactional
    public Long save(Long userId, Long gameId, OneLineCommentSaveRequestDto requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다. id = " + userId));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다. id = " + gameId));

        OneLineComment oneLineComment = OneLineComment.builder()
                .user(user)
                .context(requestDto.getContext())
                .game(game)
                .recommended_up(0)
                .recommended_down(0)
                .build();

        oneLineCommentRepository.save(oneLineComment);

        return oneLineComment.getId();
    }

    @Transactional
    public List<OneLineComment> findAllOneLineCommentByGameId(Long gameId) {
        return oneLineCommentRepository.findByGame_Id(gameId);
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
}
