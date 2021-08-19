package com.revature.dto;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.revature.beans.Notification;

@Table("notifications")
public class NotificationDTO {

	@PrimaryKeyColumn(name = "username", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String username;
	@PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private UUID id;
	@Column
	private String message;
	
	public NotificationDTO() {
		
	}
	
	public NotificationDTO(Notification note) {
		this.username = note.getUsername();
		this.id = note.getId();
		this.message = note.getMessage();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Notification getNotification() {
		Notification note = new Notification();
		note.setUsername(this.username);
		note.setId(this.id);
		note.setMessage(this.message);
		return note;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, message, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationDTO other = (NotificationDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(message, other.message)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "NotificationDTO [username=" + username + ", id=" + id + ", message=" + message + "]";
	}
}
