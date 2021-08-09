package com.mighty.ninda.domain.file;

import com.mighty.ninda.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Photo {

    @Id @GeneratedValue
    @Column(name = "photo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String UUID;

    private String fileName;

    private String filePath;

    @Builder
    public Photo(Post post, String UUID, String fileName, String filePath) {
        this.post = post;
        this.UUID = UUID;
        this.fileName = fileName;
        this.filePath = filePath;
    }

}
