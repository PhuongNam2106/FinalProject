package com.training.JWEBPraticeT02.repositories;

import com.training.JWEBPraticeT02.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	Page<User> findByCustomerNameContainingIgnoreCase(String keyword, Pageable pageable);
	User findByEmail(String email);

	User findByResetPasswordToken(String token);
	User findByCustomerName (String name);


}
