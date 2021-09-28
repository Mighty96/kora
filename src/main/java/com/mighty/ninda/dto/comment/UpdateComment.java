package com.mighty.ninda.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateComment {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 200, message="200자 이하로 작성해주세요.")
    private String context;

    public UpdateComment(String context) {
        this.context = context;
    }
}
