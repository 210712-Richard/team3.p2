package com.revature.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.beans.SprintStatus;
import com.revature.dto.SprintDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SprintDAO extends ReactiveCassandraRepository<SprintDTO, String>{
	Mono<SprintDTO> findByScrumboardIDAndStatus(UUID scrumboardID, SprintStatus status);
	@Query("SELECT * FROM sprints WHERE scrumboardid=?0 ALLOW FILTERING")
	Flux<SprintDTO> findAllById(UUID scrumboardID);
}
