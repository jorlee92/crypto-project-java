package me.jorlee.crypto;

import org.hibernate.mapping.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findBylastName(String lastName);
	User findByEmailAndPassword(String email, String password);

}
