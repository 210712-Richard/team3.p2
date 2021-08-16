package com.revature.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.revature.beans.Task;
import com.revature.beans.TaskCompletionStatus;

@Table("task")
public class TaskDTO {
	@PrimaryKey
	@Column("id")
	private UUID id;
	@Column
	private String name;
	private String description;
	private TaskCompletionStatus status;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public TaskDTO() {
		
	}
	
	public TaskDTO(Task task) {
		this.id = task.getId();
		this.name = task.getName();
		this.description = task.getDescription();
		this.status = task.getStatus();
		this.startDate = task.getStartDate();
		this.endDate = task.getEndDate();
		this.startTime = task.getStartTime();
		this.endTime = task.getEndTime();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public TaskCompletionStatus getStatus() {
		return status;
	}

	public void setStatus(TaskCompletionStatus status) {
		this.status = status;
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
		return Objects.hash(description, endDate, endTime, id, name, startDate, startTime, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskDTO other = (TaskDTO) obj;
		return Objects.equals(description, other.description) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(endTime, other.endTime) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(startTime, other.startTime) && status == other.status;
	}

	@Override
	public String toString() {
		return "TaskDTO [id=" + id + ", name=" + name + ", description=" + description + ", status=" + status
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}
	
	
	
}
