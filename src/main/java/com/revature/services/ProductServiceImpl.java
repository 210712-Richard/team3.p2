package com.revature.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Notification;
import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;
import com.revature.data.ProductDAO;
import com.revature.data.UserDAO;
import com.revature.dto.ProductDTO;

import reactor.core.publisher.Mono;

/**
 * 
 * @author jasmine
 *
 */

@Service
public class ProductServiceImpl implements ProductService {
	private ProductDAO productDao;
	private UserDAO userDAO;

	@Autowired
	public ProductServiceImpl(ProductDAO productDao, UserDAO userDAO) {
		super();
		this.productDao = productDao;
		this.userDAO = userDAO;
	}

	/**
	 * @param - All fields needed to create a new product.
	 * @return - Saves product to the database.
	 * 
	 *         Product Owners can add new products
	 * 
	 */
	@Override
	public Mono<Product> createNewProduct(UUID id, String productOwner, Map<UUID, String> scrumMasterBoardMap,
			List<UUID> boardIds, List<String> usernames, Map<UUID, String> boardIdNameMap, String productName,
			UUID masterBoardID) {
		Product product = new Product();
		product.setId(id);
		product.setProductOwner(productOwner);
		product.setScrumMasterBoardMap(scrumMasterBoardMap);
		product.setBoardIds(boardIds);
		product.setUsernames(usernames);
		product.setBoardIdNameMap(boardIdNameMap);
		product.setProductName(productName);
		product.setMasterBoardId(masterBoardID);
		return productDao.save(new ProductDTO(product)).map(p -> p.getProduct());

	}

	/**
	 * @param user - The username developer is associated with.
	 * @param id   - The id of the product the Product Owner wants the developer to
	 *             be a part of
	 * @return - Saves the id to the list of product ids the developer is associated
	 *         with
	 * 
	 *         Product owners can add developers to products by product id
	 */

	@Override
	public Mono<User> addProductById(String username, UUID productId) {
		return userDAO.findById(username).flatMap(dto -> {
			List<UUID> list = dto.getProductIds();
			if (!list.contains(productId)) {
				list.add(productId);
				dto.setProductIds(list);
				return userDAO.save(dto);
			} else {
				return Mono.just(dto);
			}
		}).map(p -> p.getUser());
	}

	/**
	 * @param user - The username the developer is associated with.
	 * @param id   - The product id of the product the Product Owner wants the
	 *             developer to be a part of
	 * @return - Saves the change to the list of product ids the developers are
	 *         associated.
	 * 
	 *         Product owners can remove developers from products by product id
	 */

	@Override
	public Mono<User> removeProductById(String username, UUID productId) {
		return userDAO.findById(username).flatMap(dto -> {
			List<UUID> list = dto.getProductIds().stream().collect(Collectors.toList());
			list.removeIf(p -> p.equals(productId));
			dto.setProductIds(list);
			return userDAO.save(dto);
		}).map(p -> p.getUser());
	}
	

}
