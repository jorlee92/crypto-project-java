package me.jorlee.crypto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	private String CURRENCY = "USD";
    private CryptoCompareAPI api = new CryptoCompareAPI();

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private CurrencyRepository currencyRepository;
	
	@Autowired
	private HoldingRepository holdingRepository;
	
	@Autowired
	private TradeRepository tradeRepository;
	
	private float GetNumberHeld(User u, Currency c){
		java.util.List<Holding> holdings = holdingRepository.findAllByUserAndCurrency(u, c);

		float numberHeld = 0;
		for (Holding holding : holdings) {
			numberHeld += holding.getQuantity();
		}
		System.out.println("user has a total of  " + numberHeld);
		return numberHeld;
	}
	
	private HashMap GetHoldings(User u){
		java.util.List<Holding> holdings = holdingRepository.findAllByUser(u);
		HashMap results = new HashMap<String, Float>();
		for (Holding holding : holdings) {
			String key = holding.getCurrency().getName();
			if(results.containsKey(key)) {
				//This means we already added one or more of this coin. We want to add the current count to the running count.
				results.put(key, (float) results.get(key) + holding.getQuantity());
			} else {
				results.put(key, (float) holding.getQuantity());
			}
		}
		System.out.println("results: " + results);
		return results;
	}
	
	private Map<String, Map<String, Float>> GenerateLeaderboards() {
		Iterable<User> users = userRepository.findAll();
		Map<String, Map<String, Float>> results = new HashMap<String, Map<String, Float>>();
		users.forEach(user -> {
			HashMap<String, Float> m = new HashMap<String, Float>();
			java.util.List<Holding> holdings = holdingRepository.findAllByUser(user);
			holdings.forEach(h -> {
				String currentCurrencyName = h.getCurrency().getName();
				if(m.containsKey(currentCurrencyName)) {
					m.put(currentCurrencyName, m.get(currentCurrencyName) + h.getQuantity());
				} else {
					m.put(currentCurrencyName, h.getQuantity());
				}
			});
			m.put("cash", (float) user.getDollars());
			System.out.println(m);
			results.put(String.valueOf(user.getId()), m);
		});
		return results;
	}
	
	@RequestMapping(value="/holdings")
	public HashMap getHoldings(HttpServletRequest request) {
		Object userId = request.getSession().getAttribute("userId");
		if(userId != null) {
			User currentUser = userRepository.findById((int) userId).get();
			return GetHoldings(currentUser);
		}
		else {
			return null;
		}
	}
	
	@Transactional
	@RequestMapping(value="/buyCoin", method=RequestMethod.POST)
	public String buyCoin(HttpServletRequest request, @RequestParam String currencyName, @RequestParam float number) {
		String msg;
		float priceOfSingleCoinOfSelectedCurrency = 0;
		Object userId = request.getSession().getAttribute("userId");
		if(userId != null) {
			//The following line should work because the sessionId should always be valid.
			User currentUser = userRepository.findById((int) userId).get();
			Currency selectedCurrency = currencyRepository.findByName(currencyName);
			if(selectedCurrency != null) {
				System.out.println("Selected Currency" + selectedCurrency + selectedCurrency.getName());
				try {
					priceOfSingleCoinOfSelectedCurrency = CryptoCompareAPI.getPrice(currencyName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("User's dollars " + currentUser.getDollars());
				System.out.println("Price of total Purchase " + priceOfSingleCoinOfSelectedCurrency * number);
				if(priceOfSingleCoinOfSelectedCurrency > 0 && currentUser.getDollars() >= (priceOfSingleCoinOfSelectedCurrency * number)) {
					float totalPurchasePrice = priceOfSingleCoinOfSelectedCurrency * number;
					Holding newHolding = new Holding(number, currentUser, selectedCurrency);
					Trade newTrade = new Trade(totalPurchasePrice, number, 1 ,currentUser, selectedCurrency);
					holdingRepository.save(newHolding);
					tradeRepository.save(newTrade);
					currentUser.setDollars(currentUser.getDollars() - totalPurchasePrice);
					msg = "Trade complete";
				} else {
					msg = "Unable to complete trade";
				}

			} else {
				msg = "Unable to complete trade";
			}
		} else {
			msg = "You must be logged in to access this page";
		}
		return msg;
	}
	
	@RequestMapping("/leaderboard")
	public Map<String, Map<String, Float>> leaderBoard(){
		return GenerateLeaderboards();
	}
	@Transactional
	@RequestMapping(value="/sellCoin", method=RequestMethod.POST)
	public String sellCoin(HttpServletRequest request, @RequestParam String currencyName, @RequestParam float number) {
		String msg;
		float priceOfSingleCoinOfSelectedCurrency = 0;
		Object userId = request.getSession().getAttribute("userId");
		if(userId != null) {
			//The following line should work because if they are logged in that means there should be a matching user.
			User currentUser = userRepository.findById((int) userId).get();
			Currency selectedCurrency = currencyRepository.findByName(currencyName);
			if(selectedCurrency != null) {
				System.out.println("Selected Currency" + selectedCurrency + selectedCurrency.getName());
				try {
					priceOfSingleCoinOfSelectedCurrency = CryptoCompareAPI.getPrice(currencyName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("User's dollars " + currentUser.getDollars());
				System.out.println("Price of total Sale " + priceOfSingleCoinOfSelectedCurrency * number);
				float numberHeld = GetNumberHeld(currentUser, selectedCurrency);
				if(priceOfSingleCoinOfSelectedCurrency > 0 && number <= numberHeld) { //This is primarily to check that the API request worked.
					float totalSalePrice = priceOfSingleCoinOfSelectedCurrency * number * -1; //Create a negative purchase
					Holding newHolding = new Holding(number * -1 , currentUser, selectedCurrency); //Create a negative holding
					Trade newTrade = new Trade(totalSalePrice, number, 0 ,currentUser, selectedCurrency);
					holdingRepository.save(newHolding);
					tradeRepository.save(newTrade);
					currentUser.setDollars(currentUser.getDollars() - totalSalePrice);
					msg = "Trade complete";
				} else {
					msg = "Unable to complete trade";
				}

			} else {
				msg = "Unable to complete trade";
			}
		} else {
			msg = "You must be logged in to access this page";
		}
		return msg;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public User registerUser(@RequestParam String first
			, @RequestParam String last, @RequestParam String email, @RequestParam String password) {
		User u = new User(first, last, email, password, 10000);
		return userRepository.save(u);
	}
	@RequestMapping("/sample")
	public User exampleUser() {
		User u = new User("Jordan","Lee","test@jorlee.me", "password", 1000);
		return userRepository.save(u);
	}
	@RequestMapping("/")
	public Iterable<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, @RequestParam String email, @RequestParam String password ) {
		User userSearchResult = userRepository.findByEmailAndPassword(email, password);
		String msg;
		if(userSearchResult != null) {
			//If we found a user take the userId and add it to the session.
			request.getSession().setAttribute("userId", userSearchResult.getId());
			msg = "Hello, " + userSearchResult.getFirstName() + "!";
		} else {
			// Otherwise tell the user their login failed.
			msg = "Login Failed!";
		}
		return msg;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLogin(HttpServletRequest request) {
		double dollars = userRepository.findById((Integer) request.getSession().getAttribute("userId")).get().getDollars();
		return request.getSession().getAttribute("userId") == null ? "Not logged in": "{ \"userID\" : " +
						request.getSession().getAttribute("userId").toString() + ", \"dollars\": " + dollars +  "} ";
	}
	
}
