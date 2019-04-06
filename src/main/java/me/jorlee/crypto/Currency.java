package me.jorlee.crypto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="crypto_currencies")
public class Currency {

    public Currency(String name) {
		super();
		this.name = name;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
    
    private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Currency() {}

	@Override
	public String toString() {
		return "Currency [id=" + id + ", name=" + name + "]";
	}
}
