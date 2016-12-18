package ru.karim.server;

import ru.karim.server.exceptions.NoSuchAccountException;

import java.util.Date;
import java.util.HashMap;

/**
 * Terminal Server for Terminal clients. Implemented as Singleton for more than one terminals.
 */
public class TerminalServer {

	private static TerminalServer instance = null;
	private HashMap<String, Account> accountPool = new HashMap<>();

	private TerminalServer() {
		accountPool.put("0000111122223331", new Account("0000111122223331", new Date(2017, 11, 10), "1234", 1000));
		accountPool.put("0000111122223332", new Account("0000111122223332", new Date(2017, 11, 10), "1234", 1000));
		accountPool.put("0000111122223333", new Account("0000111122223333", new Date(2017, 11, 10), "1234", 1000));
		accountPool.put("0000111122223334", new Account("0000111122223334", new Date(2017, 11, 10), "1234", 1000));
		accountPool.put("0000111122223335", new Account("0000111122223335", new Date(2017, 11, 10), "1234", 1000));
		accountPool.put("0000111122223336", new Account("0000111122223336", new Date(2017, 11, 10), "1234", 1000));
		accountPool.put("0000111122223337", new Account("0000111122223337", new Date(2017, 11, 10), "1234", 1000));
		accountPool.put("0000111122223338", new Account("0000111122223338", new Date(2017, 11, 10), "1234", 1000));
		accountPool.put("0000111122223339", new Account("0000111122223339", new Date(2017, 11, 10), "1234", 1000));
		accountPool.put("0000111122223330", new Account("0000111122223330", new Date(2017, 11, 10), "1234", 1000));
	}

	public static TerminalServer getServer() {
		if (instance == null) {
			instance = new TerminalServer();
		}
		return instance;
	}

	/**
	 * Method for establishing a new connection to server
	 *
	 * @return Connection - a new connection to server.
	 */
	public Connection connect(String cardNumber) throws NoSuchAccountException {
		Account account = accountPool.get(cardNumber.replace(" ", ""));
		if (account == null) {
			throw new NoSuchAccountException(cardNumber);
		}
		System.out.println("server-log : connected with account # " + account.getCardNumber());
		return new Connection(account);
	}

}
