package pt.rumos.bank.userInterface;

import java.util.Scanner;

public class UI {

	private static Scanner sc = new Scanner(System.in);

	public static void scClose() {
		sc.close();
	}
	
	public static int choose() {
		System.out.println("1 - Yes" + "\n2 - Not");
		int choose = sc.nextInt();
		while (choose != 1 && choose != 2) {
			System.out.print("\nWrong option. Choose again: ");
			choose = sc.nextInt();
		}
		return choose;
	}

	public static int getInt() {
		int number = 0;
		boolean inputNotNull = true;
		while (inputNotNull) {
			sc.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
		    String line = sc.nextLine(); 
		    try {
		        number = Integer.parseInt(line);
		    } catch (NumberFormatException e) {
		        System.err.println("Wrong input! Input only integer numbers please: " + e.getMessage());
		        System.out.println("Wrong input! Input only integer numbers please:1 " + e.getMessage());
		        continue;
		    }
		    if (number == 0) {
		        inputNotNull = false;
		    }
		    return number;
		}
		return number;
	}
		
	public static double getDouble() {
		double number = 0.0;
		boolean inputNotNull = true;
		while (inputNotNull) {
			sc.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
		    String line = sc.nextLine();    
		    try {
		        number = Double.parseDouble(line);
		    } catch (NumberFormatException e) {
		        System.err.println("Wrong input! Input only floating numbers please: " + e.getMessage());
		        System.out.println("Wrong input! Input only floating numbers please:1 " + e.getMessage());
		        continue;
		    }
		    if (number == 0.0) {
		        inputNotNull = false;
		    }
		    return number;
		}
		return number;
	}

	public static String scanLine() {
//		sc.nextLine();
		sc.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
		return sc.nextLine();
	}
}
