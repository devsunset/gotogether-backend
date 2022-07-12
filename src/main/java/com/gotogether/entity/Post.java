package com.gotogether.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "POST")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long post_id;

    @Column(length = 20, nullable = false)
    private String category;
    @Column(length = 255, nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String content;

    private boolean deleted;

    private int hit;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "username")
    private User writer;
    @Builder
    public Post(String title, String content, User writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
