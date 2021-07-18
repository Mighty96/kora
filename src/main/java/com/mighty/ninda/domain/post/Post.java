package com.mighty.ninda.domain.post;

import com.mighty.ninda.domain.BaseTimeEntity;
import com.mighty.ninda.domain.comment.Comment;
import com.mighty.ninda.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    private String board;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String context;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<Comment> comments;

    private int viewCount;
    private int reLike;
    private int reHate;

    @Column(length= 100000)
    private String likeList;

    @Column(length= 100000)
    private String hateList;

    @Builder
    public Post(String board, String title, String context, User user, int viewCount, int reLike, int reHate, String likeList, String hateList) {
        this.board = board;
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

    public void viewCountUp() {
        this.viewCount++;
    }

    public void reLikeUp() {
        this.reLike++;
    }

    public void reHateUp() {
        this.reHate++;
    }

    public void updateLikeList(String id) {

        if (!this.likeList.equals("")) {
            this.likeList +=  "," + id;
        } else {
            this.likeList += id;
        }
    }

    public void updateHateList(String id) {

        if (!this.hateList.equals("")) {
            this.hateList +=  "," + id;
        } else {
            this.hateList += id;
        }
    }
}
