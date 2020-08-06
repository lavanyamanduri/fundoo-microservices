package com.bridgelabz.fundoonotes.serviceImplementation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.Label;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exception.LabelNameAlreadyExistsException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.Responses;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.JwtService;
import com.bridgelabz.fundoonotes.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LabelServImpl implements LabelService {

	@Autowired
	private LabelRepository labelRepo;

//	@Autowired
//	private UserRepository userRepo;

//	@Autowired
//	private NotesRepository noteRepo;

	
	@Autowired
	private RestTemplate rest;
	

		public Integer getuserId(String token) {
			
			rest.getForEntity("http://localhost:8081/user/getuser/"+token, User.class).getBody();
			
			return 1;
		}
		
	@Override
	public Label createLabel(LabelDto labelDto, String token) throws UserException {
		try {
		
		if(getuserId(token)==1) {
			
			Long mail = JwtService.parse(token);
				String labelName = labelDto.getLabelName();
				Label label = labelRepo.findByLabeName(labelName, mail);
				if (label == null) {
					Label newLabel = new Label();
					newLabel.setLabelName(labelDto.getLabelName());
					labelRepo.save(newLabel);
				} else {
					throw new LabelNameAlreadyExistsException("label name already exists..");
				}
			}
			
		} catch (Exception e) {
			log.error("error " + e.getMessage() + " occured while creating the label");
		}
		throw new UserException("user not found");
	}

	@Override
	public Long updateLabel(Long id, String labelName, String token) throws UserException{
		
		if (getuserId(token) != 0) {
			Long mail = JwtService.parse(token);
				Label label = labelRepo.findByLabelId(id, mail);
				Long labelId = label.getLabelId();
				if (label != null) {
					labelRepo.updateData(labelName, labelId, mail);
					return id;
				}
		}
		throw new UserException("user not found");
	}
	
	@Override
	public Long deleteLabel(Long labelId, String token) throws UserException{
		Long mail = JwtService.parse(token);
		log.info("deleting the label of this mail " + mail);
		int id=getuserId(token);
		Long l=new Long(id);
		Long l2=Long.valueOf(id);
		
		Label label = labelRepo.findByLabelId(labelId, l2);
		if (getuserId(token) != 0) {
				if (label != null) {
					labelRepo.deleteData(labelId, l2);
					return labelId;
				}
			return labelId;
		}
		throw new UserException("user not found");
	}

	@Override
	public List<Label> getAllLabelsList(String token) throws UserException{
		Long mail = JwtService.parse(token);
		log.info("list of all labels of this user " + mail);
	//	UserDetails user = userRepo.findByEmail(mail);
		if (getuserId(token) != 0) {
			int id=getuserId(token);
			Long l=new Long(id);
			Long l2=Long.valueOf(id);
			return labelRepo.getAllLabels(l2);
		}
		throw new UserException("user not found");
	}

}