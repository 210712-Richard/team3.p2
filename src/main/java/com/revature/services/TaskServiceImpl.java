package com.revature.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;
import com.revature.beans.TaskPriority;
import com.revature.data.SprintDAO;
import com.revature.data.TaskDAO;
import com.revature.data.UserDAO;
import com.revature.dto.SprintDTO;
import com.revature.dto.TaskDTO;
import com.revature.dto.UserDTO;

@Service
public class TaskServiceImpl implements TaskService{

	private static Logger log = LogManager.getLogger(TaskServiceImpl.class);

	private TaskDAO taskDAO;
	private SprintDAO sprintDAO;
	private UserDAO userDAO;
	
	
	@Autowired
	public TaskServiceImpl(TaskDAO taskDAO) {
		super();
		this.taskDAO = taskDAO;
	}
	
	
	@Override
	public void moveTask(UUID taskId, TaskCompletionStatus status) {
		//Move task within scrumboard by changing the status
		Optional<TaskDTO> t = taskDAO.findById(taskId.toString());
		TaskDTO task = t.get();
		if(t.get() == null) {
			return;
		}
		task.setStatus(status);
		taskDAO.save(task);
	}

	@Override
	public void addToProductBackLog(UUID product, Task task) {
		//New task added to product backlog
		/*
		 * How do i do this without a list of tasks in product object
		 */
		
		
	}

	@Override
	public void makePriority(UUID taskId, TaskPriority priority) {
		//Change priority status of an existing task
		Optional<TaskDTO> t = taskDAO.findById(taskId.toString());
		if(t.get() == null) {
			return;
		}
		TaskDTO task = t.get();
		task.setPriorityStatus(priority);
		taskDAO.save(task);
	}

	@Override
	public void addToSprintBackLog(UUID sprintId, UUID taskId) {
		//Find Task set status to backlog
		Optional<TaskDTO> t = taskDAO.findById(taskId.toString());
		if(t.get() == null) {
			return;
		}
		TaskDTO task = t.get();
		task.setStatus(TaskCompletionStatus.BACKLOG);
		
		//Find sprint and add this task to the sprint's list of tasks
		Optional<SprintDTO> sp = sprintDAO.findById(sprintId.toString());
		if(sp.get() == null) {
			return;
		}
		SprintDTO sprint = sp.get();
		sprint.getTaskIds().add(sprintId);
		sprintDAO.save(sprint);
		
		
	}

	@Override
	public void assignTasks(UUID userId, UUID taskId) {
		//Maybe put this under UserService since its updating a user's task list
		
		/*

		Optional<UserDTO> u = userDAO.findById(userId.toString());
		if(u.get() == null) {
			return;
		}
		UserDTO user = u.get();
		user.getTaskIds().add(taskId);
		userDAO.save(user);
		
		*/
		
		//errors since DAO isn't set up
	}

}
