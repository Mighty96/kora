package com.mighty.ninda.service;

import com.mighty.ninda.config.auth.dto.CurrentUser;
import com.mighty.ninda.domain.comment.Impression;
import com.mighty.ninda.domain.comment.ImpressionRepository;
import com.mighty.ninda.domain.direct.Direct;
import com.mighty.ninda.domain.direct.DirectRepository;
import com.mighty.ninda.domain.user.RegistrationId;
import com.mighty.ninda.domain.user.Role;
import com.mighty.ninda.domain.user.User;
import com.mighty.ninda.domain.user.UserRepository;
import com.mighty.ninda.dto.impression.SaveImpression;
import com.mighty.ninda.dto.impression.UpdateImpression;
import com.mighty.ninda.exception.EntityNotFoundException;
import com.mighty.ninda.exception.comment.CommentAlreadyHateException;
import com.mighty.ninda.exception.comment.CommentAlreadyLikeException;
import com.mighty.ninda.exception.common.HandleAccessDenied;
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
    public void save(CurrentUser currentUser, SaveImpression requestDto) {

        checkAuth(currentUser);
        Long userId = currentUser.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User가 존재하지 않습니다. id = " + userId));

        Direct direct = directRepository.findById(requestDto.getDirectId())
                .orElseThrow(() -> new IllegalArgumentException("Direct가 존재하지 않습니다. id = " + requestDto.getDirectId()));

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
    }

    @Transactional
    public Impression findById(Long impressionId) {
        return impressionRepository.findById(impressionId).orElseThrow(() -> new EntityNotFoundException("Impression가 존재하지 않습니다. id = " + impressionId));
    }

    @Transactional
    public List<Impression> findAllImpressionByDirectId(Long directId) {
        return impressionRepository.findByDirect_Id(directId);
    }

    @Transactional
    public void update(CurrentUser currentUser, Long impressionId, UpdateImpression requestDto) {

        checkAuth(currentUser);
        Long userId = currentUser.getId();
        Impression impression = findById(impressionId);

        if (!userId.equals(impression.getUser().getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new IllegalArgumentException("작성자만 변경할 수 있습니다.");
        }

        impression.update(requestDto.getContext());
    }

    @Transactional
    public void deleteImpression(CurrentUser currentUser, Long ImpressionId) {

        checkAuth(currentUser);
        Long userId = currentUser.getId();
        Impression impression = findById(ImpressionId);

        if (!userId.equals(impression.getUser().getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new HandleAccessDenied("작성자만 삭제할 수 있습니다.");
        }

        impressionRepository.delete(impression);
    }

    @Transactional
    public void reLikeUp(CurrentUser currentUser, Long impressionId) {
        Impression impression = findById(impressionId);

        checkAuth(currentUser);

        Long userId = currentUser.getId();
        String _userId = "[" + userId.toString() + "]";

        if (impression.getLikeList().contains(_userId)) {
            throw new CommentAlreadyLikeException("이미 추천했습니다.");
        } else {
            impression.reLikeUp();
            impression.updateLikeList(_userId);
        }
    }

    @Transactional
    public void reHateUp(CurrentUser currentUser, Long impressionId) {
        Impression impression = findById(impressionId);

        checkAuth(currentUser);

        Long userId = currentUser.getId();
        String _userId = "[" + userId.toString() + "]";

        if (impression.getHateList().contains(_userId)) {
            throw new CommentAlreadyHateException("이미 비추천했습니다.");
        } else {
            impression.reHateUp();
            impression.updateHateList(_userId);
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
