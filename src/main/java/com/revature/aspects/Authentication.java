package com.revature.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;

import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;
import com.revature.data.AdminDAO;
import com.revature.util.WebSessionAttributes;

import reactor.core.publisher.Mono;

/**
 * 
 * @author MuckJosh Used for authenticating user and
 */
@Aspect
@Component
public class Authentication {

	private static final Logger log = LogManager.getLogger(Authentication.class);
	private User loggedUser;
	private WebSession session;
	private AdminDAO adminDAO;

	@Autowired
	public Authentication(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	// Handler methods are void
	@Around("loggedInHook()")
	public Object checkLoggedIn(ProceedingJoinPoint pjp) throws Throwable {
		setSession(pjp);
		if (session == null) {
			return Mono.just(ResponseEntity.status(404).build());
		}
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);

		// Checking if logged in
		if (loggedUser != null) {
			return pjp.proceed();
		}
		return Mono.just(ResponseEntity.status(401).build());
	}

	@Around("developerHook()")
	public Object checkDeveloper(ProceedingJoinPoint pjp) throws Throwable {
		setSession(pjp);
		if (session == null)
			return ResponseEntity.status(404).build();
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if (loggedUser == null)
			return ResponseEntity.status(401).build();
		ScrumBoard board = session.getAttribute(WebSessionAttributes.SELECTED_SCRUM_BOARD);
		if (board == null)
			return ResponseEntity.status(404).build();
		if(loggedUser.getBoardIds().stream().anyMatch(p -> board.getId().equals(p))) {
			return pjp.proceed();

		} else {
			return ResponseEntity.status(403).build();
		}
	}

	@Around("scrumMasterHook()")
	public Object checkscrumMaster(ProceedingJoinPoint pjp) throws Throwable {
		setSession(pjp);
		if (session == null)
			return ResponseEntity.status(404).build();

		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if (loggedUser == null) {
			log.debug("no user");
			return Mono.just(ResponseEntity.status(401).build());
		}
		Product product = session.getAttribute(WebSessionAttributes.SELECTED_PRODUCT);
		if(product == null) {
			log.debug("no product");
			return Mono.just(ResponseEntity.status(404).build());
		}
		ScrumBoard board = session.getAttribute(WebSessionAttributes.SELECTED_SCRUM_BOARD);
		if (board == null) {
			log.debug("no board");
			return Mono.just(ResponseEntity.status(404).build());
		}
		if (board.getScrumMasterUsername().equals(loggedUser.getUsername())) {
			return pjp.proceed();
			
		}else {
		return Mono.just(ResponseEntity.status(403).build());
		}

	}

	@Around("productMasterHook()")
	public Object checkproductMaster(ProceedingJoinPoint pjp) throws Throwable {
		setSession(pjp);
		if (session == null) {
			return ResponseEntity.status(404).build();
		}
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if (loggedUser == null) {
			log.debug("no user");
			return ResponseEntity.status(401).build();
		}
		Product product = session.getAttribute(WebSessionAttributes.SELECTED_PRODUCT);
		if (product == null) {
			log.debug("no product");
			return ResponseEntity.status(404).build();
		}
		if (product.getProductOwner().equals(loggedUser.getUsername())) {
			return pjp.proceed();

		}
		return ResponseEntity.status(403).build();
	}

	@Around("adminCheckHook()")
	public Object adminCheck(ProceedingJoinPoint pjp) throws Throwable {
		
		setSession(pjp);
		if (session == null) {
			return ResponseEntity.status(404).build();
		}
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if (loggedUser == null) {
			return Mono.just(ResponseEntity.status(401).build());
		}
		return adminDAO.findById(loggedUser.getUsername()).map(dto -> {
			try {
				return pjp.proceed();
			} catch (Throwable e) {
				log.atError().log(e);
			}
			return ResponseEntity.status(403).build();
		});
	}

	@Pointcut("@annotation(com.revature.aspects.LoggedIn)")
	public void loggedInHook() {
		/* Hook for loggedIn */}

	@Pointcut("@annotation(com.revature.aspects.IsDeveloper)")
	public void developerHook() {
		/* Hook for IsDeveloper */}

	@Pointcut("@annotation(com.revature.aspects.IsScrumMaster)")
	public void scrumMasterHook() {
		/* Hook for IsScrumMaster */}

	@Pointcut("@annotation(com.revature.aspects.IsProductMaster)")
	public void productMasterHook() {
		/* Hook for IsProductMaster */}

	@Pointcut("@annotation(com.revature.aspects.IsAdmin)")
	public void adminCheckHook() {
		/* Hook for IsAdmin */}
	private void setSession(ProceedingJoinPoint pjp) {
		for (Object o : pjp.getArgs()) {
			if (o instanceof WebSession) {
				session = (WebSession) o;
				log.debug(session);
			}
		}
	}
}