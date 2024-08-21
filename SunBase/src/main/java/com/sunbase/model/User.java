package com.sunbase.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	    @Id
		@JsonProperty("uuid")
		private String uuid;
		
		@JsonProperty("first_name")
		private String firstName;
		
		@JsonProperty("last_name")
	    private String lastName;
	    
	    @JsonProperty("street")
	    private String street;
	    
	    @JsonProperty("address")
	    private String address;
	    
	    @JsonProperty("city")
	    private String city;
	    
	    @JsonProperty("state")
	    private String state;
	    
	    @JsonProperty("email")
	    private String email;
	    
	    @JsonProperty("phone")
	    private String phone;


}
