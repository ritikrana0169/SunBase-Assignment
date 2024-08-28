package com.sunbase.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sunbase.model.Admin;
import com.sunbase.repository.AdminRepository;

@Service
public class AdminUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin user = adminRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("invalid email"));

		return new User(username, user.getPassword(), new ArrayList<>());
	}

}