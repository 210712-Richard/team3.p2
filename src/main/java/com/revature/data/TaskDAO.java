package com.revature.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.beans.TaskCompletionStatus;
import com.revature.dto.TaskDTO;

import reactor.core.publisher.Mono;


public interface TaskDAO extends ReactiveCassandraRepository<TaskDTO, String>{
	@Query("Select * From team3_project2.tasks Where taskid = ?0 Allow filtering")
	Mono<TaskDTO> findByTaskId(UUID id);
	Mono<TaskDTO> findByBoardid(UUID boardid);
	@Query("Select * From team3_project2.tasks Where boardid = ?0 And status = ?1 And taskid = ?2")
	Mono<TaskDTO> findByBoardidAndStatusAndId(UUID boardid, TaskCompletionStatus status, UUID id);
	
}
