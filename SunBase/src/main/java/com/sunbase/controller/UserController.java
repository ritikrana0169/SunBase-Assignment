package com.sunbase.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunbase.Dto.SunBaseLoginRes;
import com.sunbase.model.User;
import com.sunbase.service.UserService;

@RestController
@RequestMapping("/app/")
public class UserController {

	private UserService UserService;

	@Autowired
	public UserController(UserService UserService) {
		super();
		this.UserService = UserService;
	}

	@PostMapping("create")
	public ResponseEntity<User> create(@RequestBody User User) {
		if (User.getUuid() == null || User.getUuid() == "")
			User.setUuid(UUID.randomUUID().toString());
		User cust = UserService.create(User);
		return ResponseEntity.ok(cust);
	}

	@PutMapping("update")
	public ResponseEntity<User> update(@RequestBody User User) {
		User cust = UserService.update(User);
		return ResponseEntity.ok(cust);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<User> getUserById(@PathVariable String id) {
		User cust = UserService.findById(id);
		return ResponseEntity.ok(cust);
	}

	@GetMapping("get")
	public ResponseEntity<Page<User>> getUsers(Pageable pageable, @RequestParam(required = false) String column,
			@RequestParam(required = false) String search) {
		Page<User> Users = UserService.findAll(pageable, column, search);
		return ResponseEntity.ok(Users);
	}

	@PatchMapping("/sync")
	public ResponseEntity<String> syncUsers(@RequestBody SunBaseLoginRes token) throws IOException {
		UserService.syncAll(token.getAccess_token());

		return ResponseEntity.ok("data sync successfull");
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> delete(@PathVariable String id) {
		String message = UserService.delete(id);
		return ResponseEntity.ok(message);
	}

}