package com.gotogether.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long postId;

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

    @ColumnDefault("0")
    private int hit;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("createdDate asc")
    private List<Comment> comments;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "username")
    private User writer;
}
