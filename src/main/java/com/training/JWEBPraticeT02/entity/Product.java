package com.training.JWEBPraticeT02.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "tbl_products")
public class Product extends BaseEntity{
    @Column(name = "title", length = 1000, nullable = false)
    private String title;

    @Column(name = "price", precision = 13, scale = 2, nullable = true)
    private BigDecimal price;

    @Column(name = "price_sale", precision = 13, scale = 2, nullable = false)
    private BigDecimal priceSale;

    @Column(name = "detail_description", nullable = false)
    private String detailDescription;

    @Column(name = "brand", length = 100, nullable = true)
    private String brand;
    @Column(name = "avatar", length = 200, nullable = true)
    private String avatar;

    @Column(name = "seo", length = 1000, nullable = false)
    private String seo;

    @Column(name = "sale", nullable = false)
    private int sale;
    @Column(name = "hot")
    private boolean hot;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id") // định nghĩa khóa ngoài bằng @JoinCollumn
    private Category category;

    public Category getCategory() {
        return category;
    }
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Product_Image> product_Images = new HashSet<Product_Image>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
    private Set<SaleOderProduct> saleOder_Products =  new HashSet<SaleOderProduct>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductSize> productSize = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceSale() {
        return priceSale;
    }

    public void setPriceSale(BigDecimal price, int sale) {
        double result =  price.doubleValue() - (price.doubleValue()*(sale*0.01));
        BigDecimal resultFinal = new BigDecimal(result);
        this.priceSale = resultFinal;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Product_Image> getProduct_Images() {
        return product_Images;
    }

    public void setProduct_Images(Set<Product_Image> product_Images) {
        this.product_Images = product_Images;
    }

    public Set<SaleOderProduct> getSaleOder_Products() {
        return saleOder_Products;
    }

    public void setSaleOder_Products(Set<SaleOderProduct> saleOder_Products) {
        this.saleOder_Products = saleOder_Products;
    }

    public void addProductImages(Product_Image productImage) {
        productImage.setProducts(this);
        product_Images.add(productImage);
    }

    public void deleteProductImages(Product_Image productImage) {
        productImage.setProducts(null);
        product_Images.remove(productImage);
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public boolean getHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public List<ProductSize> getProductSize() {
        return productSize;
    }

    public void setProductSize(List<ProductSize> productSize) {
        this.productSize = productSize;
    }
}
