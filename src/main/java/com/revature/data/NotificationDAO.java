package com.revature.data;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.revature.dto.NotificationDTO;

public interface NotificationDAO extends CassandraRepository<NotificationDTO, String>{}
