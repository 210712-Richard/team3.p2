package com.revature.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.SprintDTO;

public interface SprintDAO extends ReactiveCassandraRepository<SprintDTO, String>{

}
