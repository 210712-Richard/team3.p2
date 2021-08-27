package com.revature.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.NotificationDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NotificationDAO extends ReactiveCassandraRepository<NotificationDTO, String>{
	Mono<NotificationDTO> findByUsernameAndId(String username, UUID id);
	Flux<NotificationDTO> findByUsername(String username);
}
