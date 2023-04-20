package com.training.JWEBPraticeT02.repositories;

import com.training.JWEBPraticeT02.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
