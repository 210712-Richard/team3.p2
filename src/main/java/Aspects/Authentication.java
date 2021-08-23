package Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;

import com.revature.beans.Product;
import com.revature.beans.ScrumBoard;
import com.revature.beans.User;

/**
 * 
 * @author MuckJosh
 *	Used for authenticating user and 
 */
@Aspect
@Component
public class Authentication {

	// Handler methods are void
	@Around("loggedInHook()")
	public ResponseEntity<Object> checkLoggedIn(ProceedingJoinPoint pjp) throws Throwable {
		WebSession session = null;
		// We know this method should only be applied to Handler methods,
		// so there should be a websession.
		session = (WebSession) pjp.getTarget();
		
		User loggedUser = session.getAttribute("loggedUser");
		// Checking if logged in
		if (loggedUser != null) {
			pjp.proceed(); 
			return null;
		}
		return ResponseEntity.status(401).build();
	}
	@Around("developerHook()")
	public ResponseEntity<Object> checkDeveloper(ProceedingJoinPoint pjp) throws Throwable{
		WebSession session = null;
		// We know this method should only be applied to Handler methods,
		// so there should be a websession.
		session = (WebSession) pjp.getTarget();
		User loggedUser = session.getAttribute("loggedUser");
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
		
		WebSession session = null;
		// We know this method should only be applied to Handler methods,
		// so there should be a websession.
		session = (WebSession) pjp.getTarget();
		
		User loggedUser = session.getAttribute("loggedUser");
		ScrumBoard board = session.getAttribute("selectedBoard");
		if(board.getScrumMasterUsername().equals(loggedUser.getUsername())) {
			pjp.proceed();
			return null;
		}
		return ResponseEntity.status(401).build();
		
	}
	@Around("productMasterHook()")
	public ResponseEntity<Object> checkproductMaster(ProceedingJoinPoint pjp) throws Throwable{
		
		WebSession session = null;
		// We know this method should only be applied to Handler methods,
		// so there should be a websession.
		session = (WebSession) pjp.getTarget();
		
		User loggedUser = session.getAttribute("loggedUser");
		Product product = session.getAttribute("selectedProduct");
		if(product.getProductOwner().equals(loggedUser.getUsername())) {
			pjp.proceed();
			return null;
		}
		return ResponseEntity.status(401).build();	
	}
	
	@Pointcut("@annotation(com.revature.aspects.LoggedIn)")
	public void loggedInHook() {/* Hook for loggedIn*/}
	
	@Pointcut("@annotation(com.revature.aspects.IsDeveloper)")
	public void isDeveloperHook() {/* Hook for IsDeveloper*/}
	
	@Pointcut("@annotation(com.revature.aspects.IsScrumMaster)")
	public void isScrumMasterHook() {/* Hook for IsScrumMaster */}
	
	@Pointcut("@annotatio(com.revature.aspects.IsProductMaster")
	public void isProductMasterHook() {/* Hook for IsProductMaster */}
}
