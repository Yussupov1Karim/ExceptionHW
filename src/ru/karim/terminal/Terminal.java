package ru.karim.terminal;

import ru.karim.Card;
import ru.karim.TerminalException;
import ru.karim.server.Connection;
import ru.karim.server.TerminalServer;
import ru.karim.server.exceptions.NoSuchAccountException;
import ru.karim.terminal.exceptions.*;

import java.util.GregorianCalendar;

public class Terminal {

	private TerminalServer terminalServer = TerminalServer.getServer();
	private PinValidator pinValidator = null;
	private TerminalInterface anInterface = new TerminalInterface();
	private Card insertedCard = null;
	private Connection connection = null;


	public void insertCard(Card card) {
		try {
			readCard(card);
		} catch (TerminalException te) {
			insertedCard = null;
			pinValidator = null;
			connection = null;
			anInterface.showMessage(te);
		}
	}

	private void readCard(Card card) throws AlreadyInsertedException, InvalidCardNumberException, CardExpiredException, NoSuchAccountException {

		if (insertedCard != null) {
			throw new AlreadyInsertedException();
		}
		if (!card.getCardNumber().replace(" ", "").matches("\\d{16}")) {
			throw new InvalidCardNumberException();
		}
		if (card.getExpireDate().before(new GregorianCalendar().getTime())) {
			throw new CardExpiredException();
		}
		insertedCard = card;
		connection = terminalServer.connect(card.getCardNumber());
		pinValidator = new PinValidator(connection);
	}

	public void enterPin(String pin) {
		try {
			if (insertedCard != null) {
				pinValidator.validatePin(pin);
			} else {
				throw new NoCardException();
			}
		} catch (TerminalException te) {
			anInterface.showMessage(te);
		}
	}

	public int checkBalance() {
		try {
			if (pinValidator == null) {
				throw new NoCardException();
			}
			if (!pinValidator.isValid()) {
				throw new InvalidPinException();
			}
		} catch (TerminalException te) {
			anInterface.showMessage(te);
			return 0;
		}
		return connection.getBalance();
	}

	public int receiveMoney(int amount) {
		try {
			if (pinValidator == null) {
				throw new NoCardException();
			}
			if (!pinValidator.isValid()) {
				throw new InvalidPinException();
			}
			return connection.receiveMoney(amount);
		} catch (TerminalException te) {
			anInterface.showMessage(te);
			return 0;
		}
	}

	public void putMoney(int amount) {
		try {
			if (pinValidator == null) {
				throw new NoCardException();
			}
			if (!pinValidator.isValid()) {
				throw new InvalidPinException();
			}
			connection.putMoney(amount);
		} catch (TerminalException te) {
			anInterface.showMessage(te);
		}
	}

	public Card removeCard() {
		try {
			if (insertedCard == null) {
				throw new NoCardException();
			}
		} catch (TerminalException te) {
			anInterface.showMessage(te);
		}

		Card tmpCard = insertedCard;
		insertedCard = null;
		connection = null;
		pinValidator = null;
		return tmpCard;
	}

	public static void main(String[] args) throws InterruptedException {
		Terminal terminal1 = new Terminal();


		terminal1.insertCard(new Card("0000 1111 2222 1333", new GregorianCalendar(2018, 10, 6).getTime()));

		terminal1.insertCard(new Card("0000-1111-2222-3333", new GregorianCalendar(2016, 10, 6).getTime()));
		terminal1.insertCard(new Card("0000-1111-2222-3333", new GregorianCalendar(2016, 10, 6).getTime()));
		terminal1.insertCard(new Card("0000 1111 2222 3334", new GregorianCalendar(2016, 10, 6).getTime()));
		terminal1.insertCard(new Card("0000 1111 2222 3335", new GregorianCalendar(2017, 10, 6).getTime()));
		terminal1.enterPin("001");
		terminal1.enterPin("002");
		terminal1.enterPin("003");
		Thread.sleep(1000);
		terminal1.enterPin("1234");
		Thread.sleep(1000);
		terminal1.enterPin("1234");
		Thread.sleep(1000);
		terminal1.enterPin("1234");
		Thread.sleep(1000);
		terminal1.enterPin("1234");
		Thread.sleep(1000);
		terminal1.enterPin("1234");
		Thread.sleep(1000);
		terminal1.enterPin("1234");

		System.out.println("Money received: " + terminal1.receiveMoney(123));
		System.out.println("Money received: " + terminal1.receiveMoney(10000));
		System.out.println("Money received: " + terminal1.receiveMoney(1000));

		System.out.println("Balance: " + terminal1.checkBalance());

		terminal1.putMoney(10000);
		System.out.println(terminal1.checkBalance());

		terminal1.insertCard(new Card("0000 1111 2222 3334", new GregorianCalendar(2017, 10, 6).getTime()));

		terminal1.removeCard();

		System.out.println("After remove:");

		terminal1.enterPin("123");
		terminal1.checkBalance();
		terminal1.putMoney(100);
		terminal1.receiveMoney(100);
	}
}
