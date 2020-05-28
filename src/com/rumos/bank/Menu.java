package com.rumos.bank;

import java.util.Locale;

import com.rumos.bank.administration.MenuADM;
import com.rumos.bank.administration.services.UI;

public class Menu {

	public static void displayMenu() {
		System.out.println("Choose a category:"
				+ "\n1 - ADM"
				+ "\n2 - ATM"
				+ "\n3 - Exit");
		selection();
	}
	
	private static void selection() {
		Locale.setDefault(Locale.US);
		int option = UI.getInt();
		
		switch(option) {
		case 1:
			MenuADM ADMmenu = new MenuADM();
			MenuADM.displayMenu();
			ADMmenu.selection();
			break;
		case 2:
			// TODO: ATM
			System.out.println("2 ATM");
			System.out.println("NAO ESTA FEITO AINDA!!");
			break;
		case 3:
			System.out.println("Thanks for using Rumos Digital Bank");
			UI.scClose();
			System.exit(0);
			break;
		default:
			System.out.print("Wrong, please do it again: ");
			selection();
			break;
		}
	}
}