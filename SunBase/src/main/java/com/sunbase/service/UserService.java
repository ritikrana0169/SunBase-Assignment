package com.sunbase.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunbase.Dto.LoginDto;
import com.sunbase.exception.NotFoundException;
import com.sunbase.model.User;
import com.sunbase.repository.UserRepository;

@Service
public class UserService {

	private UserRepository UserRepo;

	private RestTemplate restTemplate;

	private ObjectMapper objectMapper;

	@Autowired
	public UserService(UserRepository UserRepo, RestTemplate restTemplate,ObjectMapper objectMapper) {
		super();
		this.UserRepo = UserRepo;
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public User create(User User) {
		return UserRepo.save(User);
	}


	public User update(User User) {
		UserRepo.findById(User.getUuid())
				.orElseThrow(() -> new NotFoundException("invalid User id :" + User.getUuid()));
		return UserRepo.save(User);
	}

	
	public User findById(String id) {
		return UserRepo.findById(id).orElseThrow(() -> new NotFoundException("invalid User id : " + id));
	}

	
	public Page<User> findAll(Pageable pageable, String columnName, String searchKeyword) {
		if (columnName != null && searchKeyword != null) {
			return UserRepo.findByColumnNameAndKeyword(columnName, searchKeyword, pageable);
		} else {
			return UserRepo.findAll(pageable);
		}
	}

	
	public String delete(String id) {
		UserRepo.findById(id).orElseThrow(() -> new NotFoundException("invalid User id : " + id));
		UserRepo.deleteById(id);
		return "User Record Deleted Successfully";
	}

	
	public void syncAll(String token) throws IOException {
		String path = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp";
		String url = UriComponentsBuilder.fromHttpUrl(path)
				.queryParam("cmd", "get_User_list").toUriString();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		
		HttpEntity<String> entity = new HttpEntity<>(headers);

		// Make the GET request
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		List<User> Users = objectMapper.readValue(response.getBody(), new TypeReference<List<User>>() {});
		
		Users.forEach(c->createOrupdate(c));
		
	}

	
	public void createOrupdate(User User) {
		Optional<User> findById = UserRepo.findById(User.getUuid());
		if(findById.isPresent()) {
			update(User);
		}else {
			create(User);
		}
	}
	
	
	public String sunbaselogin(String loginId, String password) {

		String url = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
		LoginDto logindto = new LoginDto(loginId, password);
		System.out.println(logindto);
		String response = restTemplate.postForObject(url, logindto, String.class);

		JSONObject jsonObject = new JSONObject(response);

		String accessToken = jsonObject.getString("access_token");

		return accessToken;
	}

}