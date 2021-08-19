package com.revature.data;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.revature.dto.ProductDTO;

public interface ProductDAO extends CassandraRepository<ProductDTO, String>{
	

 

}
