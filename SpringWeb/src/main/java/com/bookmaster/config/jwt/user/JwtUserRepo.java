package com.bookmaster.config.jwt.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JwtUserRepo extends JpaRepository<JwtUserDetails, Long>{
	public Optional<JwtUserDetails> findByUserId(String userId);
}