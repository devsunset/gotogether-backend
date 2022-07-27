package com.gotogether.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class UserActiveToken extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long activeId;
	@Size(max = 255)
	private String userActiveToken;
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "username")
	private User user;
	public UserActiveToken(User user) {
		this.user = user;
		this.userActiveToken = UUID.randomUUID().toString();
	}
}