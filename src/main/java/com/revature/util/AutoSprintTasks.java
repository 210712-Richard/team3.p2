package com.revature.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.revature.beans.SprintHistory;
import com.revature.beans.SprintStatus;
import com.revature.beans.TaskCompletionStatus;
import com.revature.data.SprintDAO;
import com.revature.data.TaskDAO;
import com.revature.dto.TaskDTO;
import com.revature.services.S3Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AutoSprintTasks {

	private static final Logger log = LoggerFactory.getLogger(AutoSprintTasks.class);
	private SprintDAO sprintDao;
	private S3Service s3;
	private TaskDAO taskDao;
	
	@Autowired
	public AutoSprintTasks(SprintDAO sprintDao, S3Service s3, TaskDAO taskDao) {
		this.sprintDao = sprintDao;
		this.s3 = s3;
		this.taskDao = taskDao;
	}

	@Scheduled(fixedDelay = 5000)
	public void autoCompleteSprint() {
		log.warn("excuting sprint completes");

		List<TaskDTO> taskList = new ArrayList<>();

		sprintDao.findAll().flatMap(dto -> {
			try {
				log.info(dto.toString());
			if (dto.getStatus().equals(SprintStatus.CURRENT) && ((dto.getEndDate().isBefore(LocalDate.now()) 
					|| (dto.getEndDate().isEqual(LocalDate.now()) && dto.getEndTime().isBefore(LocalTime.now()))))) {
				sprintDao.delete(dto).subscribe();
				dto.setStatus(SprintStatus.PAST);
				Flux<TaskDTO> tasks = taskDao.findAll().filter(task -> dto.getTaskIds().contains(task.getId()));
				tasks.subscribe(it -> taskList.add(it));
				tasks.subscribe(task -> taskStatusChecker(task));
				SprintHistory hist = new SprintHistory(taskList, dto);
				s3.uploadToBucket(dto.getId().toString(), hist, S3Util.SPRINT_BUCKET_NAME).subscribe();
				return sprintDao.save(dto);
			} else if (dto.getStatus().equals(SprintStatus.FUTURE) && 
					((dto.getStartDate().isBefore(LocalDate.now()) 
							|| (dto.getStartDate().equals(LocalDate.now()) && (dto.getStartTime().equals(LocalTime.now()) || dto.getStartTime().isBefore(LocalTime.now())))))) {
				sprintDao.delete(dto).subscribe();
				dto.setStatus(SprintStatus.CURRENT);
				return sprintDao.save(dto);
			} else {
				return Mono.empty();
			}
			}catch(NullPointerException e) {
				return Mono.empty();
			}
		}).subscribe(f -> log.warn("complete " +f.toString()));
	}
	
	private void taskStatusChecker(TaskDTO task) {
		if(!task.getStatus().equals(TaskCompletionStatus.COMPLETED)) {
			task.setStatus(TaskCompletionStatus.BACKLOG);
			log.warn(task.toString());
			taskDao.save(task).subscribe();
		}
		
	}
}
