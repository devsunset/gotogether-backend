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
public class Together extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long togetherId;

    @NotNull
    @Column(length = 255, nullable = false)
    private String title;

    @NotNull
    @Column(length = 20, nullable = false)
    private String category;

    @Lob
    @NotNull
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(length = 20, nullable = false)
    private String involveType;

    @Column(length = 255, nullable = false)
    private String openKakaoChat;

    @Column(length = 50)
    private String latitude;

    @Column(length = 50)
    private String longitude;

    @ColumnDefault("0")
    private int hit;

    @ColumnDefault("0")
    private int maxMember;

    @ColumnDefault("0")
    private int currentMember;

    @Lob
    @NotNull
    @Column(nullable = false)
    private String skill;

    @OneToMany(mappedBy = "together", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("createdDate asc")
    private List<TogetherComment> togetherComments;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "username")
    private User writer;
}
