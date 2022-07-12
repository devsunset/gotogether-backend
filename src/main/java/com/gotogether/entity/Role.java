package com.gotogether.entity;

import com.gotogether.system.enums.RoleType;
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
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RoleType rolename;

	public Role(RoleType rolename) {
		this.rolename = rolename;
	}

}