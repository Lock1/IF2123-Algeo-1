package core;

import java.util.Scanner;

public class CLI {
	// Internal variable
	private static FileParser ioFile = new FileParser();
	private static Scanner userInput = new Scanner(System.in);

	// Commonly used method
	private static String stringInput() {
		System.out.print(">> ");
		String tempString = userInput.nextLine();
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
				int rowSize = 0;
				while (true) {
					System.out.print("Ukuran Baris : ");
					tempString = userInput.nextLine();
					try {
						rowSize = Integer.parseInt(tempString);
						break;
					}
					catch (NumberFormatException nfe) {
						System.out.println("Masukan tidak diketahui");
					}
				}
				// User matrix element input
				tempString = ""; // Flush current tempString
				String tempConcat = "";
				for (int i = 0 ; i < rowSize ; i++) {
					System.out.print(String.format("Baris ke-%d : ",(i+1)));
					tempConcat = userInput.nextLine().trim();
					tempString = tempString + (tempConcat + "\n");
				}

				// Internal parse, no regex :<
				boolean isValidMatrixString = true;
				for (int i = 0 ; i < tempString.length() ; i++)
					if (!Character.toString(tempString.charAt(i)).matches("-|[0-9]|\\.|\n| "))
						isValidMatrixString = false;

				// Returning branch
				if (isValidMatrixString)
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
		System.out.println("Simpan hasil dalam file? (y/n)");
		String tempString = CLI.stringInput();
		if (tempString.equals("y") || tempString.equals("Y")) {
			System.out.print("Nama file : ");
			tempString = userInput.nextLine();
			ioFile.writeFile(tempString);
			ioFile.stringWrite(stream);
			ioFile.closeFile();
		}
	}

	// Menu Method
	private static void determinantMenu() {
		// Determinant interface
		String tempString = "", writeString = "";
		Matrix tempMatrix;
		System.out.println("\nDeterminan");
		System.out.println("1. Metode Kofaktor");
		System.out.println("2. Metode Reduksi Baris");
		while (true) {
			tempString = CLI.stringInput();
			if (tempString.equals("1") || tempString.equals("2")) {
				tempMatrix = CLI.matrixInput();
				break;
			}
			else
				System.out.println("Masukkan tidak diketahui");
		}
		// Printing matrix
		if (tempMatrix.getRow() < 11)
			writeString = "Matriks masukkan\n" + Matrix.matrixToString(tempMatrix);

		// Deteminant calculation
		double determinantResult = 0.0;
		if (tempString.equals("1"))
			determinantResult = tempMatrix.cofactorDet();
		else
			determinantResult = tempMatrix.reducedRowDet();

		// Output
		writeString = String.format("%sDeterminan : %.3f", writeString, determinantResult);
		System.out.println(writeString);
		dataWrite(writeString);
	}

	private static void systemOfLinearEqMenu() {
		// Linear Equation Interface
		String tempString = "", writeString = "";
		Matrix tempMatrix;
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
		if (tempMatrix.getRow() < 11)
			writeString = "Matriks masukkan\n" + Matrix.matrixToString(tempMatrix) + "\n";

		// Determinant verification
		Matrix tempDet = new Matrix(tempMatrix.getRow(), tempMatrix.getColumn() - 1);
		for (int i = 0 ; i < tempMatrix.getRow() ; i++) {
			for (int j = 0 ; j < (tempMatrix.getColumn() - 1) ; j++)
				tempDet.matrix[i][j] = tempMatrix.matrix[i][j];
		}
		boolean isZeroDet = (tempDet.cofactorDet() == 0.0);

		// Menu evaluation
		if (tempString.equals("1") || tempString.equals("2")) {
			if (tempString.equals("1")) {
				tempMatrix.gaussianElimination();
				writeString = writeString + "Hasil operasi elimininasi Gauss\n" + Matrix.matrixToString(tempMatrix) + "\n";
			}
			writeString = writeString + tempMatrix.eliminationRREFMatrix();
		}
		else if (tempString.equals("3") && !isZeroDet) {
			Matrix squareTempMatrix = new Matrix(tempMatrix.getRow(),tempMatrix.getColumn()-1);
			for (int i = 0 ; i < tempMatrix.getRow() ; i++)
				for (int j = 0 ; j < tempMatrix.getColumn() - 1 ; j++)
					squareTempMatrix.matrix[i][j] = tempMatrix.matrix[i][j];
			squareTempMatrix = Matrix.inverseMatrix(squareTempMatrix);
			squareTempMatrix.printMatrix(); // TODO : Not done, multiplication by vector
		}
		else if (tempString.equals("4") && !isZeroDet) {
			double tempVD[] = tempMatrix.cramerMethod();
			for (int i = 0 ; i < tempMatrix.getRow() ; i++)
				writeString = String.format("%sx%d = %.2f\n", writeString, (i+1), tempVD[i]);
		}
		else if (isZeroDet)
			System.out.println("Determinan dari matriks adalah nol");
		System.out.print(writeString);
		dataWrite(writeString);
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
