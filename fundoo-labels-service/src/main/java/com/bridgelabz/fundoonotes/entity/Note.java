package com.bridgelabz.fundoonotes.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
@Table(name = "notes")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noteId;

	private String title;

	
	private String description;

	private boolean isPinned;
	
	private boolean isArchived;

	private boolean isTrashed;

	private Timestamp creationTime;

	private boolean reminder;

	private String color;
	
	private Long userId;
	
//	@ManyToOne
//	@JoinColumn(name = "userId")
//	private User user;
	
}
