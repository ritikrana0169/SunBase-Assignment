package com.sunbase.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
	private String login_id;
	private String password;
}