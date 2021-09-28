package com.mighty.ninda.dto.oneLineComment;

import com.mighty.ninda.domain.game.Game;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveOneLineComment {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 200, message="200자 이하로 작성해주세요.")
    private String context;

    @NotNull
    private Long gameId;

    public SaveOneLineComment(String context, Long gameId) {
        this.context = context;
        this.gameId = gameId;
    }
}
