package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

	@Around("execution(* com.example.demo.service..*(..))")
	public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {

		long start = System.currentTimeMillis();

		try {
			Object result = joinPoint.proceed();
			long timeTaken = System.currentTimeMillis() - start;

			log.info("Method {} executed in {} ms", joinPoint.getSignature(), timeTaken);

			return result;

		} catch (Exception ex) {
			log.error("Exception in method {} with message {}", joinPoint.getSignature(), ex.getMessage());
			throw ex;
		}
	}
}
