package com.rumos.bank;

import java.util.Locale;
import java.util.Scanner;

import com.rumos.bank.administration.MenuADM;

public class Menu {

	public void displayMenu() {
		System.out.println("Welcom,\nChoose a category:"
				+ "\n1 - ADM"
				+ "\n2 - ATM"
				+ "\n3 - Exit");
	}
	
	public void selection() {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		String choose = sc.nextLine();
		switch(choose) {
		case "1":
			MenuADM ADMmenu = new MenuADM();
			ADMmenu.displayMenu();
			ADMmenu.selection();
			break;
		case "2":
			// TODO: ATM
			System.out.println("2 ATM");
			System.out.println("NAO ESTA FEITO AINDA!!");
			break;
		case "3":
			// TODO: Sair da app
			System.out.println("Exit");
			break;
		default:
			System.out.print("Wrong, please do it again:");
			selection();
			break;
		}
		sc.close();
	}
}
