package com.in28min.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity){
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to",  to);
		
		String url = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
		ResponseEntity<CurrencyConversion> responseEntity = 
				new RestTemplate().getForEntity(url, CurrencyConversion.class, uriVariables);
		
		responseEntity.getBody();
		CurrencyConversion conversion = responseEntity.getBody();
		
		return new CurrencyConversion(conversion.getId(), from, to, quantity,
				conversion.getConversionMultiple(),
				quantity.multiply(conversion.getConversionMultiple()),
				conversion.getEnvironment());
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity){
		
		CurrencyConversion conversion = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(conversion.getId(), from, to, quantity,
				conversion.getConversionMultiple(),
				quantity.multiply(conversion.getConversionMultiple()),
				conversion.getEnvironment() + " feign");
	}
}
