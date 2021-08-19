package com.revature.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SprintHistory {

	private String name;
	private String description;
	private UUID scrumboardID;
	private UUID id;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<Task> tasks;
	private SprintStatus status;

	public SprintHistory() {
		super();
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

	public List<Task> getTaskIds() {
		return tasks;
	}

	public void setTaskIds(List<Task> tasks) {
		this.tasks = tasks;
	}

	public SprintStatus getStatus() {
		return status;
	}

	public void setStatus(SprintStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, endDate, endTime, id, name, scrumboardID, startDate, startTime, status, tasks);
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
		return Objects.equals(description, other.description) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(endTime, other.endTime) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(scrumboardID, other.scrumboardID)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(startTime, other.startTime)
				&& status == other.status && Objects.equals(tasks, other.tasks);
	}

	@Override
	public String toString() {
		return "SprintHistory [name=" + name + ", description=" + description + ", scrumboardID=" + scrumboardID
				+ ", id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", tasks=" + tasks + ", status=" + status + "]";
	}
}
