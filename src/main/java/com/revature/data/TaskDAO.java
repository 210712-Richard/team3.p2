package com.revature.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.TaskDTO;
import com.revature.beans.TaskCompletionStatus;

import reactor.core.publisher.Mono;


public interface TaskDAO extends ReactiveCassandraRepository<TaskDTO, String>{
	Mono<TaskDTO> findById(UUID id);
	Mono<TaskDTO> findByBoardid(UUID boardid);
	@Query("Select * From team3_project2.tasks Where board_id = ?0 And status = ?1 And task_id = ?2")
	Mono<TaskDTO> findByBoardidAndStatusAndId(UUID boardid, String status, UUID id);
}
