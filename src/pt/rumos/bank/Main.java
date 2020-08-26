package pt.rumos.bank;

import pt.rumos.bank.userInterface.Console;
import pt.rumos.bank.userInterface.UI;

public class Main {
	public static void main(String[] args) {
		
		new Console();

		UI.scClose();
		System.exit(0);
	}
}
