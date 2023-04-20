package com.training.JWEBPraticeT02.repositories;

import com.training.JWEBPraticeT02.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
@EnableJpaRepositories
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByHot(boolean isHot);
    List<Product> findBySeo(String seo);
    Product findTopByOrderByIdDesc();
    List<Product> findByCategoryId(int categoryId);
    @Query("SELECT p FROM Product p ORDER BY p.id DESC")
    List<Product> findLatestProducts();
}
