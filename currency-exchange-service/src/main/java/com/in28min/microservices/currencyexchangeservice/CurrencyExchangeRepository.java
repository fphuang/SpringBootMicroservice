package com.in28min.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository 
	extends JpaRepository <CurrencyExchange, Long> {
	
	//JPA would automatically map from to the from column, to to the to column
	CurrencyExchange findByFromAndTo(String from, String to);
}
