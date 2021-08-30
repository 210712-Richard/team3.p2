package com.revature.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.revature.beans.Product;

@Table("products")
public class ProductDTO {
	@PrimaryKeyColumn(name = "productid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private UUID productid;
	@Column("productowner")
	private String productOwner;
	@Column("boardidtoscrumusername")
	private Map<UUID, String> scrumMasterBoardMap;
	@Column("scrumboards")
	private List<UUID> boardIds;
	@Column("users")
	private List<String> usernames;
	@Column("boardidtoboardname")
	private Map<UUID, String> boardIdNameMap;
	@Column("productname")
	private String productName;
	@Column("masterboardid")
	private UUID masterBoardId;
	
	public ProductDTO() {
		
	}

	public ProductDTO(Product product) {
		super();
		this.productid = product.getId();
		this.productOwner = product.getProductOwner();
		this.scrumMasterBoardMap = product.getScrumMasterBoardMap();
		this.boardIds = product.getBoardIds();
		this.usernames = product.getUsernames();
		this.boardIdNameMap = product.getBoardIdNameMap();
		this.productName = product.getProductName();
		this.masterBoardId = product.getMasterBoardId();
	}
	
	public Product getProduct() {
		Product product = new Product();
		product.setId(productid);
		product.setProductOwner(productOwner);
		product.setScrumMasterBoardMap(scrumMasterBoardMap);
		product.setBoardIds(boardIds);
		product.setUsernames(usernames);
		product.setBoardIdNameMap(boardIdNameMap);
		product.setProductName(productName);
		product.setMasterBoardId(masterBoardId);
		return product;
	}
	
	public String getProductOwner() {
		return productOwner;
	}
	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}
	public Map<UUID, String> getScrumMasterBoardMap() {
		if (scrumMasterBoardMap == null) {
			return new HashMap<>();
		} else {
			return scrumMasterBoardMap;
		}
	}
	public void setScrumMasterBoardMap(Map<UUID, String> scrumMasterBoardMap) {
		this.scrumMasterBoardMap = scrumMasterBoardMap;
	}
	public List<UUID> getBoardIds() {
		if (boardIds == null) {
			return new ArrayList<>();
		} else {
			return boardIds;
		}
	}
	public void setBoardIds(List<UUID> boardIds) {
		this.boardIds = boardIds;
	}
	public List<String> getUsernames() {
		if (usernames == null) {
			return new ArrayList<>();
		} else {
			return usernames;
		}
	}
	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	public Map<UUID, String> getBoardIdNameMap() {
		if (boardIdNameMap == null) {
			return new HashMap<>();
		} else {
			return boardIdNameMap;
		}
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
		return productid;
	}
	public void setId(UUID id) {
		this.productid = id;
	}

	public UUID getMasterBoardId() {
		return masterBoardId;
	}

	public void setMasterBoardId(UUID masterBoardId) {
		this.masterBoardId = masterBoardId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardIdNameMap, boardIds, productid, masterBoardId, productName, productOwner, scrumMasterBoardMap,
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
		ProductDTO other = (ProductDTO) obj;
		return Objects.equals(boardIdNameMap, other.boardIdNameMap) && Objects.equals(boardIds, other.boardIds)
				&& Objects.equals(productid, other.productid) && Objects.equals(masterBoardId, other.masterBoardId)
				&& Objects.equals(productName, other.productName) && Objects.equals(productOwner, other.productOwner)
				&& Objects.equals(scrumMasterBoardMap, other.scrumMasterBoardMap)
				&& Objects.equals(usernames, other.usernames);
	}
	@Override
	public String toString() {
		return "ProductDTO [id=" + productid + ", productOwner=" + productOwner + ", scrumMasterBoardMap="
				+ scrumMasterBoardMap + ", boardIds=" + boardIds + ", usernames=" + usernames + ", boardIdNameMap="
				+ boardIdNameMap + ", productName=" + productName + ", masterBoardID=" + masterBoardId + "]";
	}

	
	
}
