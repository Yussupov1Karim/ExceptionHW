package ru.karim.terminal.exceptions;

import ru.karim.TerminalException;

public class ClientTerminalException extends TerminalException {
	ClientTerminalException(String userMessage) {
		super(userMessage);
	}

	@Override
	public void displayError() {
		System.out.print("[CLIENT] : ");
		super.displayError();
	}
}
