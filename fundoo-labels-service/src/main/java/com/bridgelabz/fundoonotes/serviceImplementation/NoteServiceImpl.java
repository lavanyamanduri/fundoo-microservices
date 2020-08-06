package com.bridgelabz.fundoonotes.serviceImplementation;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.auth0.jwt.exceptions.JWTVerificationException;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.entity.Label;
import com.bridgelabz.fundoonotes.entity.Note;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.JwtService;

import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService{

	
	Date date = new Date();
	Long time = date.getTime();
	Timestamp timeStamp = new Timestamp(time);
	
	
	@Autowired
	private RedisTemplate<String, Object> redis;
	
	@Autowired
	private RestTemplate rest;
	
	@Autowired
	private NoteRepository noteRepository;
	
	
	public Integer getuserId(String token) {
		
            rest.getForEntity("http://localhost:8081/user/getuser/"+token, User.class).getBody();
		
		return 1;
	}
	
	
	@Override
	public Note create(NoteDto noteDto, String token) throws UserException {
		try {
			Long parseToken = JwtService.parse(token);
			
			if(getuserId(token)==1) {
				Note notes = new Note();
				notes.setTitle(noteDto.getTitle());
				notes.setDescription(noteDto.getDescription());
				notes.setColor("white");
				notes.setCreationTime(timeStamp);
				notes.setUserId(parseToken);
				noteRepository.save(notes);
				return notes;

			}
		} catch (Exception e) {
			System.out.println(e);

		}
		throw new UserException("User not found");
	}


	@Override
	public Note update(NoteDto noteDto,Long noteId,String token) throws UserException {
			Long parseToken = JwtService.parse(token);
			if(getuserId(token)==1) {
			Note note=new Note();
			note=noteRepository.findbyId(noteId);
				if (note != null) {	
					
					note.setTitle(noteDto.getTitle());
					note.setDescription(noteDto.getDescription());
					note.setNoteId(note.getNoteId());
						noteRepository.updateData(note.getTitle(), note.getDescription(), noteId,parseToken);
					return note;
				}
				return null;
		}
			throw new UserException("User not found");
			
	}
	
	@Override
	public boolean pin(String token, Long noteId) throws UserException{
		Long parseToken = JwtService.parse(token);
		if(getuserId(token)==1) {
		Note note=new Note();
		note=noteRepository.findbyId(noteId);
				if (note != null) {
					if (note.isPinned()) {
						noteRepository.isPinned(false, noteId);
						return true;
					} else {
						noteRepository.isPinned(true, noteId);
						return true;
					}
				}
			}

		
		throw new UserException("User not found");
	}
	
	@Override
	public boolean trash(long noteId, String token) throws UserException{

		Long parseToken = JwtService.parse(token);
		if(getuserId(token)==1) {
		Note note=new Note();
		note=noteRepository.findbyId(noteId);
				if (note != null) {
					if (note.isTrashed()) {
						noteRepository.isTrashed(false, noteId);
						return true;
					} else {
						noteRepository.isTrashed(true, noteId);
						return true;
					} 
				} 
	}
		throw new UserException("User not found");
	
}

	@Override
	@CacheEvict(value = "note", key = "#noteId", condition = "#result!=null")
	public Long delete(Long noteId, String token) throws UserException{

		Long parseToken = JwtService.parse(token);
		if(getuserId(token)==1) {
		Note note=new Note();
		note=noteRepository.findbyId(noteId);
				if (note != null) {
					noteRepository.delete(noteId);
					return noteId;
				} else {
					return null;
				}
			}
		
		throw new UserException("User not found");
	}

	
	@Override
	public boolean isArchive(Long noteId, String token)throws UserException {
		Long parseToken = JwtService.parse(token);
		if(getuserId(token)==1) {
		Note note=new Note();
		note=noteRepository.findbyId(noteId);
				if (note != null) {
					if (note.isArchived()) {
						noteRepository.isArchived(false, noteId);
						return true;
					} else if (!note.isArchived()) {
						noteRepository.isArchived(true, noteId);
						return true;
					} else {
						return false;
					}
				} 
		}
		throw new UserException("User not found");
	}
	
	
	
	@Override
	public Note searchByTitle(String title) throws UserException{
		Note notes=new Note();
		notes = noteRepository.searchByTitle(title);
		if (notes != null) {
			return notes;
		} else
			throw new UserException("User not found");
	}

	@Override
	public List<Note> getAllNotes(String token) throws UserException {
		List<Note> notes = noteRepository.findAllNotes(token);
		if (notes != null) {
			return notes;
		}
		throw new UserException("User not found");
	}

	
}
