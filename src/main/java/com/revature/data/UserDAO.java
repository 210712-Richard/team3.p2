package com.revature.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.revature.beans.User;
import com.revature.dto.UserDTO;

import reactor.core.publisher.Mono;

@Repository
public interface UserDAO extends ReactiveCassandraRepository<UserDTO, String>{
	Mono<UserDTO> findByUsernameAndPassword(String username, String password);
}
