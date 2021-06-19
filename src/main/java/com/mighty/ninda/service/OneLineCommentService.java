package com.mighty.ninda.service;

import com.mighty.ninda.domain.comment.OneLineComment;
import com.mighty.ninda.domain.comment.OneLineCommentRepository;
import com.mighty.ninda.dto.oneLineComment.OneLineCommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OneLineCommentService {

    private final OneLineCommentRepository oneLineCommentRepository;

    public Long save(OneLineCommentSaveRequestDto requestDto) {
        OneLineComment oneLineComment = OneLineComment.builder()
                .user(requestDto.getUser())
                .context(requestDto.getContext())
                .game(requestDto.getGame())
                .depth(requestDto.getDepth())
                .orders(requestDto.getOrders())
                .parent_id(requestDto.getParent_id())
                .recommended_up(0)
                .recommended_down(0)
                .build();

        oneLineCommentRepository.save(oneLineComment);

        return oneLineComment.getId();
    }
}
