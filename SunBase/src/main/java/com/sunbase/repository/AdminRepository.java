package com.sunbase.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbase.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
	public Optional<Admin> findByEmail(String username);
}