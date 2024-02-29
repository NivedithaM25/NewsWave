package com.newswave.authserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authserver")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int Id;
	private String userName;
	private String password;

}
