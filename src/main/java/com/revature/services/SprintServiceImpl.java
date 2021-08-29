package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Sprint;
import com.revature.beans.SprintHistory;
import com.revature.beans.SprintStatus;
import com.revature.beans.TaskCompletionStatus;
import com.revature.data.SprintDAO;
import com.revature.data.TaskDAO;
import com.revature.dto.SprintDTO;
import com.revature.dto.TaskDTO;
import com.revature.util.S3Util;

import reactor.core.publisher.Flux;
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
	private TaskDAO taskDao;
	private static final Logger log = LoggerFactory.getLogger(SprintService.class);

	@Autowired
	public SprintServiceImpl(SprintDAO sprintDao, S3Service s3, TaskDAO taskDao) {
		this.sprintDao = sprintDao;
		this.s3 = s3;
		this.taskDao = taskDao;
	}

	@Override
	public Mono<Sprint> createSprint(Sprint sprint) {

		return sprintDao.insert(new SprintDTO(sprint)).log().map(dto -> dto.getSprint());
	}

	@Override
	public Mono<Sprint> retireCurrentSprint(UUID scrumboardID) {

		return sprintDao.findByScrumboardIDAndStatus(scrumboardID, SprintStatus.CURRENT).flatMap(dto -> {
			sprintDao.delete(dto).subscribe();
			dto.setStatus(SprintStatus.PAST);
			Flux<TaskDTO> tasks = taskDao.findAll()
					.filter(task -> dto.getTaskIds().contains(task.getId()))
					.map(t -> {
				taskDao.delete(t);
				return t;
			});
			List<TaskDTO> taskList = new ArrayList<>();
			tasks.subscribe(it -> new Consumer<TaskDTO>() {

				@Override
				public void accept(TaskDTO t) {
					taskList.add(t);
				}
			});
			tasks.subscribe(task -> new Consumer<TaskDTO>() {

				@Override
				public void accept(TaskDTO t) {
					if (!task.getStatus().equals(TaskCompletionStatus.COMPLETED)) {
						task.setStatus(TaskCompletionStatus.BACKLOG);
						log.warn(task.toString());
						taskDao.save(task).subscribe();
					}
				}
			});
			SprintHistory hist = new SprintHistory(taskList, dto);
			s3.uploadToBucket(dto.getId().toString(), hist, S3Util.SPRINT_BUCKET_NAME).subscribe();
			return sprintDao.insert(dto);
		}).map(saved -> saved.getSprint());
	}

	@Override
	public Mono<Sprint> changeEndSprint(Sprint sprint, UUID scrumboardID) {
		// TODO Auto-generated method stub
		return null;
	}

}