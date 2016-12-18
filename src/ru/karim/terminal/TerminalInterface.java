package ru.karim.terminal;

import ru.karim.TerminalException;

class TerminalInterface {

	void showMessage(TerminalException e) {
		e.displayError();
	}
}
