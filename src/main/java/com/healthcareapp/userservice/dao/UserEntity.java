package com.healthcareapp.userservice.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1293084829632561836L;

	@Id
	@Column(name="email", nullable=false, length=50)
	private String email;
	
	@Column(name="first_name", nullable=false, length=50)
	private String firstName;
	
	@Column(name="last_name", nullable=false, length=50)
	private String lastName;
	
	@Column(name="password", nullable=false)
	private String encryptedPassword;

}
