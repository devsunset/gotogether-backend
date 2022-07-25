package com.gotogether.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Entity
public class Note extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long noteId;

    @Lob
    @NotNull
    @Column(nullable = false)
    private String note;

    @ColumnDefault("'N'")
    private String read;

    @ColumnDefault("'N'")
    private String fromDeleted;

    @ColumnDefault("'N'")
    private String toDeleted;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_username")
    private User fromUser;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_username")
    private User toUser;
}
