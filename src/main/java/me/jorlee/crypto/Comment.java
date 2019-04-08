package me.jorlee.crypto;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="crypto_comments")
public class Comment {

	public Comment(User from, User to, Timestamp posted, String text, int score) {
		super();
		this.from = from;
		this.to = to;
		this.posted = posted;
		this.text = text;
		this.score = score;
	}
	
	public Comment() {}
	@PrePersist
	void preInsert() {
	//See https://stackoverflow.com/a/13432234
	   if (this.posted == null)
	       this.posted = new Timestamp(System.currentTimeMillis());
	}
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private User from;
	
	@ManyToOne
	private User to;
	
	private Timestamp posted;
	private String text;
	private int score;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	public Timestamp getPosted() {
		return posted;
	}
	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
    
}
