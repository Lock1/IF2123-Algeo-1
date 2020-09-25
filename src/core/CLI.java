package core;

import java.util.Scanner;

public class CLI {
	// Internal variable
	private static Scanner userInput = new Scanner(System.in);
	private static String tempString = "", stringMemory = "";
	private static boolean tempBoolean = false;
	private static Double tempDouble = 0.0;
	private static Matrix tempMatrix;
	private static int tempInt = 0;


	private static void stringInput() {
		System.out.print(">> ");
		tempString = userInput.nextLine();
	}


	// Commonly used method
	private static Matrix matrixInput() {
		FileParser readMatrixFile = new FileParser();
		// Input type Interface
		System.out.println("\nInput matriks");
		System.out.println("1. File");
		System.out.println("2. Keyboard");
		while (true) {
			CLI.stringInput();
			// File Input
			if (tempString.equals("1")) {
				System.out.println("Masukan nama file (termasuk ekstensi)");
				CLI.stringInput();
				if (!readMatrixFile.readFile(tempString))
					return Matrix.stringToMatrix(readMatrixFile.stringRead());
				else
					System.out.println("File tidak ditemukan");
			}

			// String Input
			else if (tempString.equals("2")) {
				// Size Row
				while (true) {
					System.out.print("Ukuran Baris : ");
					tempString = userInput.nextLine();
					try {
						tempInt = Integer.parseInt(tempString);
						break;
					}
					catch (NumberFormatException nfe) {
						System.out.println("Masukan tidak diketahui");
					}
				}
				// User matrix element input
				tempString = ""; // Flush current tempString
				String tempConcat = "";
				for (int i = 0 ; i < tempInt ; i++) {
					System.out.print("Baris ke-" + Integer.toString(i+1) + " : ");
					tempConcat = userInput.nextLine().trim();
					tempString = tempString + (tempConcat + "\n");
				}

				// Internal parse, no regex :<
				tempBoolean = true;
				for (int i = 0 ; i < tempString.length() ; i++)
					if (!Character.toString(tempString.charAt(i)).matches("[0-9]|\\.|\n| "))
						tempBoolean = false;

				// Returning branch
				if (tempBoolean)
					return Matrix.stringToMatrix(tempString);
				else
					return new Matrix(0,0);
			}

			// Invalid Input
			else
				System.out.println("Masukan tidak diketahui");
		}
	}


	// Menu Method
	private static void determinantMenu() {
		// Determinant interface
		System.out.println("\nDeterminan");
		System.out.println("1. Metode Kofaktor");
		System.out.println("2. Metode Reduksi Baris");
		while (true) {
			CLI.stringInput();
			stringMemory = tempString;
			if (tempString.equals("1") || tempString.equals("2")) {
				tempMatrix = CLI.matrixInput();
				break;
			}
			else
				System.out.println("Masukkan tidak diketahui");
		}
		// Printing matrix
		tempMatrix.printMatrix();
		System.out.println(Integer.toString(tempMatrix.getRow()) + " " + Integer.toString(tempMatrix.getColumn()));
		if (tempMatrix.getRow() < 11) {
			System.out.println("Matriks masukkan");
			tempMatrix.printMatrix();
		}
		// Deteminant calculation
		if (stringMemory.equals("1"))
			tempDouble = tempMatrix.cofactorDet();
		else
			tempDouble = tempMatrix.reducedRowDet();

		System.out.println("Determinan : " + Double.toString(tempDouble));
	}



	// Main Method
	public static void main(String args[]) {
		// Main menu loop
		while (true) {
			// Main Menu interface
			System.out.println("MENU");
			System.out.println("1. Sistem Persamaan Linier");
			System.out.println("2. Determinan");
			System.out.println("3. Matriks balikan");
			System.out.println("4. Interpolasi Polinom");
			System.out.println("5. Regresi linier berganda");
			System.out.println("6. Keluar");

			// Input
			CLI.stringInput();

			// Test case
			if (tempString.equals("1"))
				System.out.println("TBA");
			else if (tempString.equals("2"))
				CLI.determinantMenu();
			else if (tempString.equals("3"))
				System.out.println("TBA");
			else if (tempString.equals("4"))
				System.out.println("TBA");
			else if (tempString.equals("5"))
				System.out.println("TBA");
			else if(tempString.equals("6"))
				break;
			else
				System.out.println("Masukan tidak diketahui");
			System.out.println("");
		}

		// Exit sequence
		userInput.close();


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
