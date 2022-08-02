package com.gotogether.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
public class UserSkill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long skillId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    @NotNull
    @Column(length = 255, nullable = false)
    private String item;

    @NotNull
    @Column(length = 20, nullable = false)
    private String itemLevel;

}

