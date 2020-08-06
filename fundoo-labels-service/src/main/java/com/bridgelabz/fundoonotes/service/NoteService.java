package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.exception.UserException;

public interface NoteService {

	Note create(NoteDto noteDto, String token) throws UserException;
	
	public Note update(NoteDto noteDto,Long noteId,String token) throws UserException;

	boolean pin(String token, Long noteId) throws UserException;
	
	boolean trash(long noteId, String token) throws UserException;
	
	Long delete(Long noteId, String token)throws UserException;
	
	boolean isArchive(Long noteId, String token)throws UserException;
	
	Note searchByTitle(String title) throws UserException;
	
	List<Note> getAllNotes(String token) throws UserException;
	
	// Long getRedisCecheId(String token);

}
