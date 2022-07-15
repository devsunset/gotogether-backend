package com.gotogether.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "POST")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long post_id;

    @NotNull
    @Column(length = 20, nullable = false)
    private String category;
    @NotNull
    @Column(length = 255, nullable = false)
    private String title;
    @Lob
    @NotNull
    @Column(nullable = false)
    private String content;

    //@Column(columnDefinition = "default Y")
    private String deleted;

    //@Column(columnDefinition = "default 0")
    private int hit;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true, fetch = LAZY)
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
