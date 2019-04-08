package me.jorlee.crypto;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	

	@RequestMapping("/byUser/{id}")
	public java.util.List<Comment> getAllByUser(@PathVariable int id){
		User u = userRepository.findById(id).get();
		return commentRepository.findAllByTo(u);
	}
	
	@RequestMapping("/new/{id}")
	public Comment newToUser(HttpServletRequest request, @PathVariable int id){
		Object userId = request.getSession().getAttribute("userId");
		if(userId != null) {
			User to = userRepository.findById(id).get();
			User from = userRepository.findById((int) userId).get();
			Comment c = new Comment(from, to, null, "Testing", 0);
			return commentRepository.save(c);
		}
		else {
			return null;
		}
	}
	
}
