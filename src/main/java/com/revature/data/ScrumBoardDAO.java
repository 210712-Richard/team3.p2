package com.revature.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.ScrumBoardDTO;

public interface ScrumBoardDAO extends ReactiveCassandraRepository<ScrumBoardDTO, String> {

}
