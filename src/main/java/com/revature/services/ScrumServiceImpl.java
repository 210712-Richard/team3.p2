package com.revature.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.SprintStatus;
import com.revature.beans.User;
import com.revature.data.SprintDAO;
import com.revature.data.UserDAO;
import com.revature.dto.SprintDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ScrumServiceImpl implements ScrumService {

	private UserDAO userDao;
	private SprintDAO sprintDao;

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
	public Mono<User> removeTasks(UUID id, String username) {
		return userDao.findById(username).map(dto -> {
			dto.getTaskIds().removeIf(p -> p.equals(id));
			return dto.getUser();
		});
	}

	@Override
	public void autoUpdate() {
		Flux<SprintDTO> sprintData = sprintDao.findAll().filter(
				dto -> dto.getStatus().equals(SprintStatus.CURRENT) || dto.getStatus().equals(SprintStatus.FUTURE));
		sprintData.map(dto -> {
			if (dto.getStatus().equals(SprintStatus.CURRENT)
					&& (dto.getEndDate().isBefore(LocalDate.now()) || dto.getEndDate().isEqual(LocalDate.now()))
					&& dto.getEndTime().isBefore(LocalTime.now())) {
				dto.setStatus(SprintStatus.PAST);
				return sprintDao.save(dto);
			} else if (dto.getStatus().equals(SprintStatus.FUTURE)
					&& (dto.getStartDate().isAfter(LocalDate.now()) || dto.getStartDate().isEqual(LocalDate.now()))
					&& dto.getStartTime().isAfter(LocalTime.now())) {
				dto.setStatus(SprintStatus.FUTURE);
				return sprintDao.save(dto);
			} else {
				return dto;
			}
		});
	}
}
