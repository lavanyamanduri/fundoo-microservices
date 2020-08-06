package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.MailService;

@RestController
public class MailController {
@Autowired
MailService service;

	@RequestMapping("/sendmail/{email}/{response}")
	public ResponseEntity<Response> sendmail(@PathVariable("email")String toMail,@PathVariable("response") String response) throws Exception {
		System.out.println(toMail);
		service.sendMail(toMail,response);
		
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("mail sent Successfull", 200));
	}
}

