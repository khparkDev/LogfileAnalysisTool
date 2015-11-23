package com.khpark.log.analysis.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

	@Around(value = "@annotation(com.tmoncorp.log.analysis.aspect.Loggable)")
	public Object aroundTargetMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		joinPoint.proceed();
		stopWatch.stop();

		prettyPrint(joinPoint, stopWatch.getTotalTimeMillis());
		return joinPoint;

	}

	private void prettyPrint(ProceedingJoinPoint joinPoint, long elapsedTime) {
		String resultMsg = "[ Method Name : " + joinPoint.getSignature().getName() + ", Elapsed Time = " + elapsedTime + " ms ]";
		String deco = "";

		for (int i = 0; i < resultMsg.length(); i++) {
			deco += "=";
		}

		LOGGER.info(deco);
		LOGGER.info(resultMsg);
		LOGGER.info(deco);
	}
}
