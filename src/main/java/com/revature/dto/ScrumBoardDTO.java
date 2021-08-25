package com.revature.dto;

import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.revature.beans.ScrumBoard;

@Table("scrumboards")
public class ScrumBoardDTO {

	@PrimaryKeyColumn(name = "boardid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private UUID boardId;
	@Column("name")
	private String name;
	@Column("scrummaster")
	private String scrumMaster;
	@Column("productid")
	private UUID productId;

	public ScrumBoardDTO() {
		super();
	}
	
	public ScrumBoardDTO(ScrumBoard board) {
		super();
		this.boardId = board.getId();
		this.name = board.getName();
		this.scrumMaster = board.getScrumMasterUsername();
	}

	public ScrumBoard getScrumBoard() {
		ScrumBoard board = new ScrumBoard();
		board.setId(boardId);
		board.setName(name);
		board.setScrumMasterUsername(scrumMaster);
		return board;
	}

	public UUID getBoardId() {
		return boardId;
	}

	public void setBoardId(UUID boardId) {
		this.boardId = boardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScrumMaster() {
		return scrumMaster;
	}

	public void setScrumMaster(String scrumMaster) {
		this.scrumMaster = scrumMaster;
	}

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardId, name, productId, scrumMaster);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScrumBoardDTO other = (ScrumBoardDTO) obj;
		return Objects.equals(boardId, other.boardId) && Objects.equals(name, other.name)
				&& Objects.equals(productId, other.productId) && Objects.equals(scrumMaster, other.scrumMaster);
	}

	@Override
	public String toString() {
		return "ScrumBoardDTO [boardId=" + boardId + ", name=" + name + ", scrumMaster=" + scrumMaster + ", productId="
				+ productId + "]";
	}

}
