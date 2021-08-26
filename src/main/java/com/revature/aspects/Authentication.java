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
		session = null;
		for (Object o : pjp.getArgs()) {
			if (o instanceof WebSession) {
				session = (WebSession) o;
			}
		}
		if (session == null) {
			System.out.println(session + " session is null");
			return ResponseEntity.status(401).build();
		}
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);

		// Checking if logged in
		if (loggedUser != null) {
			return pjp.proceed();
		}
		return ResponseEntity.status(401).build();
	}

	@Around("developerHook()")
	public Object checkDeveloper(ProceedingJoinPoint pjp) throws Throwable {
		session = null;
		for (Object o : pjp.getArgs()) {
			if (o instanceof WebSession) {
				session = (WebSession) o;
			}
		}
		if (session == null)
			return ResponseEntity.status(401).build();
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if (loggedUser == null)
			return ResponseEntity.status(401).build();
		ScrumBoard board = session.getAttribute("selectedBoard");
		if (board == null)
			return ResponseEntity.status(401).build();
		if (board.getUsers().stream().anyMatch(user -> user.equals(loggedUser.getUsername()))) {
			return pjp.proceed();

		} else {
			return ResponseEntity.status(401).build();
		}
	}

	@Around("scrumMasterHook()")
	public Object checkscrumMaster(ProceedingJoinPoint pjp) throws Throwable {
		session = null;
		for (Object o : pjp.getArgs()) {
			if (o instanceof WebSession) {
				session = (WebSession) o;
			}
		}
		if (session == null)
			return ResponseEntity.status(401).build();

		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if (loggedUser == null)
			return ResponseEntity.status(401).build();
		ScrumBoard board = session.getAttribute("selectedBoard");
		if (board == null)
			return ResponseEntity.status(401).build();
		if (board.getScrumMasterUsername().equals(loggedUser.getUsername())) {
			return pjp.proceed();
			
		}else {
		return ResponseEntity.status(401).build();
		}

	}

	@Around("productMasterHook()")
	public Object checkproductMaster(ProceedingJoinPoint pjp) throws Throwable {
		session = null;
		for (Object o : pjp.getArgs()) {
			if (o instanceof WebSession) {
				session = (WebSession) o;
			}
		}
		if (session == null) {
			return ResponseEntity.status(401).build();
		}
		loggedUser = session.getAttribute(WebSessionAttributes.LOGGED_USER);
		if (loggedUser == null) {
			return ResponseEntity.status(401).build();
		}
		Product product = session.getAttribute("selectedProduct");
		if (product == null)
			return ResponseEntity.status(401).build();
		if (product.getProductOwner().equals(loggedUser.getUsername())) {
			return pjp.proceed();

		}
		return ResponseEntity.status(401).build();
	}

	@Around("adminCheckHook()")
	public Object adminCheck(ProceedingJoinPoint pjp) throws Throwable {
		for (Object o : pjp.getArgs()) {
			if (o instanceof WebSession) {
				session = (WebSession) o;
			}
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
			return ResponseEntity.status(401).build();
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
}