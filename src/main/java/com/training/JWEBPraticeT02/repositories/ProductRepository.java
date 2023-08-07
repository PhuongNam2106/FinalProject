package com.training.JWEBPraticeT02.repositories;

import com.training.JWEBPraticeT02.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
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

    Page<Product> findByCategoryId(int field, Pageable pageable);

    Page<Product> findByCategoryIdAndTitle(int field,String title, Pageable pageable);
//    @Query("SELECT p FROM Product p WHERE p.category = :categoryId AND p.title LIKE %:keyword%")
//    Page<Product> findByCategoryIdAndTitleLike(@Param("categoryId") int categoryId, @Param("keyword") String keyword, Pageable pageable);

    Page<Product> findByCategoryIdAndTitleContainingIgnoreCase(int categoryId, String keyword, Pageable pageable);
    Page<Product> findByTitleContainingIgnoreCase(String keyword,Pageable pageable);
}
