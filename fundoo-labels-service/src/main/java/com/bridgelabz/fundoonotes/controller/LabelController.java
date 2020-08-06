package com.bridgelabz.fundoonotes.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.Label;
import com.bridgelabz.fundoonotes.exception.ExceptionMessages;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.Responses;
import com.bridgelabz.fundoonotes.service.LabelService;

import io.swagger.annotations.Api;


@Api( description = "API's for the label controller")
@RestController
@FeignClient(name="api-gateway")
@RibbonClient(name="fundoo-labels-service")
public class LabelController {

	@Autowired
	private LabelService labelService;
	
	/* API for creating the Label */

	@PostMapping("/createlabel/{token}")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto labelDto, @PathVariable("token") String token) throws UserException{
		labelService.createLabel(labelDto, token);
		
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.OK, ExceptionMessages.LABEL_CREATED_SUCESSFUL));
	}

	/* API for Updating the Label */
	
	@PutMapping("/update-label/{token}")
	public ResponseEntity<Response> updateLabel(@RequestParam Long id, @RequestParam String labelName,
			@PathVariable("token") String token) throws UserException{
		Long result = labelService.updateLabel(id, labelName, token);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.OK, ExceptionMessages.LABEL_UPDATED_SUCESSFULL,result));
	}
	
	/* API for Deleting the Label */
	
	@DeleteMapping("/delete-label/{token}")
	public ResponseEntity<Response> deleteLabel(@RequestParam Long labelId, @PathVariable("token") String token) throws UserException{
		Long result = labelService.deleteLabel(labelId, token);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.OK, ExceptionMessages.LABEL_DELETED_SUCESSFULL,result));
	}
	
	/* API for getting the List of Labels */
	
	@GetMapping("/getlistoflabels/{token}")
	public ResponseEntity<Response> getLabelList(@PathVariable("token") String token) throws UserException{
		List<Label> result = labelService.getAllLabelsList(token);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.OK, ExceptionMessages.LABEL_DELETED_SUCESSFULL,result));
		
	}
}