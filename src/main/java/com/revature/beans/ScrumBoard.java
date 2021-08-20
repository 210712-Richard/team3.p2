package com.revature.beans;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ScrumBoard {

	private String name;
	private UUID id;
	private String scrumMasterUsername;
	private List<String> users;
	
	public ScrumBoard() {
		super();
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

	public String getScrumMasterUsername() {
		return scrumMasterUsername;
	}

	public void setScrumMasterUsername(String scrumMasterUsername) {
		this.scrumMasterUsername = scrumMasterUsername;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, scrumMasterUsername, users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScrumBoard other = (ScrumBoard) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(scrumMasterUsername, other.scrumMasterUsername) && Objects.equals(users, other.users);
	}

	@Override
	public String toString() {
		return "ScrumBoard [name=" + name + ", id=" + id + ", scrumMasterUsername=" + scrumMasterUsername + ", users="
				+ users + "]";
	}
}
