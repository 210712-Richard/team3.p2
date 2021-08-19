package com.revature.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.TaskDTO;

public interface TaskDAO extends ReactiveCassandraRepository<TaskDTO, String>{

}
