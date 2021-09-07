package com.mighty.ninda.domain.hot;


import com.mighty.ninda.domain.BaseTimeEntity;
import com.mighty.ninda.domain.game.Game;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class Hot extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long gameId;

    @Builder
    public Hot(Long gameId) {
        this.gameId = gameId;
    }

}
