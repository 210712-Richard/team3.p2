package com.revature.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.TaskDTO;

import reactor.core.publisher.Mono;

public interface TaskDAO extends ReactiveCassandraRepository<TaskDTO, String>{
	Mono<TaskDTO> findByUUID(UUID id);
}
