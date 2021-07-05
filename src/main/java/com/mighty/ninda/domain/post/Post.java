package com.mighty.ninda.domain.post;

import com.mighty.ninda.domain.BaseTimeEntity;
import com.mighty.ninda.domain.comment.Comment;
import com.mighty.ninda.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private Long boardNo;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String context;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    private int viewCount;
    private int reLike;
    private int reHate;

    @Column(length= 100000)
    private String likeList;

    @Column(length= 100000)
    private String hateList;

    @Builder
    public Post(Long boardNo, String title, String context, User user, int viewCount, int reLike, int reHate, String likeList, String hateList) {
        this.boardNo = boardNo;
        this.title = title;
        this.context = context;
        this.user = user;
        this.viewCount = viewCount;
        this.comments = new ArrayList<>();
        this.reLike = reLike;
        this.reHate = reHate;
        this.likeList = likeList;
        this.hateList = hateList;
    }

    public void update(String title, String context) {
        this.title = title;
        this.context = context;
    }
}
