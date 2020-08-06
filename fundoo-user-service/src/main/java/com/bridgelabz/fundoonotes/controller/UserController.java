package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.ResetPassword;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.ExceptionMessages;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.JwtService;
import com.bridgelabz.fundoonotes.utility.JwtService.Token;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
@FeignClient(name="api-gateway")
@RibbonClient(name="fundoo-user-service")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@ApiOperation(value = "Api to Register User  ", response = Response.class)
	@PostMapping("/register")
	public ResponseEntity<Response> registration(@RequestBody RegisterDto userInfoDto) throws UserException {
		
		User user = service.register(userInfoDto);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.USER_REGISTER_SUCESSFULL, user));

	}

	@ApiOperation(value = "Api to verify the User ", response = Response.class)
	@PutMapping("/verifyemail/{token}")
	public ResponseEntity<Response> verification(@PathVariable("token") String token) throws UserException {

		boolean user = service.verifyUser(token);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.USER_VERIFIED_STATUS, user));
	}
	
	@ApiOperation(value = "Api to Login User", response = Response.class)
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto, BindingResult res) throws UserException {
		if (res.hasErrors()) {
			return ResponseEntity.badRequest().body(
					new Response(HttpStatus.NOT_ACCEPTABLE, ExceptionMessages.USER_FAILED_LOGIN_STATUS, loginDto));
		}
		User user = service.login(loginDto);
		String mailResponse = JwtService.generateToken(user.getUserId(), Token.WITH_EXPIRE_TIME);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.USER_LOGIN_STATUS, mailResponse));
	}
	
	
	@ApiOperation(value = "Api to check if UserExists or not", response = Response.class)
	@PostMapping("/forgetpassword")
	public ResponseEntity<Response> forgetPassword(@RequestParam String email) throws UserException {
		User user = service.forgetPassword(email);
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, "reset password mail send to email", email));

	}

	@ApiOperation(value = "Api to Reset User Password ", response = Response.class)
	@PutMapping("/resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@RequestBody ResetPassword password,
			@Valid @PathVariable("token") String token, BindingResult res) throws UserException {
		if (res.hasErrors()) {
			return ResponseEntity.badRequest().body(
					new Response(HttpStatus.NOT_ACCEPTABLE, ExceptionMessages.USER_RESET_PASSWORD_FAILED, password));
		}
		boolean result = service.resetPassword(password, token);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.USER_RESET_PASSWORD_SUCESSFULL, result));
	}
	
	@ApiOperation(value = "get  the user details by user id", response = Iterable.class)
	@GetMapping("/getuser/{token}")
	public ResponseEntity<Response> getUser(@PathVariable("token") String token)
			throws  UserException, IOException {
		User user = service.getUserById(token);
		return ResponseEntity.ok().body(new Response(HttpStatus.ACCEPTED, " user details are...", user));

	}
	
}
