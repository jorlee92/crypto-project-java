package me.jorlee.crypto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="crypto_users")
public class User {
	public User(String firstName, String lastName, String email, String password, double dollars) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dollars = dollars;
	}
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String firstName, lastName, email, password;
	private double dollars;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public double getDollars() {
		return dollars;
	}
	public void setDollars(double dollars) {
		this.dollars = dollars;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", " + (firstName != null ? "firstName=" + firstName + ", " : "")
				+ (lastName != null ? "lastName=" + lastName + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (password != null ? "password=" + password + ", " : "") + "dollars=" + dollars + "]";
	}
}
