package com.revature.beans;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Product {

	private String productOwner;
	private Map<UUID, String> scrumMasterBoardMap;
	private List<UUID> boardIds;
	private List<String> usernames;
	private Map<UUID, String> boardIdNameMap;
	private String productName;
	private UUID id;
	private UUID masterBoardId;

	public Product() {
		super();
	}
	

	public String getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}

	public Map<UUID, String> getScrumMasterBoardMap() {
		return scrumMasterBoardMap;
	}

	public void setScrumMasterBoardMap(Map<UUID, String> scrumMasterBoardMap) {
		this.scrumMasterBoardMap = scrumMasterBoardMap;
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

	public Map<UUID, String> getBoardIdNameMap() {
		return boardIdNameMap;
	}

	public void setBoardIdNameMap(Map<UUID, String> boardIdNameMap) {
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

	public UUID getMasterBoardId() {
		return masterBoardId;
	}

	public void setMasterBoardId(UUID masterBoardId) {
		this.masterBoardId = masterBoardId;
	}


	@Override
	public int hashCode() {
		return Objects.hash(boardIdNameMap, boardIds, id, masterBoardId, productName, productOwner, scrumMasterBoardMap,
				usernames);
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
		return Objects.equals(boardIdNameMap, other.boardIdNameMap) && Objects.equals(boardIds, other.boardIds)
				&& Objects.equals(id, other.id) && Objects.equals(masterBoardId, other.masterBoardId)
				&& Objects.equals(productName, other.productName) && Objects.equals(productOwner, other.productOwner)
				&& Objects.equals(scrumMasterBoardMap, other.scrumMasterBoardMap)
				&& Objects.equals(usernames, other.usernames);
	}


	@Override
	public String toString() {
		return "Product [productOwner=" + productOwner + ", scrumMasterBoardMap=" + scrumMasterBoardMap + ", boardIds="
				+ boardIds + ", usernames=" + usernames + ", boardIdNameMap=" + boardIdNameMap + ", productName="
				+ productName + ", id=" + id + ", masterBoardId=" + masterBoardId + "]";
	}

}
