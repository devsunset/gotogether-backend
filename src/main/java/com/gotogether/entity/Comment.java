package com.gotogether.entity;


import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Table(name="COMMENT")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long comment_id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @Lob
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "username")
    private User writer;
    @Builder
    public Comment( Post post, String content, User writer) {
        this.post = post;
        this.content = content;
        this.writer = writer;
    }
}
