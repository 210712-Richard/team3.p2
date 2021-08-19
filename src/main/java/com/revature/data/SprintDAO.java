package com.revature.data;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.revature.dto.SprintDTO;

public interface SprintDAO extends CassandraRepository<SprintDTO, String>{

}
