package me.jorlee.crypto;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holdings")
public class HoldingController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TradeRepository tradeRepository;
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Autowired
	private HoldingRepository holdingRepository;
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public User newHolding(@RequestParam String first
			, @RequestParam String last, @RequestParam String email, @RequestParam String password) {
		User u = new User(first, last, email, password, 10000);
		return userRepository.save(u);
	}

	@RequestMapping("/sample")
	public Holding exampleHolding() {
		User u = userRepository.findById(1).get();
		Currency c = currencyRepository.findById(1).get();
		Holding h = new Holding(1, u, c);
		return holdingRepository.save(h);
	}
	@RequestMapping("/")
	public Iterable<Holding> getAllHoldings(){
		//Should return a list of the logged in user's trades.
		return holdingRepository.findAll();
	}
}
