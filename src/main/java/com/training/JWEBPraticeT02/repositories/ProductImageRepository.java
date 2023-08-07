package com.training.JWEBPraticeT02.repositories;

import com.training.JWEBPraticeT02.entity.Product_Image;
import com.training.JWEBPraticeT02.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<Product_Image,Integer> {
//    @Query("SELECT s FROM Product_Image s WHERE s.product_id = :id")
//    List<Product_Image> productImageAvailable(@Param("id") Integer id);

    List<Product_Image> findByProduct_Id(Integer productId);
}
