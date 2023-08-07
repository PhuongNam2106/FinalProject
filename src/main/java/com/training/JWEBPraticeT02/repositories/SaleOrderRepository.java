package com.training.JWEBPraticeT02.repositories;

import com.training.JWEBPraticeT02.entity.SaleOder;
import com.training.JWEBPraticeT02.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleOrderRepository extends JpaRepository<SaleOder,Integer> {
    List<SaleOder> findByUser_Id(int id);
    Page<SaleOder> findByCodeContainingIgnoreCase(String keyword, Pageable pageable);
    Page<SaleOder> findAllByOrderByIdDesc(Pageable pageable);
}
