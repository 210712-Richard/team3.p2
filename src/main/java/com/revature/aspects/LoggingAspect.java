package com.revature.aspects;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	
	@Around("everything()")
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		Logger log = LogManager.getLogger(pjp.getTarget().getClass()); // get a logger for the class of the method being
																		// called.
		log.trace("Method with signature: " + pjp.getSignature());
		log.trace("With arguments: " + Arrays.toString(pjp.getArgs()));
		try {
			result = pjp.proceed();
		} catch (Throwable t) {
			logError(log, t);
			throw t; // if we forget to throw t, we have the side effect that all exceptions are
						// caught
		}
		log.trace("Method returning with: " + result);
		return result;
	}

	private void logError(Logger log, Throwable t) {
		log.error("Method threw exception: " + t);
		for (StackTraceElement s : t.getStackTrace()) {
			log.warn(s);
		}
		if (t.getCause() != null) {
			logError(log, t.getCause());
		}
	}

	@Pointcut("execution( * com.revature..*(..) )")
	private void everything() {
		/* empty method for hook */};
}
