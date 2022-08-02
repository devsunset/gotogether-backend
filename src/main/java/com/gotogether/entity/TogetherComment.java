package com.gotogether.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Entity
public class TogetherComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long togetherCommentId;

    @ManyToOne
    @JoinColumn(name = "together_id")
    private Together together;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "username")
    private User writer;
}
