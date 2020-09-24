package core;

import java.util.Scanner;

public class CLI {
	
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		String menuInput = "";
		boolean exit = false;
		int tempInt = 0;
		// Main menu loop
		do {
			// Main Menu interface
			System.out.println("MENU");
			System.out.println("1. Sistem Persamaan Linier");
			System.out.println("2. Determinan");
			System.out.println("3. Matriks balikan");
			System.out.println("4. Interpolasi Polinom");
			System.out.println("5. Regresi linier berganda");
			System.out.println("6. Keluar");
			
			// Input
			menuInput = input.nextLine();
			// Exception handler for casting integer
			try { 
				tempInt = Integer.parseInt(menuInput);
			}
			catch (NumberFormatException nfe) {
				
			}
			
			
			// Test case
			switch (tempInt) {
				case 1:
					System.out.println("TBA");
					break;
				case 2:
					System.out.println("TBA");
					break;
				case 3:
					System.out.println("TBA");
					break;
				case 4:
					System.out.println("TBA");
					break;
				case 5:
					System.out.println("TBA");
					break;
				case 6:
					exit = true;
					break;
			}
			
		} while (!exit);
		
		// Exit sequence
		input.close();


		// ---------------- testing purpose ----------------
		// det
//		Matrix kek = new Matrix(2,2);
//		for (int i = 0 ; i < 2 ; i++)
//			for (int j = 0 ; j < 2 ; j++)
//				kek.matrix[i][j] = input.nextInt();
//
//		System.out.println(Double.toString(kek.reducedRowDet()));

		// file parser
//		boolean exceptionRaised = false;
//		System.out.println("TOPKEK");
//		FileParser readInput = new FileParser();
//		String fn = input.nextLine();
//		exceptionRaised = readInput.writeFile(fn);
//		if (!exceptionRaised) {
//			readInput.stringWrite(input.nextLine());
//			readInput.closeFile();
//			readInput.readFile(fn);
//			System.out.print(readInput.charStringRead());
		
//		}
		// raw string
		System.out.println("Raw string file");
		FileParser read = new FileParser();
		read.readFile("anotherone.txt");
		String lul = read.stringRead();//.replaceAll("\\d","1");//.replaceAll("\\n", "");
		System.out.print(lul);
		System.out.println();
		Matrix tp = Matrix.stringToMatrix(lul);
//		for (int i = 0 ; i < lul.length() ; i++) 
//			System.out.println(Integer.toString(i) + " -> " + lul.charAt(i));
		
		// object 
		System.out.println("Object");
		tp.printMatrix();
		
		// processed raw string
		String prc = Matrix.matrixToString(tp);
		System.out.println("\nRaw string object");
		System.out.print(prc);
		
		read.writeFile("cool.txt");
		read.stringWrite(prc);
		// ---------------- testing purpose ----------------
	}

}
