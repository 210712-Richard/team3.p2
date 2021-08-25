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

import reactor.core.publisher.Mono;

/**
 * 
 * @author MuckJosh
 *	Used for authenticating user and 
 */
@Aspect
@Component
public class Authentication {

	
	private static final Logger log = LogManager.getLogger(Authentication.class);
	private User loggedUser;
	private String loginUserField = "loggedUser";
	private WebSession session;
	private AdminDAO adminDAO;
	
	@Autowired
	public Authentication(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}


	// Handler methods are void
	@Around("loggedInHook()")
	public ResponseEntity<Object> checkLoggedIn(ProceedingJoinPoint pjp) throws Throwable {

		session = (WebSession) pjp.getTarget();
		loggedUser = session.getAttribute(loginUserField);

		// Checking if logged in
		if (loggedUser != null) {
			pjp.proceed(); 
			return null;
		}
		return ResponseEntity.status(401).build();
	}
	@Around("developerHook()")
	public ResponseEntity<Object> checkDeveloper(ProceedingJoinPoint pjp) throws Throwable{

		session = (WebSession) pjp.getTarget();
		loggedUser = session.getAttribute(loginUserField);
		ScrumBoard board = session.getAttribute("selectedBoard");
		if(board.getUsers().stream()
				.filter(user -> user.equals(loggedUser.getUsername()))
				.findFirst().isPresent()) {
			pjp.proceed();
			return null;
		}
		return ResponseEntity.status(401).build();
	}
	
	@Around("scrumMasterHook()")
	public ResponseEntity<Object> checkscrumMaster(ProceedingJoinPoint pjp) throws Throwable{
		
		session = (WebSession) pjp.getTarget();
		loggedUser = session.getAttribute(loginUserField);

		ScrumBoard board = session.getAttribute("selectedBoard");
		if(board.getScrumMasterUsername().equals(loggedUser.getUsername())) {
			pjp.proceed();
			return null;
		}
		return ResponseEntity.status(401).build();
		
	}
	@Around("productMasterHook()")
	public ResponseEntity<Object> checkproductMaster(ProceedingJoinPoint pjp) throws Throwable{
	
		// We know this method should only be applied to Handler methods,
		// so there should be a websession.
		session = (WebSession) pjp.getTarget();
		loggedUser = session.getAttribute(loginUserField);
		User loggedUser = session.getAttribute("loggedUser");

		Product product = session.getAttribute("selectedProduct");
		if(product.getProductOwner().equals(loggedUser.getUsername())) {
			pjp.proceed();
			return null;
		}
		return ResponseEntity.status(401).build();	
	}
	

	@Around("adminCheckHook()")
	public Mono<ResponseEntity<Object>> adminCheck(ProceedingJoinPoint pjp) throws Throwable{
		session = (WebSession) pjp.getTarget();
		loggedUser = session.getAttribute(loginUserField);
		if(loggedUser == null) {
			return Mono.just(ResponseEntity.status(401).build());
		}
		return adminDAO.findById(loggedUser.getUsername()).map(dto ->{
			try {
				pjp.proceed();
			} catch (Throwable e) {
				log.atError().log(e);
			}
			return ResponseEntity.status(401).build();
		});
	}
	
	@Pointcut("@annotation(com.revature.aspects.LoggedIn)")
	public void loggedInHook() {/* Hook for loggedIn*/}
	
	@Pointcut("@annotation(com.revature.aspects.IsDeveloper)")
	public void developerHook() {/* Hook for IsDeveloper*/}
	
	@Pointcut("@annotation(com.revature.aspects.IsScrumMaster)")
	public void scrumMasterHook() {/* Hook for IsScrumMaster */}
	
	@Pointcut("@annotation(com.revature.aspects.IsProductMaster)")
	public void productMasterHook() {/* Hook for IsProductMaster */}
}
