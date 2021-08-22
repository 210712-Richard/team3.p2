package com.revature.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.NotificationDTO;

import reactor.core.publisher.Mono;

public interface NotificationDAO extends ReactiveCassandraRepository<NotificationDTO, String>{
	Mono<NotificationDTO> findByUsernameAndId(String username, UUID id);
	Mono<NotificationDTO> findByUsername(String username);
}
