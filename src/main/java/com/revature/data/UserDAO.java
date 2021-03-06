package com.revature.data;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import com.revature.dto.UserDTO;

import reactor.core.publisher.Mono;

@Repository
public interface UserDAO extends ReactiveCassandraRepository<UserDTO, String>{ 
	@Query("Select * From team3_project2.users where username=?0")
	Mono<UserDTO> findByUsername(String username);
	
}
