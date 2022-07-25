package com.gotogether.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class TogetherSkill extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long togetherSkillId;

  @ManyToOne(targetEntity=Together.class, fetch=FetchType.LAZY)
  @JoinColumn(name="together_id")
  private Together together;

  @NotNull
  @Column(length = 255, nullable = false)
  private String item;
}

