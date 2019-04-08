package me.jorlee.crypto;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TradeRepository extends CrudRepository<Trade, Integer> {

	List<Trade> findAllByUser(User u);

}
