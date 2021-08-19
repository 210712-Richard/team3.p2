package com.revature.data;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.revature.dto.UserDTO;

@Repository
public interface UserDAO extends CassandraRepository<UserDTO, String>{}
