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
public class Memo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long memoId;

    @Lob
    @NotNull
    @Column(nullable = false)
    private String memo;

    @ColumnDefault("'N'")
    private String readflag;

    @ColumnDefault("'N'")
    private String sdelete;

    @ColumnDefault("'N'")
    private String rdelete;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="sender_username")
    private User sender;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="receiver_username")
    private User receiver;
}
