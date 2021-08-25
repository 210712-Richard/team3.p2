package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.data.UserDAO;

@Service
public class ScrumServiceImpl implements ScrumService {

	private UserDAO userDao;

	@Autowired
	public ScrumServiceImpl(UserDAO userDao) {
		this.userDao = userDao;
	}

}
