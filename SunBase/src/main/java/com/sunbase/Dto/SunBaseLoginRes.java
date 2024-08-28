package com.sunbase.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SunBaseLoginRes {
	@JsonProperty("access_token")
	private String access_token;
}