package com.revature.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.User;
import com.revature.data.UserDAO;

import reactor.core.publisher.Mono;

@Service
public class ScrumServiceImpl implements ScrumService {

	private UserDAO userDao;

	@Autowired
	public ScrumServiceImpl(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public Mono<User> assignTasks(UUID taskId, String username) {

		return userDao.findById(username).map(dto -> {
			dto.getTaskIds().add(taskId);
			userDao.save(dto);
			return dto.getUser();
		});
	}
	
	@Override
	public Mono<User> removeTasks(UUID id, String username){
		return userDao.findById(username)
				.map(dto -> {
					dto.getTaskIds()
						.removeIf(p-> p.equals(id));
					return dto.getUser();
				});
	}
}
