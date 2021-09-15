package com.in28min.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	//@Retry(name="sample-api", fallbackMethod="hardcodedResponse") 
	//@CircuitBreaker(name="default",fallbackMethod="hardcodedResponse")
	//@RateLimiter(name="default")
	@Bulkhead(name="default")
	public String sampleApi() {
		logger.info("sample api call received");
		return "Sample Api";
		
//		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy", String.class);
//		return forEntity.getBody();
	}
	
	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}
}
