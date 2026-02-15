package com.java.xdigit_session2.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.xdigit_session2.ent.SessionsEnt;

public interface SessionRepo extends JpaRepository<SessionsEnt , Integer> {

	@Query("SELECT s FROM SessionsEnt s WHERE s.sessionId = :sessionId")
	Optional<SessionsEnt> findBySessionId(@Param("sessionId") String sessionId);
	
}
