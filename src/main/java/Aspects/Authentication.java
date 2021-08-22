package Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
	public void checkLoggedIn(ProceedingJoinPoint pjp) throws Throwable {
		WebSession session = null;
		// We know this method should only be applied to Handler methods,
		// so there should be a websession.
		session = (WebSession) pjp.getTarget();
		
		User loggedUser = session.getAttribute("loggedUser");
		// Checking if logged in
		if (loggedUser == null) {
			session.invalidate();
			return;
		} else {
			pjp.proceed(); // We are logged in. Call the method.
		}
	}
	@Around("developerHook()")
	public void checkDeveloper(ProceedingJoinPoint pjp) throws Throwable{
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
		}
		session.invalidate();
		return;
	}
	
	@Around("scrumMasterHook()")
	public void checkscrumMaster(ProceedingJoinPoint pjp) throws Throwable{
		
		WebSession session = null;
		// We know this method should only be applied to Handler methods,
		// so there should be a websession.
		session = (WebSession) pjp.getTarget();
		
		User loggedUser = session.getAttribute("loggedUser");
		ScrumBoard board = session.getAttribute("selectedBoard");
		if(board.getScrumMasterUsername().equals(loggedUser.getUsername()))
			pjp.proceed();
		session.invalidate();
		return;
		
	}
	@Around("productMasterHook()")
	public void checkproductMaster(ProceedingJoinPoint pjp) throws Throwable{
		
		WebSession session = null;
		// We know this method should only be applied to Handler methods,
		// so there should be a websession.
		session = (WebSession) pjp.getTarget();
		
		User loggedUser = session.getAttribute("loggedUser");
		Product product = session.getAttribute("selectedProduct");
		if(product.getProductOwner().equals(loggedUser.getUsername()))
			pjp.proceed();
		session.invalidate();
		return;	
	}
}
