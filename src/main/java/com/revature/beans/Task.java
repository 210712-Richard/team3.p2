package com.revature.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Task {

	private UUID boardId;
	private String name;
	private String description;
	private UUID id;
	private TaskCompletionStatus status;
	private TaskPriority priority;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;

	public Task() {
		super();
	}
	
	public UUID getBoardId() {
		return boardId;
	}

	public void setBoardId(UUID boardId) {
		this.boardId = boardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public TaskCompletionStatus getStatus() {
		return status;
	}

	public void setStatus(TaskCompletionStatus status) {
		this.status = status;
	}

	public TaskPriority getPriorityStatus() {
		return priority;
	}

	public void setPriorityStatus(TaskPriority priorityStatus) {
		this.priority = priorityStatus;
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

	@Override
	public int hashCode() {
		return Objects.hash(boardId, description, endDate, endTime, id, name, priority, startDate, startTime,
				status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(boardId, other.boardId) && Objects.equals(description, other.description)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& priority == other.priority && Objects.equals(startDate, other.startDate)
				&& Objects.equals(startTime, other.startTime) && status == other.status;
	}

	@Override
	public String toString() {
		return "Task [boardId=" + boardId + ", name=" + name + ", description=" + description + ", id=" + id
				+ ", status=" + status + ", priorityStatus=" + priority + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
