package com.bridgelabz.fundoonotes.serviceImplentation;

import java.time.LocalDateTime;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundoonotes.configuration.Constants;
import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.ResetPassword;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.ExceptionMessages;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.ResponseEmail;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.JwtService;
import com.bridgelabz.fundoonotes.utility.JwtService.Token;
import com.bridgelabz.fundoonotes.utility.MailService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private RestTemplate template; 
	
	
	@Transactional
	@Override
	public User register(@Valid RegisterDto userInfoDto) throws UserException {
		User user = new User();
		if (repository.FindUserByEmail(userInfoDto.getEmail()).isPresent()) {
			throw new UserException(HttpStatus.NOT_ACCEPTABLE, ExceptionMessages.USER_EMAIL_ID_ALREADY_PRASENT);
		}
		BeanUtils.copyProperties(userInfoDto, user);
		user.setPassword(bcrypt.encode(userInfoDto.getPassword()));
		user.setCreationTime(LocalDateTime.now());
		user = repository.save(user);
		String mailResponse =
			JwtService.generateToken(user.getUserId(), Token.WITH_EXPIRE_TIME);
	//	MailService.sendEmail(user.getEmail(), Constants.USER_VERIFICATION_MSG, mailResponse);
		
		ResponseEmail response=template.getForEntity("http://localhost:8085/sendmail/"+userInfoDto.getEmail()+"/"+mailResponse,ResponseEmail.class).getBody();	
		
		log.info(mailResponse);
		
		return user;
	}

	@Transactional
	@Override
	public boolean verifyUser(String token) throws UserException {

		Long id = JwtService.parse(token);

		User userInfo = repository.findbyId(id).orElseThrow(
				() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		if (userInfo.isIsverified() != true) {
			userInfo.setIsverified(true);
			repository.save(userInfo);
			return true;
		}

		throw new UserException(HttpStatus.ACCEPTED, ExceptionMessages.USER_ALREADY_VERIFIED);
	}
	
	@Transactional
	@Override
	public User login(LoginDto login) throws UserException {
		User user = repository.FindUserByEmail(login.getEmail()).orElseThrow(
				() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));

		if ((user.isIsverified() == true) && (bcrypt.matches(login.getPassword(), user.getPassword())) != true) {

			
			throw new UserException(HttpStatus.ACCEPTED, "please enter correct email and password ");

		}
		return user;}


	@Transactional
	@Override
	public User forgetPassword(String email) throws UserException {
		User userMail = repository.FindUserByEmail(email).orElseThrow(
				() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));

		if (userMail.isIsverified() == true) {
			String responsemail = Constants.USER_RESETPASSWORD_LINK
					+ JwtService.generateToken(userMail.getUserId(), Token.WITH_EXPIRE_TIME);
			MailService.sendEmail(userMail.getEmail(), Constants.RESET_PASSWORD, responsemail);
			return userMail;
		}
		throw new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE);
	}

	/*********************************************************************
	 * To reset password by the user with token.
	 * 
	 * @param String token,UserPasswordDto restpassword
	 ********************************************************************/
	@Transactional
	@Override
	public boolean resetPassword(ResetPassword password, String token) throws UserException {

		Long id = JwtService.parse(token);
		User user = repository.findById(id).orElseThrow(
				() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		if (user.isIsverified()) {
			user.setPassword(new BCryptPasswordEncoder().encode(password.getConfirmPassword()));
			repository.updateUserPassword(bcrypt.encode(password.getConfirmPassword()), id);
		}
		return true;
	}


	@Override
	@Transactional
	
	public User getUserById(String token) throws UserException {
		Long id = JwtService.parse(token);
		User user = repository.findbyId(id).orElseThrow(
				() -> new UserException(HttpStatus.NOT_FOUND, ExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGE));
		return user;
	}
	
	

}
