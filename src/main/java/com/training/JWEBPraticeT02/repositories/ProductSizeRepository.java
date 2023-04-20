package com.training.JWEBPraticeT02.repositories;

import com.training.JWEBPraticeT02.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize,Integer>  {
    @Query("SELECT pz FROM ProductSize pz where pz.product is null and pz.size is null")
    List<ProductSize> findNullProductIdAndSizeId();

    List<ProductSize> findByProductId(Integer productId);

//    List<ProductSize> findByQuantityGreaterThan(int quantity);
}
