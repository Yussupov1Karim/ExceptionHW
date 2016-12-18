package ru.karim.terminal.exceptions;

public class AlreadyInsertedException extends ClientTerminalException {

	public AlreadyInsertedException() {
		super("The another card already inserted. Please Remove it before inserting a new card.");
	}
}
