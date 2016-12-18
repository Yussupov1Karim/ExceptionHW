package ru.karim.terminal.exceptions;

public class CardExpiredException extends ClientTerminalException {
	public CardExpiredException() {
		super("Card is no longer valid");
	}
}
