package com.revature.data;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import com.revature.dto.ProductDTO;

public interface ProductDAO extends ReactiveCassandraRepository<ProductDTO, String>{
	
}
