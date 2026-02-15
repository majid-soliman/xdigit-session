package com.java.xdigit_session2.ent;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sessions")
@Data
@NoArgsConstructor
public class SessionsEnt {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String sessionId;
	private String userName;
	private Date creatingTime;
	
	public SessionsEnt(String sessionId, String userName, Date creatingTime) {
		this.sessionId = sessionId;
		this.userName  = userName;
		this.creatingTime = creatingTime;
	}
	
	
	
}
