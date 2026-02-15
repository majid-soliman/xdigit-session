package com.java.xdigit_session2.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.java.xdigit_session2.ent.SessionsEnt;
import com.java.xdigit_session2.repo.SessionRepo;

import jakarta.transaction.Transactional;

@Component
public class SessionDAO {

	@Autowired
	SessionRepo repo;
	
	public SessionsEnt createSession(SessionsEnt session) {
	   Optional<SessionsEnt> existingSession = repo.findBySessionId(session.getSessionId());
	if (existingSession.isPresent()) {
	   return existingSession.get();
	  }	else {
	   return repo.save(session);
	}}
	
	@Transactional
	public SessionsEnt renameSession(int id,String name) {
		if(repo.findById(id).isPresent()) {
			SessionsEnt ent = repo.findById(id).get();
			 ent.setUserName(name);
			 return repo.save(ent);	
		}	
		else {
			return new SessionsEnt();
		}
	
	}
	
	@Transactional
   public SessionsEnt deleteSession(int id) {
    if(repo.findById(id).isPresent()) {
		SessionsEnt ent = repo.findById(id).get();
		repo.deleteById(id);
		 return ent;	
	}else {	
	return new SessionsEnt();
	}
	}
	
	
}
