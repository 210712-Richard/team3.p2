package com.revature.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Sprint;
import com.revature.data.SprintDAO;
import com.revature.dto.SprintDTO;

import reactor.core.publisher.Mono;
/**
 * 
 * @author MuckJosh
 *
 */
@Service
public class SprintServiceImpl implements SprintService {

	private SprintDAO sprintDao;
	
	@Autowired
	public SprintServiceImpl(SprintDAO sprintDao) {
		this.sprintDao = sprintDao;
	}
	
	@Override
	public Mono<Sprint> createSpring(Sprint sprint) {
	
		return sprintDao.insert(new SprintDTO(sprint)).map(s -> s.getSprint());
	}

	@Override
	public Mono<Sprint> retireCurrentSprint(UUID boardId) {
		
		return null;//sprintDao.findByBoardIdAndStatus(boardId, SprintStatus.CURRENT);
	}

}
