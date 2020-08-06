package com.bridgelabz.fundoonotes.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
	
	private String name;
	
	private String email;
	
	private String password;
	
	private Long mobileNumber;

}
