package me.jorlee.crypto;

import java.util.ArrayList;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@RequestMapping("/generate")
	public String exampleTrade() {
		Currency btc = new Currency("BTC");
	    Currency eth = new Currency("ETH");
	    Currency xrp = new Currency("XRP");
	    Currency ltc = new Currency("LTC");
	    Currency eos = new Currency("EOS");
	    Currency bch = new Currency("BCH");
	    Currency usdt = new Currency("USDT");
	    currencyRepository.save(btc);
	    currencyRepository.save(eth);
	    currencyRepository.save(xrp);
	    currencyRepository.save(ltc);
	    currencyRepository.save(eos);
	    currencyRepository.save(bch);
	    currencyRepository.save(usdt);
		return "Loaded coins";
	}
	@RequestMapping("/")
	public Iterable<Currency> getAllUsers(){
		return currencyRepository.findAll();
	}
}
