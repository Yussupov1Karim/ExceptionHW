package ru.karim;

import java.util.Date;

public class Card {
	private String cardNumber;
	private Date expireDate;

	public Card(String cardNumber, Date expireDate) {
		this.cardNumber = cardNumber;
		this.expireDate = expireDate;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public Date getExpireDate() {
		return expireDate;
	}
}
