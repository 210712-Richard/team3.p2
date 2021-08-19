package com.revature.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.revature.beans.UserType;

@Table("user")
public class UserDTO {

	@PrimaryKeyColumn(name = "username")
	private String username;
	@Column("password")
	private String password;
	@Column("email")
	private String email;
	@Column("firstname")
	private String firstName;
	@Column("lastname")
	private String lastName;
	@Column("type")
	private UserType type;
	@Column("boardids")
	private List<UUID> boardIds;
	@Column("taskids")
	private List<UUID> taskIds;
	@Column("productids")
	private List<UUID> productIds;

	public UserDTO() {
		super();
	}

	public UserDTO(String username, String password, String email, String firstName, String lastName, UserType type,
			List<UUID> boardIds, List<UUID> taskIds, List<UUID> productIds) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type;
		this.boardIds = boardIds;
		this.taskIds = taskIds;
		this.productIds = productIds;
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
		UserDTO other = (UserDTO) obj;
		return Objects.equals(boardIds, other.boardIds) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(productIds, other.productIds)
				&& Objects.equals(taskIds, other.taskIds) && type == other.type
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", password=" + password + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", type=" + type + ", boardIds=" + boardIds + ", taskIds="
				+ taskIds + ", productIds=" + productIds + "]";
	}
}
