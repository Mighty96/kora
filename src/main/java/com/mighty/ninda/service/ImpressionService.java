package com.mighty.ninda.service;

import com.mighty.ninda.domain.comment.Impression;
import com.mighty.ninda.domain.comment.ImpressionRepository;
import com.mighty.ninda.domain.direct.Direct;
import com.mighty.ninda.domain.direct.DirectRepository;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.impression.SaveImpression;
import com.mighty.ninda.dto.impression.UpdateImpression;
import com.mighty.ninda.exception.comment.CommentAlreadyHateException;
import com.mighty.ninda.exception.comment.CommentAlreadyLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImpressionService {

    private final ImpressionRepository impressionRepository;
    private final UserRepository userRepository;
    private final DirectRepository directRepository;

    @Transactional
    public Long save(Long userId, SaveImpression requestDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다. id = " + userId));

        Direct direct = directRepository.findById(requestDto.getDirectId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다. id = " + requestDto.getDirectId()));

        Impression impression = Impression.builder()
                .user(user)
                .context(requestDto.getContext())
                .direct(direct)
                .reLike(0)
                .reHate(0)
                .likeList("")
                .hateList("")
                .build();

        impressionRepository.save(impression);

        return impression.getId();
    }

    @Transactional
    public List<Impression> findAllImpressionByDirectId(Long directId) {
        return impressionRepository.findByDirect_Id(directId);
    }

    @Transactional
    public Long update(Long userId, Long impressionId, UpdateImpression requestDto) {
        Impression impression = impressionRepository.findById(impressionId)
                .orElseThrow(() -> new IllegalArgumentException("소감이 존재하지 않습니다. id = " + impressionId));

        if (!userId.equals(impression.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 변경할 수 있습니다.");
        }

        impression.update(requestDto.getContext());

        return impressionId;
    }

    @Transactional
    public Long deleteImpression(Long id) {
        Impression impression = impressionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("소감이 존재하지 않습니다. id = " + id));

        impressionRepository.delete(impression);

        return id;
    }

    @Transactional
    public Long reLikeUp(Long userId, Long impressionId) {
        Impression impression = impressionRepository.findById(impressionId)
                .orElseThrow(() -> new IllegalArgumentException("소감이 존재하지 않습니다. id = " + impressionId));

        String _userId = "[" + userId.toString() + "]";

        if (impression.getLikeList().contains(_userId)) {
            throw new CommentAlreadyLikeException("이미 추천했습니다.");
        } else {
            impression.reLikeUp();
            impression.updateLikeList(_userId);
        }

        return impression.getId();
    }

    @Transactional
    public Long reHateUp(Long userId, Long impressionId) {
        Impression impression = impressionRepository.findById(impressionId)
                .orElseThrow(() -> new IllegalArgumentException("소감이 존재하지 않습니다. id = " + impressionId));

        String _userId = "[" + userId.toString() + "]";

        if (impression.getHateList().contains(_userId)) {
            throw new CommentAlreadyHateException("이미 비추천했습니다.");
        } else {
            impression.reHateUp();
            impression.updateHateList(_userId);
        }

        return impression.getId();
    }
}
