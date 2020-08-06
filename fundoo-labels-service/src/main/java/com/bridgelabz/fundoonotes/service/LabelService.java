package com.bridgelabz.fundoonotes.service;

import java.util.List;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.Label;
import com.bridgelabz.fundoonotes.exception.UserException;

public interface LabelService {

	Label createLabel(LabelDto labelDto, String token) throws UserException;
	
	Long updateLabel(Long id,String labelName,String token) throws UserException;
	
	Long deleteLabel(Long labelId, String token) throws UserException; 
	
	List<Label> getAllLabelsList(String token) throws UserException;
}
