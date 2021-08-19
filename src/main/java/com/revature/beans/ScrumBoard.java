package com.revature.beans;

import java.util.Objects;
import java.util.UUID;

public class ScrumBoard {

	private String name;
	private UUID id;
	private String scrumMasterUsername;
	
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

	@Override
	public int hashCode() {
		return Objects.hash(scrumMasterUsername, id, name);
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
		return Objects.equals(scrumMasterUsername, other.scrumMasterUsername) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "ScrumBoard [name=" + name + ", id=" + id + ", ScrumMasterUsername=" + scrumMasterUsername + "]";
	}
	
	

}
