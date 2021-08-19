package com.revature.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.NotificationDTO;

public interface NotificationDAO extends ReactiveCassandraRepository<NotificationDTO, String>{}
