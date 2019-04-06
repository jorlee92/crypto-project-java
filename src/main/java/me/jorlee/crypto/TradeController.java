package me.jorlee.crypto;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trades")
public class TradeController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TradeRepository tradeRepository;
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public User newTrade(@RequestParam String first
			, @RequestParam String last, @RequestParam String email, @RequestParam String password) {
		User u = new User(first, last, email, password, 10000);
		return userRepository.save(u);
	}

	@RequestMapping("/sample")
	public Trade exampleTrade() {
		User u = userRepository.findById(1).get();
		Currency c = currencyRepository.findById(1).get();
		return tradeRepository.save(new Trade(1000, 1, 1, u, c));
	}
	@RequestMapping("/")
	public Iterable<Trade> getAllTrades(){
		return tradeRepository.findAll();
	}
}
