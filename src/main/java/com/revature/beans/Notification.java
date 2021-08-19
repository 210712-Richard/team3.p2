package com.revature.beans;

import java.util.Objects;
import java.util.UUID;

public class Notification {

	private String message;
	private String username;
	private UUID id;

	public Notification() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
		Notification other = (Notification) obj;
		return Objects.equals(id, other.id) && Objects.equals(message, other.message)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "Notification [message=" + message + ", username=" + username + ", id=" + id + "]";
	}

}
