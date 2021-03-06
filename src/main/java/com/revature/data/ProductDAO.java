package com.revature.data;

import java.util.UUID;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;


import com.revature.dto.ProductDTO;

import reactor.core.publisher.Mono;

public interface ProductDAO extends ReactiveCassandraRepository<ProductDTO, String>{
	Mono<ProductDTO> findByProductid(UUID id);	
}

