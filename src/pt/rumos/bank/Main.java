package pt.rumos.bank;

import pt.rumos.bank.userInterface.Console;
import pt.rumos.bank.userInterface.UI;

public class Main {
	public static void main(String[] args) {

//		AccountDao accountDao = DaoFactory.createAccountDao();
//		List<String> list = accountDao.consultAccountMovement(30);
//		System.out.println("Last movements of account id 30");
//		System.out.println("Date:                   Balance:   Amount:");
//		for (String s : list) {
//			System.out.println(s);
//		}
		
		
		new Console();

		System.out.println("saiu aqui ver o problema");
		UI.scClose();
		System.exit(0);
	}
}
