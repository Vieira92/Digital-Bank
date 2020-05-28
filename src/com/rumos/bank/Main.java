package com.rumos.bank;

import com.rumos.bank.administration.services.UI;

public class Main {

	public static void main(String[] args) {

		System.out.println("====    Welcome to Rumos Digital Bank    ====\n");
		Menu.displayMenu();
	
		
		// este e so just in case porque tem nos menus tambem
		System.out.println("saiu aqui ver o problema");
		UI.scClose();
		System.exit(0);
	}

}
