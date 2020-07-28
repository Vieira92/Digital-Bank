package com.rumos.bank;

import com.rumos.bank.userInterface.Console;
import com.rumos.bank.userInterface.UI;

public class Main {
	public static void main(String[] args) {

		
		new Console();

		// este e so just in case porque tem nos menus tambem
		System.out.println("saiu aqui ver o problema");
		UI.scClose();
		System.exit(0);
	}
}
