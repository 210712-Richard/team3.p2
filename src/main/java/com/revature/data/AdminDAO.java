package com.revature.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.AdminDTO;

public interface AdminDAO extends ReactiveCassandraRepository<AdminDTO, String> {

}
