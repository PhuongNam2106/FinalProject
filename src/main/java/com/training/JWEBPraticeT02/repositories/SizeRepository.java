package com.training.JWEBPraticeT02.repositories;

import com.training.JWEBPraticeT02.entity.Product;
import com.training.JWEBPraticeT02.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface SizeRepository extends JpaRepository<Size,Integer> {
   // @Query("SELECT s FROM Size s JOIN ProductSize ps WHERE ps.quantity > 0 and ps.product.id = :id")
   @Query("SELECT s FROM Size s JOIN s.productSizes ps JOIN ps.product p WHERE p.id = :id AND ps.quantity > 0")
    List<Size> sizeAvailable(@Param("id") Integer id);
}
