package com.bridgelabz.fundoonotes.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long userId;

		@Column(name="name")
		private String name;

		@Column(name="password")
		private String password;

		@Column(name="email")
		private String email;

		@Column(name="mobilenumber")
		private Long mobileNumber;

		@Column(name="creationtime")
		
		private LocalDateTime creationTime;

		@Column(columnDefinition = "boolean default false", nullable = false,name="isverified")
		private boolean isverified;
}
