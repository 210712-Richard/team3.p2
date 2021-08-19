package com.revature.data;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.revature.dto.TaskDTO;

public interface TaskDAO extends CassandraRepository<TaskDTO, String>{

}
