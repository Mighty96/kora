package com.mighty.ninda.service;

import com.mighty.ninda.domain.direct.Direct;
import com.mighty.ninda.domain.direct.DirectRepository;
import com.mighty.ninda.dto.direct.SaveDirect;
import com.mighty.ninda.exception.onelinecomment.OneLineCommentAlreadyHateException;
import com.mighty.ninda.exception.onelinecomment.OneLineCommentAlreadyLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DirectService {

    private final DirectRepository directRepository;

    @Transactional
    public Direct findById(Long id) {
        Direct direct = directRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 다이렉트가 없습니다. id = " + id));

        return direct;
    }

    public List<Direct> findAll() {
        return directRepository.findAll();
    }

    @Transactional
    public Long save(SaveDirect requestDto) {
        Direct direct = Direct.builder()
                .title(requestDto.getTitle())
                .japanUrl(requestDto.getJapanUrl())
                .americaUrl(requestDto.getAmericaUrl())
                .likeList("")
                .hateList("")
                .reHate(0)
                .reLike(0)
                .build();

        directRepository.save(direct);

        return direct.getId();
    }

    @Transactional
    public Long reLikeUp(Long userId, Long directId) {
        Direct direct = directRepository.findById(directId)
                .orElseThrow(() -> new IllegalArgumentException("해당 다이렉트가 없습니다. id = " + directId));

        String _userId = "[" + userId.toString() + "]";

        if (direct.getLikeList().contains(_userId)) {
            throw new OneLineCommentAlreadyLikeException("이미 추천했습니다.");
        } else {
            direct.reLikeUp();
            direct.updateLikeList(_userId);
        }

        return direct.getId();
    }

    @Transactional
    public Long reHateUp(Long userId, Long directId) {
        Direct direct = directRepository.findById(directId)
                .orElseThrow(() -> new IllegalArgumentException("해당 다이렉트가 없습니다. id = " + directId));

        String _userId = "[" + userId.toString() + "]";

        if (direct.getHateList().contains(_userId)) {
            throw new OneLineCommentAlreadyHateException("이미 비추천했습니다.");
        } else {
            direct.reHateUp();
            direct.updateHateList(_userId);
        }


        return direct.getId();
    }
}
