package com.revature.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.SprintStatus;
import com.revature.beans.User;
import com.revature.data.ProductDAO;
import com.revature.data.ScrumBoardDAO;
import com.revature.data.SprintDAO;
import com.revature.data.UserDAO;
import com.revature.dto.ScrumBoardDTO;
import com.revature.dto.SprintDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ScrumServiceImpl implements ScrumService {

	private ProductDAO productDAO;
	private SprintDAO sprintDao;
	private ScrumBoardDAO scrumDAO;
	private UserDAO userDAO;

	@Autowired
	public ScrumServiceImpl(SprintDAO sprintDao, ProductDAO productDao, ScrumBoardDAO scrumDAO, UserDAO userDAO) {
		this.sprintDao = sprintDao;
		this.productDAO = productDao;
		this.scrumDAO = scrumDAO;
		this.userDAO = userDAO;
	}


	@Override
	public void autoUpdate() {
		Flux<SprintDTO> sprintData = sprintDao.findAll().filter(
				dto -> dto.getStatus().equals(SprintStatus.CURRENT) || dto.getStatus().equals(SprintStatus.FUTURE));
		sprintData.map(dto -> {
			if (dto.getStatus().equals(SprintStatus.CURRENT)
					&& (dto.getEndDate().isBefore(LocalDate.now()) || dto.getEndDate().isEqual(LocalDate.now()))
					&& dto.getEndTime().isBefore(LocalTime.now())) {
				dto.setStatus(SprintStatus.PAST);
				return sprintDao.save(dto);
			} else if (dto.getStatus().equals(SprintStatus.FUTURE)
					&& (dto.getStartDate().isAfter(LocalDate.now()) || dto.getStartDate().isEqual(LocalDate.now()))
					&& dto.getStartTime().isAfter(LocalTime.now())) {
				dto.setStatus(SprintStatus.FUTURE);
				return sprintDao.save(dto);
			} else {
				return dto;
			}
		});
	}


	@Override
	public Mono<ScrumBoard> createScrumBoard(User user, ScrumBoard scrumBoard, Product product) {
		scrumBoard.setProductId(product.getId());
	
		productDAO.findByProductid(product.getId()).map(dto ->{
			List<UUID> boards = dto.getBoardIds().stream().collect(Collectors.toList());
			boards.add(scrumBoard.getId());
			dto.setBoardIds(boards);
			dto.getBoardIdNameMap().put(scrumBoard.getId(), scrumBoard.toString());
			Map<UUID, String> idnameMap = new HashMap<>();
			idnameMap.putAll(dto.getBoardIdNameMap());
			dto.setBoardIdNameMap(idnameMap);
			return productDAO.save(dto).subscribe();
		}).subscribe();
		
		userDAO.findById(user.getUsername()).map(dto -> {
			List<UUID> boards = dto.getBoardIds().stream().collect(Collectors.toList());
			boards.add(scrumBoard.getId());
			dto.setBoardIds(boards);
			return userDAO.save(dto);
		}).subscribe();
		
		return scrumDAO.insert(new ScrumBoardDTO(scrumBoard)).map(dto -> dto.getScrumBoard());
	}

}
