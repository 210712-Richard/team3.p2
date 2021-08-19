package com.revature.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Product {

	private String productOwner;
	private HashMap<String, UUID> ScrumMasterBoardMap;
	private List<UUID> boardIds;
	private List<String> usernames;
	private HashMap<UUID, String> boardIdNameMap;
	private String productName;
	private UUID id;

	public Product() {
		super();
	}

	public String getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}

	public HashMap<String, UUID> getScrumMasterBoardMap() {
		return ScrumMasterBoardMap;
	}

	public void setScrumMasterBoardMap(HashMap<String, UUID> scrumMasterBoardMap) {
		ScrumMasterBoardMap = scrumMasterBoardMap;
	}

	public List<UUID> getBoardIds() {
		return boardIds;
	}

	public void setBoardIds(List<UUID> boardIds) {
		this.boardIds = boardIds;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

	public HashMap<UUID, String> getBoardIdNameMap() {
		return boardIdNameMap;
	}

	public void setBoardIdNameMap(HashMap<UUID, String> boardIdNameMap) {
		this.boardIdNameMap = boardIdNameMap;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ScrumMasterBoardMap, boardIdNameMap, boardIds, id, productName, productOwner, usernames);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(ScrumMasterBoardMap, other.ScrumMasterBoardMap)
				&& Objects.equals(boardIdNameMap, other.boardIdNameMap) && Objects.equals(boardIds, other.boardIds)
				&& Objects.equals(id, other.id) && Objects.equals(productName, other.productName)
				&& Objects.equals(productOwner, other.productOwner) && Objects.equals(usernames, other.usernames);
	}

	@Override
	public String toString() {
		return "Product [productOwner=" + productOwner + ", ScrumMasterBoardMap=" + ScrumMasterBoardMap + ", boardIds="
				+ boardIds + ", usernames=" + usernames + ", boardIdNameMap=" + boardIdNameMap + ", productName="
				+ productName + ", id=" + id + "]";
	}

}
