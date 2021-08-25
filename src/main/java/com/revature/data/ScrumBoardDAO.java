package com.revature.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.ScrumBoardDTO;

import reactor.core.publisher.Mono;

public interface ScrumBoardDAO extends ReactiveCassandraRepository<ScrumBoardDTO, String> {
	Mono<ScrumBoardDTO> findByBoardId(UUID id);
}
