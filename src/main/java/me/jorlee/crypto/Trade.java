package me.jorlee.crypto;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="crypto_trades")
public class Trade {
	public Trade(float pricepaid, float quantity, int mode, User user, Currency currency) {
		super();
		this.pricepaid = pricepaid;
		this.quantity = quantity;
		this.mode = mode;
		this.user = user;
		this.currency = currency;
	}
	public Trade() {}
	private float pricepaid, quantity;
	private int mode;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name="currency_id")
	private Currency currency;

	public float getPricepaid() {
		return pricepaid;
	}

	public void setPricepaid(float pricepaid) {
		this.pricepaid = pricepaid;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Trade [pricepaid=" + pricepaid + ", quantity=" + quantity + ", mode=" + mode + ", id=" + id + ", user="
				+ user + "]";
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}


}
