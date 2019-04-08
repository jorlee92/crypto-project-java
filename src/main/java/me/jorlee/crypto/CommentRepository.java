package me.jorlee.crypto;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
	List<Comment> findAllByTo(User u);
}
