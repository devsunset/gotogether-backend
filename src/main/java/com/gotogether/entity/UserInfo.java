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
public class UserInfo extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long infoId;

  @ManyToOne(targetEntity=User.class, fetch=FetchType.LAZY)
  @JoinColumn(name="username")
  private User user;

  @NotNull
  @Column(length = 255, nullable = false)
  private String introduce;

  @NotNull
  @Lob
  private String note;

  @Column(length = 255)
  private String github;

  @Column(length = 255)
  private String homepage;

}




