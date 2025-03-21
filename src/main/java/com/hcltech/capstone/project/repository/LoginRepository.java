package com.hcltech.capstone.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcltech.capstone.project.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

	Optional<Login> findByUsername(String username);

}
