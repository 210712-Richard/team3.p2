package com.revature.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.beans.SprintStatus;
import com.revature.dto.SprintDTO;

import reactor.core.publisher.Mono;

public interface SprintDAO extends ReactiveCassandraRepository<SprintDTO, String>{
	Mono<SprintDTO> findByBoardIdAndStatus(UUID boardId, SprintStatus status);
}
