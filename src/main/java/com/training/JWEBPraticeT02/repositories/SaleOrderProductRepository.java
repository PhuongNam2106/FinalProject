package com.training.JWEBPraticeT02.repositories;

import com.training.JWEBPraticeT02.entity.SaleOderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleOrderProductRepository extends JpaRepository<SaleOderProduct,Integer> {
    List<SaleOderProduct> findBySaleOrder_Id(int saleOrderId);
}
