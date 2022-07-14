package com.gotogether.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import java.util.UUID;


@Getter
@Setter
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