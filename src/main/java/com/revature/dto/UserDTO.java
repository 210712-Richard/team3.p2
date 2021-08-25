package com.revature.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.revature.beans.User;
import com.revature.beans.UserType;

@Table("users")
public class UserDTO {

	@PrimaryKey("username")
	@Column
	private String username;
	@Column("password")
	private String password;
	@Column("email")
	private String email;
	private UserType type;
	@Column
	private List<UUID> boardids;
	@Column
	private List<UUID> taskids;
	@Column
	private List<UUID> productids;

	public UserDTO() {
		this.type = UserType.DEVELOPER;
		this.boardids = new ArrayList<>();
		this.taskids = new ArrayList<>();
		this.productids = new ArrayList<>();
	}


	public UserDTO(User user) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.type = user.getType();
		this.boardids = user.getBoardIds();
		this.taskids = user.getTaskIds();
		this.productids = user.getProductIds();
	}
	
	public User getUser() {
		User u = new User();
		u.setUsername(this.username);
		u.setPassword(this.password);
		u.setEmail(this.email);
		u.setType(this.type);
		u.setBoardIds(this.boardids);
		u.setTaskIds(this.taskids);
		u.setProductIds(this.productids);
		return u;
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


	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public List<UUID> getBoardIds() {
		if(boardids == null)
			return new ArrayList<>();
		return boardids;
	}

	public void setBoardIds(List<UUID> boardIds) {
		this.boardids = boardIds;
	}

	public List<UUID> getTaskIds() {
		if(taskids == null)
			return new ArrayList<>();
		return taskids;
	}

	public void setTaskIds(List<UUID> taskIds) {
		this.taskids = taskIds;
	}

	public List<UUID> getProductIds() {
		if(productids == null)
			return new ArrayList<>();
		return productids;
	}

	public void setProductIds(List<UUID> productIds) {
		this.productids = productIds;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardids, email, password, productids, taskids, type, username);
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
		return Objects.equals(boardids, other.boardids) && Objects.equals(email, other.email)
				&& Objects.equals(password, other.password) && Objects.equals(productids, other.productids)
				&& Objects.equals(taskids, other.taskids) && type == other.type
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", password=" + password + ", email=" + email + ", type=" + type
				+ ", boardIds=" + boardids + ", taskIds=" + taskids + ", productIds=" + productids + "]";
	}

	
}
