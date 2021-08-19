package com.revature.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Table;

import com.revature.beans.Product;

@Table
public class ProductDTO {
	
	private String productOwner;
	private Map<String, UUID> scrumMasterBoardMap;
	private List<UUID> boardIds;
	private List<String> usernames;
	private Map<UUID, String> boardIdNameMap;
	private String productName;
	
	public ProductDTO(Product product) {
		super();
	}


	
	
	public String getProductOwner() {
		return productOwner;
	}
	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}
	public Map<String, UUID> getScrumMasterBoardMap() {
		return scrumMasterBoardMap;
	}
	public void setScrumMasterBoardMap(Map<String, UUID> scrumMasterBoardMap) {
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
	private UUID id;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boardIdNameMap == null) ? 0 : boardIdNameMap.hashCode());
		result = prime * result + ((boardIds == null) ? 0 : boardIds.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + ((productOwner == null) ? 0 : productOwner.hashCode());
		result = prime * result + ((scrumMasterBoardMap == null) ? 0 : scrumMasterBoardMap.hashCode());
		result = prime * result + ((usernames == null) ? 0 : usernames.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		if (boardIdNameMap == null) {
			if (other.boardIdNameMap != null)
				return false;
		} else if (!boardIdNameMap.equals(other.boardIdNameMap))
			return false;
		if (boardIds == null) {
			if (other.boardIds != null)
				return false;
		} else if (!boardIds.equals(other.boardIds))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (productOwner == null) {
			if (other.productOwner != null)
				return false;
		} else if (!productOwner.equals(other.productOwner))
			return false;
		if (scrumMasterBoardMap == null) {
			if (other.scrumMasterBoardMap != null)
				return false;
		} else if (!scrumMasterBoardMap.equals(other.scrumMasterBoardMap))
			return false;
		if (usernames == null) {
			if (other.usernames != null)
				return false;
		} else if (!usernames.equals(other.usernames))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ProductDTO [productOwner=" + productOwner + ", scrumMasterBoardMap=" + scrumMasterBoardMap
				+ ", boardIds=" + boardIds + ", usernames=" + usernames + ", boardIdNameMap=" + boardIdNameMap
				+ ", productName=" + productName + ", id=" + id + "]";
	}

	
	
}
