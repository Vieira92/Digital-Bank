package com.rumos.bank.userInterface;

import java.util.Locale;

import com.rumos.bank.userInterface.MenuADM;
import com.rumos.bank.userInterface.UI;

public class Console {

	{System.out.println("====    Welcome to Rumos Digital Bank    ====\n");}
	{displayMenu();}
	
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
			MenuADM.displayMenuADM();
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
