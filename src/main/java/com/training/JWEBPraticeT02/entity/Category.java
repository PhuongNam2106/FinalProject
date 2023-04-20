package com.training.JWEBPraticeT02.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import javax.persistence.Table;

import java.util.Set;


@Entity
@Table(name="tbl_category")
public class Category extends BaseEntity{
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
	@Column(name = "description", length = 100)
	private String description;
	
	@Column(name = "seo", length = 100, nullable = true)
	private String seo;
	
	//mappedBy là tên cả property liên quan với định nghĩa Many to one nào
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
	private Set<Product> product;

	public void addProduct(Product product)
	{
		this.product.add(product); // thêm product vào trong Set<Product>
		// product phải thuộc đối tượng category đang xét(đang call vào hàm này)
		product.setCategory(this);
	}
	 
	public void deleteProduct(Product product)
	{
		this.product.remove(product); // thêm product vào trong Set<Product>
		// product phải thuộc đối tượng category đang xét(đang call vào hàm này)
		product.setCategory(null);
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

	public Set<Product> getProducts() {
		return product;
	}

	public void setProducts(Set<Product> products) {
		this.product = products;
	}

	public String getSeo() {
		return seo;
	}

	public void setSeo(String seo) {
		this.seo = seo;
	}
	
	
}
