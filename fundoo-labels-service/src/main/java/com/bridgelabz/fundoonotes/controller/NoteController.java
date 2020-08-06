package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.exception.ExceptionMessages;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.Responses;
import com.bridgelabz.fundoonotes.service.NoteService;

import io.swagger.annotations.Api;

@RestController
//@FeignClient(name="api-gateway")
//@RibbonClient(name="fundoo-labels-service")
public class NoteController {

	@Autowired
	NoteService service;
	
	@PostMapping("/createnote/{token}")
	public ResponseEntity<Response> create(@RequestBody NoteDto noteDto, @PathVariable String token) throws UserException{
	
		Note note = service.create(noteDto, token);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.NOTE_CREATED_SUCESSFULL, note));

	}
	
	@PutMapping("/updatenotes/{noteId}/{token}")
	public ResponseEntity<Response> updateNotes(@PathVariable Long noteId, @RequestBody NoteDto noteDto,@PathVariable String token) throws UserException{
		
		Note note=service.update(noteDto, noteId, token);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.NOTE_UPDATED_SUCESSFULL, note));

	}	
	
	@PutMapping("/pin/{noteId}/{token}")
	public ResponseEntity<Response> pin(@PathVariable("token") String token, @PathVariable("noteId") Long noteId)throws UserException {

		boolean result = service.pin(token, noteId);

		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.NOTE_PINNED_SUCESSFULL, result));
	}
	
	@PutMapping("/trash/{noteId}/{token}")
	public ResponseEntity<Response> isTrash(@PathVariable("token") String token, @PathVariable("noteId") Long noteId) throws UserException{

		boolean result = service.trash(noteId, token);

		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.NOTE_MOVED_TO_TRASH, result));
	}
	
	@DeleteMapping("/deletenotes/{token}")
	public ResponseEntity<Response> deleteNotes(@RequestParam Long noteId, @PathVariable("token")String token) throws UserException {

		Long notes = service.delete(noteId, token);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.NOTE_DELETED, notes));
			
	}
	
	@PutMapping("/isArchive/{token}")
	public ResponseEntity<Response> isArchive(@RequestParam Long noteId, @PathVariable("token") String token) throws UserException{
		boolean result = service.isArchive(noteId, token);

		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.NOTE_ARCHIVED_SUCESSFULL, result));
	}
	
	
	@PostMapping("/notes/{searchNotes}")
	public ResponseEntity<Response> getNotesByName(@RequestParam("title") String title,
			@RequestHeader("token") String token) throws UserException{
		Note notes = service.searchByTitle(title);
		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.NOTE_SEARCH_SUCESSFULL, notes));
		
	}
	
	@GetMapping("/notes/getAllNotes")
	public ResponseEntity<Response> getAllNotes(String token) throws UserException{
		List<Note> notes = service.getAllNotes(token);

		return ResponseEntity.ok()
				.body(new Response(HttpStatus.ACCEPTED, ExceptionMessages.NOTE_LISTED, notes));
	}

}
