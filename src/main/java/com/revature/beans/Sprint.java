package com.revature.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Sprint {

	private UUID scrumboardID;
	private UUID id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm a")
	private LocalTime startTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm a")
	private LocalTime endTime;
	private List<UUID> taskIds;
	private SprintStatus status;

	public Sprint() {
		super();
		id = UUID.randomUUID();
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

	public List<UUID> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(List<UUID> taskIds) {
		this.taskIds = taskIds;
	}

	public SprintStatus getStatus() {
		return status;
	}

	public void setStatus(SprintStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endDate, endTime, id, scrumboardID, startDate, startTime, status, taskIds);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sprint other = (Sprint) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(id, other.id) && Objects.equals(scrumboardID, other.scrumboardID)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(startTime, other.startTime)
				&& status == other.status && Objects.equals(taskIds, other.taskIds);
	}

	@Override
	public String toString() {
		return "Sprint [scrumboardID=" + scrumboardID + ", id=" + id + ", startDate=" + startDate + ", endDate="
				+ endDate + ", startTime=" + startTime + ", endTime=" + endTime + ", taskIds=" + taskIds + ", status="
				+ status + "]";
	}

}
