package core;

import java.util.Scanner;
import java.lang.Math;

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

				return Matrix.stringToMatrix(tempString);
			}

			// Invalid Input
			else
				System.out.println("Masukan tidak diketahui");
		}
	}

	private static Matrix interpolationMatrixInput() {
		// Input type Interface
		String tempString = "", stringMemory = "";
		System.out.println("\nInput sampel titik");
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
					System.out.print("Derajat polinom : ");
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
				for (int i = 0 ; i < rowSize + 1 ; i++) {
					System.out.print(String.format("Titik ke-%d : ",(i+1)));
					tempConcat = userInput.nextLine().trim();
					tempString = tempString + (tempConcat + "\n");
				}
				System.out.print(String.format("Nilai x yang akan ditaksir : "));
				tempConcat = userInput.nextLine().trim();
				tempString = tempString + (tempConcat + "\n");

				return Matrix.stringToMatrix(tempString);
			}

			// Invalid Input
			else
				System.out.println("Masukan tidak diketahui");
		}
	}

	private static Matrix[] regressionMatrixInput() {
		// Input type Interface
		String tempString = "", stringMemory = "", estimateValue = "";
		Matrix matrixVectorTemp[] = new Matrix[2];
		System.out.println("\nInput data tabel");
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
					matrixVectorTemp[0] = Matrix.stringToMatrix(stringMemory);
				}
				else
					System.out.println("File tidak ditemukan");
			}

			// String Input
			else if (tempString.equals("2")) {
				// Size Row
				System.out.println("Masukkan \"akhir\" untuk mengakhiri input");
				// User matrix element input
				tempString = ""; // Flush current tempString
				String tempConcat = "";
				for (int i = 0 ; !tempConcat.equals("akhir") ; i++) {
					System.out.print(String.format("Baris data ke-%d : ",(i+1)));
					tempConcat = userInput.nextLine().trim();
					if (!tempConcat.equals("akhir"))
						tempString = tempString + (tempConcat + "\n");
				}
				matrixVectorTemp[0] = Matrix.stringToMatrix(tempString);
			}
			
			// Invalid Input
			else
				System.out.println("Masukan tidak diketahui");
			System.out.print("Nilai xk yang akan ditaksir : ");
			estimateValue = userInput.nextLine().trim() + "\n";
			matrixVectorTemp[1] = Matrix.stringToMatrix(estimateValue);
			return matrixVectorTemp;
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
		System.out.println("\n-- Determinan --");
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
		System.out.println("\n-- Sistem Persamaan Linier --");
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
				tempMatrix.gaussianElimination(); // For some reason, probably due rounding error,
				tempMatrix.gaussianElimination(); // Need 2x call to get proper row echelon form
				writeString = writeString + "Hasil operasi eliminasi Gauss\n" + Matrix.matrixToString(tempMatrix) + "\n";
			}
			else {
				tempMatrix.gaussJordanElimination();
				tempMatrix.gaussJordanElimination();
				writeString = writeString + "Hasil operasi eliminasi Gauss-Jordan\n" + Matrix.matrixToString(tempMatrix) + "\n";
			}
			writeString = writeString + tempMatrix.eliminationRREFMatrix();
		}
		else if (tempString.equals("3") && !isZeroDet) {
			Matrix squareTempMatrix = new Matrix(tempMatrix.getRow(),tempMatrix.getColumn()-1);
			for (int i = 0 ; i < tempMatrix.getRow() ; i++)
				for (int j = 0 ; j < tempMatrix.getColumn() - 1 ; j++)
					squareTempMatrix.matrix[i][j] = tempMatrix.matrix[i][j];
			squareTempMatrix = Matrix.inverseMatrix(squareTempMatrix);
			writeString =  writeString + "Hasil invers\n" + Matrix.matrixToString(squareTempMatrix) + "Solusi\n";
			for (int i = 0 ; i < squareTempMatrix.getRow() ; i++) {
				double multiplicationResult = 0;
				for (int j = 0 ; j < squareTempMatrix.getColumn() ; j++)
					multiplicationResult += squareTempMatrix.matrix[i][j] * tempMatrix.matrix[j][tempMatrix.getColumn()-1];
				writeString = String.format("%sx%d = %.3f\n", writeString, (i+1), multiplicationResult);
			}
		}
		else if (tempString.equals("4") && !isZeroDet) {
			double tempVD[] = tempMatrix.cramerMethod();
			for (int i = 0 ; i < tempMatrix.getRow() ; i++)
				writeString = String.format("%sx%d = %.2f\n", writeString, (i+1), tempVD[i]);
		}
		else if (isZeroDet)
			writeString = writeString + "Determinan dari matriks adalah nol\n";
		System.out.print(writeString);
		dataWrite(writeString);
	}

	private static void inverseMenu() {
		String writeString = "";
		Matrix tempMatrix;
		System.out.println("-- Invers matriks --");
		while (true) {
			tempMatrix = CLI.matrixInput();
			if (tempMatrix.getColumn() != tempMatrix.getRow())
				System.out.println("Matriks masukkan bukanlah matriks persegi");
			else
				break;
		}
		// Printing matrix
		if (tempMatrix.getRow() < 11)
			writeString = "Matriks masukkan\n" + Matrix.matrixToString(tempMatrix);

		// Inverse calculation
		if (tempMatrix.cofactorDet() != 0) {
			tempMatrix = Matrix.inverseMatrix(tempMatrix);
			writeString = writeString + "Hasil Invers\n"+ Matrix.matrixToString(tempMatrix);
		}
		else
			writeString = writeString + "Determinan matriks adalah 0\n";

		System.out.println(writeString);
		dataWrite(writeString);
	}

	// Put Interpolation here
	private static void interpolationMenu() {
		String writeString = "";
		System.out.println("-- Interpolasi polinom --");
		System.out.print("Titik sampel input n + 1 untuk polinom derajat n");
		Matrix tempMatrix = CLI.interpolationMatrixInput();
		double estimate[] = new double[2];
		estimate[0] = tempMatrix.matrix[tempMatrix.getRow() - 1][0];
		estimate[1] = 0.0;
		tempMatrix.setRow(tempMatrix.getRow() - 1);
		writeString = "Sampel titik\n" + Matrix.matrixToString(tempMatrix);
		double polyCoefficient[] = Matrix.polynomialInterpolation(tempMatrix);
		for (int i = 0 ; i < polyCoefficient.length ; i++)
			estimate[1] += polyCoefficient[i] * Math.pow(estimate[0], i);
		String functionBuilder = "f(x) = ";
		for (int i = polyCoefficient.length - 1 ; i >= 0  ; i--) {
			if ((polyCoefficient[i] > 0) && (i != (polyCoefficient.length - 1)))
				functionBuilder = String.format("%s+ %.2fx^%d ",functionBuilder,polyCoefficient[i],i);
			else if (polyCoefficient[i] > 0)
				functionBuilder = String.format("%s%.2fx^%d ",functionBuilder,polyCoefficient[i],i);
			else if (polyCoefficient[i] == 0.0)
				functionBuilder = String.format("%s",functionBuilder);
			else
				functionBuilder = String.format("%s- %.2fx^%d ",functionBuilder,-polyCoefficient[i],i);
		}
		writeString = writeString + "Hasil polinom interpolasi\n" + functionBuilder + "\n" + String.format("Hasil estimasi f(%.3f) = %.3f\n", estimate[0], estimate[1]);
		System.out.println(writeString);
		CLI.dataWrite(writeString);
	}

	private static void regressionMenu() {
		String writeString = "";
		System.out.println("-- Regresi linier berganda --");
		Matrix tempMatrixVector[] = CLI.regressionMatrixInput();
		Matrix tempMatrix = tempMatrixVector[0];
		writeString = "Matriks masukkan\n" + Matrix.matrixToString(tempMatrix);
		tempMatrix = Matrix.regresi(tempMatrix);
		writeString = writeString + "Hasil Regresi\n" + "y = ";
		for (int i = 0 ; i < tempMatrix.getRow() ; i++) {
			if ((tempMatrix.matrix[i][0] > 0) && (i != 0))
				writeString = String.format("%s+ %.3fx%d ",writeString,tempMatrix.matrix[i][0],i);
			else if (tempMatrix.matrix[i][0] > 0)
				writeString = String.format("%s%.3f ",writeString,tempMatrix.matrix[i][0]);
			else if ((tempMatrix.matrix[i][0] < 0) && (i == 0))
				writeString = String.format("%s- %.3f ",writeString,-tempMatrix.matrix[i][0]);
			else if (tempMatrix.matrix[i][0] == 0.0)
				writeString = String.format("%s",writeString);
			else
				writeString = String.format("%s- %.3fx%d ",writeString,-tempMatrix.matrix[i][0],i);
		}
		writeString = writeString + "\nEstimasi\n";
		writeString = writeString + "y(xk) = ";
		double regResult = tempMatrix.matrix[0][0];
		for (int i = 0 ; i < tempMatrixVector[1].getColumn() ; i++)
			regResult += tempMatrixVector[1].matrix[0][i]*tempMatrix.matrix[i+1][0];
		
		writeString = writeString + Double.toString(regResult);
		System.out.println(writeString);
		CLI.dataWrite(writeString);
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
				CLI.inverseMenu();
			else if (tempString.equals("4"))
				CLI.interpolationMenu();
			else if (tempString.equals("5"))
				CLI.regressionMenu();
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
