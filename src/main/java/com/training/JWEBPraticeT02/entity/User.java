package com.training.JWEBPraticeT02.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tbl_users")
public class User extends BaseEntity /*implements UserDetails*/{
	@Column(name = "username", length = 100, nullable = false)
	private String username;

	@Column(name = "password", length = 100, nullable = false)
	private String password;

	@Column(name = "email", length = 100, nullable = false)
	private String email;

	@Column(name = "address", length = 100, nullable = false)
	private String address;


	@Column(name = "phone", length = 100, nullable = false)
	private String phone;

	@Column(name = "customer_name", length = 100, nullable = false)
	private String customerName;



	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	// mappedBy là tên cả property liên quan với định nghĩa Many to one nào
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private Set<SaleOder> saleOder = new HashSet<SaleOder>();

	public Set<SaleOder> getSaleOder() {
		return saleOder;
	}

	public void setSaleOder(Set<SaleOder> saleOder) {
		this.saleOder = saleOder;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "users")
	private Set<Role> roles = new HashSet<Role>();

	public void addRoles(Role role)
	{
		role.getUsers().add(this);
		roles.add(role);
	}

	public void deleteRoles(Role role)
	{
		role.getUsers().remove(this);
		roles.remove(role);
	}


	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return roles;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return true;
//	}

}
