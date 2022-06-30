package com.gotogether.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleid;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RoleType rolename;

	public Role(RoleType rolename) {
		this.rolename = rolename;
	}

}