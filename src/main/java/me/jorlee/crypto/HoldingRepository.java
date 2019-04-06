package me.jorlee.crypto;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HoldingRepository extends CrudRepository<Holding, Integer> {
	List<Holding> findAllByUserAndCurrency(User u, Currency c);
	List<Holding> findAllByUser(User u);

}
