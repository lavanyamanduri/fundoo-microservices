package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ResetPassword {

	
	@NotBlank
	private String confirmPassword;
	

}