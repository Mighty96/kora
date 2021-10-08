package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.direct.Direct;
import com.mighty.ninda.domain.direct.DirectRepository;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.dto.direct.SaveDirect;
import com.mighty.ninda.exception.EntityNotFoundException;
import com.mighty.ninda.exception.common.HandleAccessDenied;
import com.mighty.ninda.exception.onelinecomment.OneLineCommentAlreadyHateException;
import com.mighty.ninda.exception.onelinecomment.OneLineCommentAlreadyLikeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DirectService {

    private final DirectRepository directRepository;

    @Transactional
    public Direct findById(Long directId) {
        Direct direct = directRepository.findById(directId)
                .orElseThrow(() -> new EntityNotFoundException("Direct가 존재하지 않습니다. id = " + directId));

        return direct;
    }

    public List<Direct> findAllByOrderByReleasedDateDescIdDesc() {
        return directRepository.findAllByOrderByReleasedDateDescIdDesc();
    }

    @Transactional
    public void save(SaveDirect requestDto) {
        Direct direct = Direct.builder()
                .title(requestDto.getTitle())
                .releasedDate(LocalDate.parse(requestDto.getReleasedDate(), DateTimeFormatter.ofPattern("yyyyMMdd")))
                .koreaUrl(requestDto.getKoreaUrl())
                .japanUrl(requestDto.getJapanUrl())
                .americaUrl(requestDto.getAmericaUrl())
                .likeList("")
                .hateList("")
                .reHate(0)
                .reLike(0)
                .viewCount(0)
                .build();

        directRepository.save(direct);
    }

    @Transactional
    public void reLikeUp(CurrentUser currentUser, Long directId) {
        Direct direct = findById(directId);

        checkAuth(currentUser);

        Long userId = currentUser.getId();

        String _userId = "[" + userId.toString() + "]";

        if (direct.getLikeList().contains(_userId)) {
            throw new OneLineCommentAlreadyLikeException("이미 추천했습니다.");
        } else {
            direct.reLikeUp();
            direct.updateLikeList(_userId);
        }
    }

    @Transactional
    public void reHateUp(CurrentUser currentUser, Long directId) {
        Direct direct = findById(directId);

        checkAuth(currentUser);

        Long userId = currentUser.getId();

        String _userId = "[" + userId.toString() + "]";

        if (direct.getHateList().contains(_userId)) {
            throw new OneLineCommentAlreadyHateException("이미 비추천했습니다.");
        } else {
            direct.reHateUp();
            direct.updateHateList(_userId);
        }
    }

    @Transactional
    public void viewCountUp(Long directId) {
        Direct direct = findById(directId);
        direct.viewCountUp();
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
