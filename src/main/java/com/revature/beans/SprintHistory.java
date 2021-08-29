package com.revature.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.revature.dto.SprintDTO;
import com.revature.dto.TaskDTO;

public class SprintHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6672932901510155434L;
	private UUID scrumboardID;
	private UUID id;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<TaskDTO> tasks;
	private SprintStatus status;

	public SprintHistory() {
		super();
	}
	public SprintHistory(List<TaskDTO> taskList, SprintDTO dto) {
		this.scrumboardID = dto.getScrumboardID();
		this.id = dto.getId();
		this.startDate = dto.getStartDate();
		this.endDate = dto.getEndDate();
		this.startTime = dto.getStartTime();
		this.endTime = dto.getEndTime();
		this.status = dto.getStatus();
		this.tasks = taskList;
	}

	
	public List<TaskDTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskDTO> tasks) {
		this.tasks = tasks;
	}

	public UUID getScrumboardID() {
		return scrumboardID;
	}

	public void setScrumboardID(UUID scrumboardID) {
		this.scrumboardID = scrumboardID;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public SprintStatus getStatus() {
		return status;
	}

	public void setStatus(SprintStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endDate, endTime, id, scrumboardID, startDate, startTime, status, tasks);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SprintHistory other = (SprintHistory) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(id, other.id) && Objects.equals(scrumboardID, other.scrumboardID)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(startTime, other.startTime)
				&& status == other.status && Objects.equals(tasks, other.tasks);
	}

	@Override
	public String toString() {
		return "SprintHistory [scrumboardID=" + scrumboardID + ", id=" + id + ", startDate=" + startDate + ", endDate="
				+ endDate + ", startTime=" + startTime + ", endTime=" + endTime + ", tasks=" + tasks + ", status="
				+ status + "]";
	}
}
