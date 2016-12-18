package ru.karim.server;

import ru.karim.Card;
import ru.karim.server.exceptions.NotEnoughFundsException;

import java.util.Date;

class Account extends Card {

	private String pin;
	private int balance;

	Account(String cardNumber, Date expireDate, String pin, int balance) {
		super(cardNumber, expireDate);
		this.pin = pin;
		this.balance = balance;
	}

	boolean checkPin(String pin) {
		return pin.equals(this.pin);
	}

	int receiveMoney(int amount) throws NotEnoughFundsException {
		if (amount > balance) {
			throw new NotEnoughFundsException();
		} else {
			balance -= amount;
			return amount;
		}
	}

	int getBalance() {
		return balance;
	}

	void putMoney(int amount) {
		balance += amount;
	}
}
