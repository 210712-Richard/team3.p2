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

	@PrimaryKeyColumn(name = "scrumboardId", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private UUID scrumboardID;
	@Column("id") 
	private UUID id;
	@Column("name") 
	private String name;
	@Column("description") 
	private String description;
	@Column("startdate") 
	private LocalDate startDate;
	@Column("enddate") 
	private LocalDate endDate;
	@Column("starttime") 
	private LocalTime startTime;
	@Column("endTime") 
	private LocalTime endTime;
	@Column("taskids")
	private List<UUID> taskIds;
	@PrimaryKeyColumn(name = "status", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private SprintStatus status;

	public SprintDTO() {
		super();
	}
	
	public SprintDTO(Sprint sprint) {
		this.scrumboardID = sprint.getScrumboardID();
		this.id = sprint.getId();
		this.name = sprint.getName();
		this.description = sprint.getDescription();
		this.startDate = sprint.getStartDate();
		this.endDate = sprint.getEndDate();
		this.startTime = sprint.getStartTime();
		this.endTime = sprint.getEndTime();
	}
	
	public Sprint getSprint() {
		Sprint sprint = new Sprint();
		sprint.setScrumboardID(this.scrumboardID);
		sprint.setId(this.getId());
		sprint.setName(this.name);
		sprint.setDescription(this.description);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return Objects.hash(description, endDate, endTime, id, name, scrumboardID, startDate, startTime, status,
				taskIds);
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
		return Objects.equals(description, other.description) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(endTime, other.endTime) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(scrumboardID, other.scrumboardID)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(startTime, other.startTime)
				&& status == other.status && Objects.equals(taskIds, other.taskIds);
	}

	@Override
	public String toString() {
		return "SprintDTO [scrumboardID=" + scrumboardID + ", id=" + id + ", name=" + name + ", description="
				+ description + ", startDate=" + startDate + ", endDate=" + endDate + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", taskIds=" + taskIds + ", status=" + status + "]";
	}

}
