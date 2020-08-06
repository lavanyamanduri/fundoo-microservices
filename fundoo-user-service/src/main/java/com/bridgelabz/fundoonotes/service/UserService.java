package com.bridgelabz.fundoonotes.service;

import javax.validation.Valid;
import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.ResetPassword;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.UserException;

public interface UserService {

	public User register(@Valid RegisterDto userInfoDto) throws UserException;

	boolean verifyUser(String token) throws UserException;

	User login(LoginDto login) throws UserException;

	User forgetPassword(String email) throws UserException;

	boolean resetPassword(ResetPassword resetPassword, String token) throws UserException;
	
	public User getUserById(String token) throws UserException;
}
