package ru.karim.terminal.exceptions;

public class PinAlreadyEnteredException extends ClientTerminalException {
	public PinAlreadyEnteredException() {
		super("Pin already entered.");
	}
}
