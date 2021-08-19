package com.revature.beans;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User {

	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private UserType type;
	private List<UUID> boardIds;
	private List<UUID> taskIds;
	private List<UUID> productIds;

	public User() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public List<UUID> getBoardIds() {
		return boardIds;
	}

	public void setBoardIds(List<UUID> boardIds) {
		this.boardIds = boardIds;
	}

	public List<UUID> getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(List<UUID> taskIds) {
		this.taskIds = taskIds;
	}

	public List<UUID> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<UUID> productIds) {
		this.productIds = productIds;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardIds, email, firstName, lastName, password, productIds, taskIds, type, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(boardIds, other.boardIds) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(productIds, other.productIds)
				&& Objects.equals(taskIds, other.taskIds) && type == other.type
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", type=" + type + ", boardIds=" + boardIds + ", taskIds=" + taskIds
				+ ", productIds=" + productIds + "]";
	}

}
