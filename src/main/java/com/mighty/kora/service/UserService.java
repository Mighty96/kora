package com.mighty.kora.service;

import com.mighty.kora.domain.user.User;
import com.mighty.kora.domain.user.UserRepository;
import com.mighty.kora.dto.user.UserResponseDto;
import com.mighty.kora.dto.user.UserSaveRequestDto;
import com.mighty.kora.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long save(UserSaveRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id =" + id));

        user.update(requestDto.getPassword(), requestDto.getNickname());

        return id;
    }

    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id =" + id));

        return new UserResponseDto(user);
    }


}
