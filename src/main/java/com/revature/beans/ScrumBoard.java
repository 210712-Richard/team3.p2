package com.revature.beans;

import java.util.Objects;
import java.util.UUID;

public class ScrumBoard {

	private String name;
	private UUID id;
	private String scrumMasterUsername;
	private UUID productId;
	
	public ScrumBoard() {
		super();
		id = UUID.randomUUID();
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
	
	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, productId, scrumMasterUsername);
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
				&& Objects.equals(productId, other.productId)
				&& Objects.equals(scrumMasterUsername, other.scrumMasterUsername);
	}

	@Override
	public String toString() {
		return "ScrumBoard [name=" + name + ", id=" + id + ", scrumMasterUsername=" + scrumMasterUsername
				+ ", productId=" + productId + "]";
	}
}
