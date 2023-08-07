package com.training.JWEBPraticeT02.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name = "tbl_roles")
public class Role extends BaseEntity implements GrantedAuthority {
	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", length = 100, nullable = false)
	private String description;

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tbl_users_roles", joinColumns = @JoinColumn(name ="role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<User>();
	public void addUsers(User user)
	{
		this.users.add(user);
		user.getRoles().add(this);
	}

	public void deleteUsers(User user)
	{
		this.users.remove(user);
		user.getRoles().remove(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getAuthority() {
		return name;
	}

//	@Override
//	public String getAuthority() {
//		// TODO Auto-generated method stub
//		return name;
//	}

}
