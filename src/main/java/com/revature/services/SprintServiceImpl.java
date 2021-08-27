package com.revature.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Sprint;
import com.revature.beans.SprintStatus;
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
	private S3Service s3;
	private SprintDAO sprintDao;
	
	@Autowired
	public SprintServiceImpl(SprintDAO sprintDao, S3Service s3) {
		this.sprintDao = sprintDao;
		this.s3 = s3;
	}
	
	@Override
	public Mono<Sprint> createSprint(Sprint sprint) {
	//check if there is a future/current for which status you are inserting.
		//date overlaps?
		return sprintDao.insert(new SprintDTO(sprint)).map(s -> s.getSprint());
	}

	@Override
	public Mono<Sprint> retireCurrentSprint(UUID scrumboardID) {
		
		return sprintDao.findByScrumboardIDAndStatus(scrumboardID, SprintStatus.CURRENT)
				.flatMap(dto ->{
				sprintDao.delete(dto).subscribe();
			dto.setStatus(SprintStatus.PAST);
			s3.uploadToBucket(dto.getId().toString(), dto);
			return sprintDao.save(dto);
		}).map(saved -> saved.getSprint());
	}

	@Override
	public Mono<Sprint> changeEndSprint(Sprint sprint, UUID scrumboardID) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
