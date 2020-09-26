package core;

import java.util.Scanner;

public class CLI {
	// Internal variable
	private static FileParser ioFile = new FileParser();
	private static Scanner userInput = new Scanner(System.in);
	private static boolean tempBoolean = false;
	private static Double tempDouble = 0.0;
	private static Matrix tempMatrix;
	private static int tempInt = 0;

	// Commonly used method
	private static String stringInput() {
		String tempString = "";
		System.out.print(">> ");
		tempString = userInput.nextLine();
		return tempString;
	}


	private static Matrix matrixInput() {
		// Input type Interface
		String tempString = "", stringMemory = "";
		System.out.println("\nInput matriks");
		System.out.println("1. File");
		System.out.println("2. Keyboard");
		while (true) {
			tempString = CLI.stringInput();
			// File Input
			if (tempString.equals("1")) {
				System.out.println("Masukan nama file (termasuk ekstensi)");
				tempString = CLI.stringInput();
				if (!ioFile.readFile(tempString)) {
					stringMemory = ioFile.stringRead();
					ioFile.closeFile();
					return Matrix.stringToMatrix(stringMemory);
				}
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

	private static void dataWrite(String stream) {
		String tempString = "";
		System.out.print("Nama file : ");
		tempString = userInput.nextLine();
		ioFile.writeFile(tempString);
		ioFile.stringWrite(stream);
		ioFile.closeFile();
	}

	// Menu Method
	private static void determinantMenu() {
		// Determinant interface
		String tempString = "", stringMemory = "";
		System.out.println("\nDeterminan");
		System.out.println("1. Metode Kofaktor");
		System.out.println("2. Metode Reduksi Baris");
		while (true) {
			tempString = CLI.stringInput();
			if (tempString.equals("1") || tempString.equals("2")) {
				stringMemory = tempString;
				tempMatrix = CLI.matrixInput();
				break;
			}
			else
				System.out.println("Masukkan tidak diketahui");
		}
		// Printing matrix
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
		System.out.println("Simpan hasil dalam file? (y/n)");
		tempString = CLI.stringInput();

		if (tempString.equals("y") || tempString.equals("Y"))
			dataWrite(Matrix.matrixToString(tempMatrix) + "Determinan : " + Double.toString(tempDouble));
	}

	private static void systemOfLinearEqMenu() {
		// Linear Equation Interface
		String tempString = "";
		System.out.println("\nSistem Persamaan Linier");
		System.out.println("1. Metode eliminasi Gauss");
		System.out.println("2. Metode eliminasi Gauss-Jordan");
		System.out.println("3. Matriks balikan");
		System.out.println("4. Kaidah Cramer");

		// Matrix input
		while (true) {
			tempString = CLI.stringInput();
			if (tempString.equals("1") || tempString.equals("2")) {
				tempMatrix = CLI.matrixInput();
				break;
			}
			// Cramer & Inverse require square matrix
			else if (tempString.equals("3") || tempString.equals("4")) {
				while (true) {
					tempMatrix = CLI.matrixInput();
					if (tempMatrix.getRow() == (tempMatrix.getColumn() - 1)) // Including augmented vector
						break;
					else
						System.out.println("Matriks masukan bukan matriks persegi");
				}
				break;
			}
			else
				System.out.println("Masukkan tidak diketahui");
		}

		// Printing matrix
		if (tempMatrix.getRow() < 11) {
			System.out.println("Matriks masukkan");
			tempMatrix.printMatrix();
		}
		System.out.println("Hasil operasi");

		// Determinant verification
		Matrix tempDet = new Matrix(tempMatrix.getRow(), tempMatrix.getColumn() - 1);
		boolean isZeroDet = false;
		for (int i = 0 ; i < tempMatrix.getRow() ; i++) {
			for (int j = 0 ; j < (tempMatrix.getColumn() - 1) ; j++)
				tempDet.matrix[i][j] = tempMatrix.matrix[i][j];
		}
		isZeroDet = (tempDet.cofactorDet() == 0.0);

		if (tempString.equals("1") || tempString.equals("2")) {
			if (tempString.equals("1"))
				tempMatrix.gaussianElimination();
			else
				tempMatrix.gaussJordanElimination();
			tempMatrix.printMatrix();
		}
		else if (tempString.equals("3") && !isZeroDet) {
			// Inverse
		}
		else if (tempString.equals("4") && !isZeroDet) {
			double tempVD[] = tempMatrix.cramerMethod();
			for (int i = 0 ; i < tempMatrix.getRow() ; i++)
				System.out.println(Double.toString(tempVD[i]));
		}
		else if (isZeroDet)
			System.out.println("Determinan dari matriks adalah nol");

	}

	// Main Method
	public static void main(String args[]) {
		String tempString = "";
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
			tempString = CLI.stringInput();

			// Test case
			if (tempString.equals("1"))
				CLI.systemOfLinearEqMenu();
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
	}

}
