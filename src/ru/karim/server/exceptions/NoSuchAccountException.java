package ru.karim.server.exceptions;

public class NoSuchAccountException extends ServerTerminalException {
	public NoSuchAccountException(String accountNumber) {
		super("No such account: " + accountNumber);
	}
}
