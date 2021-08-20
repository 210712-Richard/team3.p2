package com.revature.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.revature.beans.User;
import com.revature.beans.UserType;

@Table("user")
public class UserDTO {

	@PrimaryKey("username")
	private String username;
	@Column("password")
	private String password;
	@Column("email")
	private String email;
	@Column("type")
	private UserType type;
	@Column("board_ids")
	private List<UUID> boardIds;
	@Column("task_ids")
	private List<UUID> taskIds;
	@Column("product_ids")
	private List<UUID> productIds;

	public UserDTO() {
		super();
		this.type = UserType.DEVELOPER;
		this.boardIds = new ArrayList<UUID>();
		this.taskIds = new ArrayList<UUID>();
		this.productIds = new ArrayList<UUID>();
	}
	
	@Autowired
	public UserDTO(String username, String password, String email, UserType type, List<UUID> boardIds,
			List<UUID> taskIds, List<UUID> productIds) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.type = type;
		this.boardIds = boardIds;
		this.taskIds = taskIds;
		this.productIds = productIds;
	}


	public UserDTO(User user) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.type = user.getType();
		this.boardIds = user.getBoardIds();
		this.taskIds = user.getTaskIds();
		this.productIds = user.getProductIds();
	}
	
	public User getUser() {
		User u = new User();
		u.setUsername(this.username);
		u.setPassword(this.password);
		u.setEmail(this.email);
		u.setType(this.type);
		u.setBoardIds(this.boardIds);
		u.setTaskIds(this.taskIds);
		u.setProductIds(this.productIds);
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
		return Objects.hash(boardIds, email, password, productIds, taskIds, type, username);
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
				&& Objects.equals(password, other.password) && Objects.equals(productIds, other.productIds)
				&& Objects.equals(taskIds, other.taskIds) && type == other.type
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", password=" + password + ", email=" + email + ", type=" + type
				+ ", boardIds=" + boardIds + ", taskIds=" + taskIds + ", productIds=" + productIds + "]";
	}

	
}
