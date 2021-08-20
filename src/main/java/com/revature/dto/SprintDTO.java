package com.revature.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.revature.beans.Sprint;
import com.revature.beans.SprintStatus;

@Table("sprints")
public class SprintDTO {

	@PrimaryKeyColumn(name = "scrumboard_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private UUID scrumboardID;
	@Column("sprint_id") 
	private UUID id;
	@Column("start_date") 
	private LocalDate startDate;
	@Column("end_date") 
	private LocalDate endDate;
	@Column("start_time") 
	private LocalTime startTime;
	@Column("end_time") 
	private LocalTime endTime;
	@Column("task_ids")
	private List<UUID> taskIds;
	@PrimaryKeyColumn(name = "status", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private SprintStatus status;

	public SprintDTO() {
		super();
	}
	
	public SprintDTO(Sprint sprint) {
		this.scrumboardID = sprint.getScrumboardID();
		this.id = sprint.getId();
		this.startDate = sprint.getStartDate();
		this.endDate = sprint.getEndDate();
		this.startTime = sprint.getStartTime();
		this.endTime = sprint.getEndTime();
	}
	
	public Sprint getSprint() {
		Sprint sprint = new Sprint();
		sprint.setScrumboardID(this.scrumboardID);
		sprint.setId(this.getId());
		sprint.setStartDate(this.startDate);
		sprint.setEndDate(this.endDate);
		sprint.setStartTime(this.startTime);
		sprint.setEndTime(this.endTime);
		
		return sprint;
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
		SprintDTO other = (SprintDTO) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(endTime, other.endTime)
				&& Objects.equals(id, other.id) && Objects.equals(scrumboardID, other.scrumboardID)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(startTime, other.startTime)
				&& status == other.status && Objects.equals(taskIds, other.taskIds);
	}

	@Override
	public String toString() {
		return "SprintDTO [scrumboardID=" + scrumboardID + ", id=" + id + ", startDate=" + startDate + ", endDate="
				+ endDate + ", startTime=" + startTime + ", endTime=" + endTime + ", taskIds=" + taskIds + ", status="
				+ status + "]";
	}

}
