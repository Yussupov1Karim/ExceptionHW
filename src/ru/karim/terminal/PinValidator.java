package ru.karim.terminal;

import ru.karim.server.Connection;
import ru.karim.terminal.exceptions.AccountBlockedException;
import ru.karim.terminal.exceptions.InvalidPinException;
import ru.karim.terminal.exceptions.PinAlreadyEnteredException;

import java.util.Date;

class PinValidator {

	private Connection connection;
	private int attemptsCount = 0;
	private boolean isValid = false;
	private Date blockedTime = null;

	PinValidator(Connection connection) {
		this.connection = connection;
	}

	boolean validatePin(String pin) throws AccountBlockedException, InvalidPinException, PinAlreadyEnteredException {
		if (isValid) {
			throw new PinAlreadyEnteredException();
		}
		if (blockedTime == null || blockedTime.before(new Date())) {
			if (connection.validatePin(pin)) {
				isValid = true;
				return true;
			} else {
				attemptsCount += 1;
				if (attemptsCount >= 3) {
					blockedTime = new Date(
							new Date().getTime() + 5000
					);
					attemptsCount = 0;
				}
				throw new InvalidPinException();
			}
		} else {
			throw new AccountBlockedException(blockedTime);
		}
	}

	boolean isValid() {
		return isValid;
	}

}
