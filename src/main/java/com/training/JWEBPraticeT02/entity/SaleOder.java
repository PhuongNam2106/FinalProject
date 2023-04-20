package com.training.JWEBPraticeT02.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tbl_saleorder")
public class SaleOder extends BaseEntity {
	@Column(name = "code", length = 100, nullable = false)
	private String code;
	
	
	@Column(name = "total", length = 100, nullable = false)
	private BigDecimal total;
	
	@Column(name = "customer_name", length = 100, nullable = false)
	private String customerName;
	
	@Column(name = "customer_address", length = 100, nullable = false)
	private String customerAddress;
	
	@Column(name = "seo", length = 100, nullable = true)
	private String seo;
	
	@Column(name = "customer_phone", length = 100, nullable = false)
	private String customerPhone;
	
	@Column(name = "customer_email", length = 100, nullable = false)
	private String customerEmail;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getSeo() {
		return seo;
	}

	public void setSeo(String seo) {
		this.seo = seo;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id") //định nghĩa khóa ngoài bằng @JoinCollumn
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "saleOrder", fetch = FetchType.LAZY)
	private Set<SaleOderProduct> saleOder_Products =  new HashSet<SaleOderProduct>();
	
	public void addSaleOrderProduct(SaleOderProduct saleOderProduct) {
		saleOderProduct.setSaleOrder(this);
		saleOder_Products.add(saleOderProduct);
	}
	
	public void deleteProductImages(SaleOderProduct saleOderProduct) {
		saleOderProduct.setSaleOrder(null);
		saleOder_Products.remove(saleOderProduct);
	}

	public Set<SaleOderProduct> getSaleOder_Products() {
		return saleOder_Products;
	}

	public void setSaleOder_Products(Set<SaleOderProduct> saleOder_Products) {
		this.saleOder_Products = saleOder_Products;
	}
}
