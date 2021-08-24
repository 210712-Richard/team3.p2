package com.revature.dto;

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
	private UUID id;
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
	private UUID masterBoardID;
	
	
	
	
	public ProductDTO(UUID id, String productOwner, Map<UUID, String> scrumMasterBoardMap, List<UUID> boardIds,
			List<String> usernames, Map<UUID, String> boardIdNameMap, String productName, UUID masterBoardID) {
		super();
		this.id = id;
		this.productOwner = productOwner;
		this.scrumMasterBoardMap = scrumMasterBoardMap;
		this.boardIds = boardIds;
		this.usernames = usernames;
		this.boardIdNameMap = boardIdNameMap;
		this.productName = productName;
		this.masterBoardID = masterBoardID;
	}

	public ProductDTO(Product product) {
		super();
		this.id = product.getId();
		this.productOwner = product.getProductOwner();
		this.scrumMasterBoardMap = product.getScrumMasterBoardMap();
		this.boardIds = product.getBoardIds();
		this.usernames = product.getUsernames();
		this.boardIdNameMap = product.getBoardIdNameMap();
		this.productName = product.getProductName();
		this.masterBoardID = product.getMasterBoardId();
	}
	
	public Product getProduct() {
		Product product = new Product();
		product.setId(id);
		product.setProductOwner(productOwner);
		product.setScrumMasterBoardMap(scrumMasterBoardMap);
		product.setBoardIds(boardIds);
		product.setUsernames(usernames);
		product.setBoardIdNameMap(boardIdNameMap);
		product.setProductName(productName);
		product.setMasterBoardId(masterBoardID);
		return product;
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

	public UUID getMasterBoardID() {
		return masterBoardID;
	}

	public void setMasterBoardID(UUID masterBoardID) {
		this.masterBoardID = masterBoardID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(boardIdNameMap, boardIds, id, masterBoardID, productName, productOwner, scrumMasterBoardMap,
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
				&& Objects.equals(id, other.id) && Objects.equals(masterBoardID, other.masterBoardID)
				&& Objects.equals(productName, other.productName) && Objects.equals(productOwner, other.productOwner)
				&& Objects.equals(scrumMasterBoardMap, other.scrumMasterBoardMap)
				&& Objects.equals(usernames, other.usernames);
	}
	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", productOwner=" + productOwner + ", scrumMasterBoardMap="
				+ scrumMasterBoardMap + ", boardIds=" + boardIds + ", usernames=" + usernames + ", boardIdNameMap="
				+ boardIdNameMap + ", productName=" + productName + ", masterBoardID=" + masterBoardID + "]";
	}

	
	
}
