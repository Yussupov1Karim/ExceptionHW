package ru.karim.terminal.exceptions;

public class InvalidCardNumberException extends ClientTerminalException {

	public InvalidCardNumberException() {
		super("Invalid card number. Please call for assist.");
	}
}
