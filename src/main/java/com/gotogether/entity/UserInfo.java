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
  private Long userInfoId;

  @OneToOne(targetEntity=User.class, fetch=FetchType.EAGER)
  @JoinColumn(name="username")
  private User user;

  @NotNull
  @Column(length = 255, nullable = false)
  private String introduce;

  @NotNull
  @Column(length = 1000)
  private String note;

  @Column(length = 500)
  private String github;

  @Column(length = 500)
  private String homepage;

}




