package com.in28minutes.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private Environment env;
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
	@Autowired
	private ExchangeValueRepository repo;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retriveExchangeValue(@PathVariable String from, @PathVariable String to) {
		logger.info("Kolllu from exchange==> retriveExchangeValue"); 
		ExchangeValue exValue = repo.findByFromAndTo(from, to);
		
		exValue.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		//kubernetes
		String host = env.getProperty("HOSTNAME");
		String version ="v11";
		exValue.setEnvironment(exValue.getPort()+""+version+""+host);
		logger.info("Host details --> "+exValue); 
		return exValue;
	}
}
