package com.revature.beans;

import java.util.Objects;
import java.util.UUID;

public class ScrumBoard {

	private String name;
	private UUID id;
	private String ScrumMasterUsername;
	
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
		return ScrumMasterUsername;
	}

	public void setScrumMasterUsername(String scrumMasterUsername) {
		ScrumMasterUsername = scrumMasterUsername;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ScrumMasterUsername, id, name);
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
		return Objects.equals(ScrumMasterUsername, other.ScrumMasterUsername) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "ScrumBoard [name=" + name + ", id=" + id + ", ScrumMasterUsername=" + ScrumMasterUsername + "]";
	}
	
	

}
