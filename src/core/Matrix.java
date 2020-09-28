package core;

public class Matrix {
	// Attribute
	private int row;
	private int column;
	public double matrix[][];

	// Constructor
	public Matrix(int r, int c) {
		row = r;
		column = c;
		matrix = new double[r][c];
		// Zero matrix initalisation
		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				matrix[i][j] = 0;
	}

	// Selector Method
	public int getRow() { return row; }
	public void setRow(int row) { this.row = row; }
	public int getColumn() { return column; }
	public void setColumn(int column) { this.column = column; }



	// Conversion method
	public static Matrix stringToMatrix(String stream) {
		// FIXME : Just make it shorter
		// Count Size
		int rRow = 0, rCol = 0;
		boolean readingN = false, firstRow = true;
		for (int i = 0; i < stream.length(); i++) { // maybe it split to row & column read for lower instruction
			if ((Character.toString(stream.charAt(i)).matches("[0-9]|\\.")) && !readingN && firstRow) {
				rCol++;
				readingN = true;
			}
			if (Character.toString(stream.charAt(i)).matches(" "))
				readingN = false;

			if (Character.toString(stream.charAt(i)).matches("\n")) {
				firstRow = false;
				rRow++;
			}
		}

		// Initalisation
		Matrix readMatrix = new Matrix(rRow, rCol);

		// Convert
		try {
			String temporaryNumber = "";
			int currentIndex = 0;
			for (int i = 0 ; i < stream.length() ; i++) {
				if (Character.toString(stream.charAt(i)).matches("[0-9]|-|\\.")) {
					readingN = true;
					if (readingN)
						temporaryNumber = temporaryNumber + Character.toString(stream.charAt(i));
				}
				if (Character.toString(stream.charAt(i)).matches(" |\n")) {
					readingN = false;
					readMatrix.matrix[currentIndex / rCol][currentIndex % rCol] = Double.parseDouble(temporaryNumber);
					temporaryNumber = "";
					currentIndex++;
				}
			}
		} catch (NumberFormatException emptystring) {
			return new Matrix(0, 0);
		}
		return readMatrix;
	}

	public static String matrixToString(Matrix matrixData) {
		String tempMString = "";
		for (int i = 0; i < matrixData.getRow(); i++) {
			for (int j = 0; j < matrixData.getColumn(); j++) {
				tempMString = tempMString + Double.toString(matrixData.matrix[i][j]);
				if (j != (matrixData.getColumn() - 1))
					tempMString = tempMString + " ";
			}
			tempMString = tempMString + "\n";
		}

		return tempMString;
	}



	// Other method
	public void printMatrix() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				String rawString = Double.toString(matrix[i][j]);
				if (rawString.endsWith(".0"))
					rawString = rawString.replace(".0","");
				if (rawString.equals("-0"))
					rawString = rawString.replace("-0","0");
				System.out.print(rawString + " ");
			}
			System.out.println();
		}
	}



	// Row & Column operation
	public void swapRow(int r1, int r2) {
		double tempDB = 0;
		for (int j = 0 ; j < column ; j++) {
			tempDB = matrix[r1][j];
			matrix[r1][j] = matrix[r2][j];
			matrix[r2][j] = tempDB;
		}
	}

	public void multiplyRow(int rDst, double m) {
		for (int j = 0 ; j < column ; j++)
			matrix[rDst][j] *= m;
	}

	public void sumRow(int rSrc, int rDst, double m) {
		for (int j = 0 ; j < column ; j++)
			matrix[rDst][j] += (matrix[rSrc][j] * m);
	}

	public void replaceColumn(int cDst, double rColumn[]) {
		for (int i = 0 ; i < row ; i++)
			matrix[i][cDst] = rColumn[i];
	}



	// System of Linear Equation Method
	public void gaussianElimination() {
		for (int i = 0 ; i < row ; i++) {
			for (int j = 0 ; j < column - 1 ; j++) { // Assuming input are augmented matrix
				if (matrix[i][j] != 0.0) {
					for (int a = i + 1 ; a < row ; a++)
						this.sumRow(i,a,((-1) * (matrix[a][j] / matrix[i][j])));
					break;
				}
			}
		}
	}

	public void gaussJordanElimination() {
		this.gaussianElimination();
		for (int i = 0 ; i < row ; i++) {
			for (int j = 0 ; j < column - 1 ; j++) {
				if (matrix[i][j] != 0.0) {
					this.multiplyRow(i,1/(matrix[i][j]));
					break;
				}
			}
		}
	}

	public double[] cramerMethod() {
		// Copying matrix to tempCramer and splitting to new vector
		Matrix tempCramer = new Matrix(row,column - 1);
		double originalDet = 0;
		double vectorResult[] = new double[row];
		double vectorAug[] = new double[row];
		for (int i = 0 ; i < row ; i++) {
			vectorAug[i] = this.matrix[i][column-1];
			for (int j = 0 ; j < (column - 1) ; j++)
				tempCramer.matrix[i][j] = this.matrix[i][j];
		}
		originalDet = tempCramer.cofactorDet(); // Using cofactor because RR got bug currently xd
		for (int i = 0 ; i < row ; i++) {
			tempCramer.replaceColumn(i,vectorAug);
			vectorResult[i] = (tempCramer.cofactorDet() / originalDet);
			for (int a = 0 ; a < row ; a++)
				tempCramer.matrix[a][i] = this.matrix[a][i];
		}
		return vectorResult;
	}


	public String eliminationRREFMatrix() {
		// Make sure this matrix already reduced
		this.gaussJordanElimination();
		String writeString = "";

		// Gauss-Jordan Elimination for upper diagonal
		for (int i = row-1 ; i >= 0 ; i--) {
			for (int j = 0 ; j < column - 1 ; j++) {
				if (matrix[i][j] != 0.0) {
					for (int a = i - 1 ; a >= 0 ; a--)
						this.sumRow(i,a,((-1) * (matrix[a][j] / matrix[i][j])));
					break;
				}
			}
		}

		// Printing elimination result
		System.out.println("Hasil operasi eliminasi");
		this.printMatrix();
		writeString = "Hasil operasi eliminasi\n" + Matrix.matrixToString(this) + "\n";

		// Scan for inconsistent equation

		int zeroRowCount = 0;
		for (int i = 0 ; i < row ; i++) {
			boolean isRowNonZero = false;
			for (int j = 0 ; j < column - 1 ; j++)
				if (matrix[i][j] != 0.0)
					isRowNonZero = true;
			if ((matrix[i][column-1] != 0.0) && (!isRowNonZero)) {
				System.out.println("Sistem persamaan tidak konsisten");
				return "Sistem persamaan tidak konsisten";
			}
			else if (!isRowNonZero)
				zeroRowCount++;
		}

		// Case when zero matrix
		if (zeroRowCount == row) {
			System.out.println("Matriks adalah matriks nol");
			return "Matriks adalah matriks nol\n";
		}

		// Checking relation with other variable
		boolean freeVariable[] = new boolean[column-1];
		for (int i = 0 ; i < column - 1 ; i++)
			freeVariable[i] = true;

		for (int i = 0 ; i < row ; i++) {
			for (int j = 0 ; j < (column - 1) ; j++) {
				if (matrix[i][j] != 0.0) {
					freeVariable[j] = false;
					for (int a = j + 1 ; a < (column - 1) ; a++) {
						if (matrix[i][a] != 0.0)
							freeVariable[a] = true;
					}
					break;
				}

			}
		}

		// Hohoho
		for (int j = 0 ; j < (column - 1) ; j++) {
			if (freeVariable[j]) {
				char tempChar = (char) (j+97); // 97 is ASCII for 'a', use 65 for capital 'A'
				System.out.println("x" + Integer.toString(j+1) + " = " + Character.toString(tempChar));
				writeString = writeString + "x" + Integer.toString(j+1) + " = " + Character.toString(tempChar) + "\n";
			}
			else {
				String solutionBuilder = " ";
				for (int i = 0 ; i < row ; i++) {
					if (matrix[i][j] != 0) {
						for (int col = 0 ; col < (column - 1) ; col++) {
							// Case of non-free variable
							if ((matrix[i][col] != 0) && (col != j) && !freeVariable[col]) {
								String doubleStringConvert = Double.toString(-matrix[i][col]);
								if (doubleStringConvert.startsWith("-"))
									doubleStringConvert = doubleStringConvert.replace("-","- ");
								else
									doubleStringConvert = "+ " + doubleStringConvert;
								if (doubleStringConvert.endsWith(".0"))
									doubleStringConvert = doubleStringConvert.replace(".0","");
								solutionBuilder = solutionBuilder + doubleStringConvert + "x" + Integer.toString(col+1) + " ";
							}
							// Case of free variable
							else if ((matrix[i][col] != 0) && (col != j)) {
								String doubleStringConvert = Double.toString(-matrix[i][col]);
								if (doubleStringConvert.startsWith("-"))
									doubleStringConvert = doubleStringConvert.replace("-","- ");
								else
									doubleStringConvert = "+ " + doubleStringConvert;
								if (doubleStringConvert.endsWith(".0"))
									doubleStringConvert = doubleStringConvert.replace(".0","");
								char tempChar = (char) (col+97);
								solutionBuilder = solutionBuilder + doubleStringConvert + Character.toString(tempChar) + " ";
							}
						}
						String constantValue = Double.toString(matrix[i][column-1]);
						if (constantValue.endsWith(".0"))
							constantValue = constantValue.replace(".0","");
						System.out.println("x" + Integer.toString(j+1) + " = " + constantValue + solutionBuilder);
						writeString = writeString + "x" + Integer.toString(j+1) + " = " + constantValue + solutionBuilder + "\n";
						break;
					}
				}
			}
		}
		return writeString;
	}



	// Determinant method
	public double cofactorDet() {
		// NaN flag if not square matrix
		if (row != column) {
			return Double.NaN;
		}

		// Base case, 2x2 Matrix Determinant
		if ((row == 2) && (column == 2))
			return (this.matrix[0][0] * this.matrix[1][1] - this.matrix[0][1] * this.matrix[1][0]);
		// Recursive case
		double det = 0;
		boolean skip = false;
		int p = 0;
		Matrix minor = new Matrix(row - 1, column - 1);
		for (int i = 0; i < row; i++) {
			p = 0;
			skip = false;
			for (int j = 0; j < row - 1; j++) {
				for (int k = 0; k < column - 1; k++) {
					if ((i == j) && !skip) {
						p++;
						skip = true;
					}
					minor.matrix[j][k] = matrix[p][k + 1];
				}
				p++;
			}
			det += (this.matrix[i][0] * minor.cofactorDet() * (1 - 2 * (i & 1)));
		}

		return det;
	}

	public double reducedRowDet() { // FIXME : Linear combination
		// NaN flag if not square matrix
		if (row != column) {
			return Double.NaN;
		}

		// Initalisation and copying matrix value
		double det = 1, multiplier = 0;
		Matrix temp = new Matrix(row,column);
		for (int i = 0 ; i < row ; i++)
			for (int j = 0 ; j < column ; j++)
				temp.matrix[i][j] = this.matrix[i][j];

		// Diagonal row swap, try make all diagonal non-zero
		boolean negated = false;
		for (int i = 0 ; i < row ; i++) {
			if (temp.matrix[i][i] == 0.0) {
				for (int a = i + 1 ; a < row ; a++) {
					if (temp.matrix[a][i] != 0.0) {
						temp.swapRow(a,i);
						negated = !negated;			// Single swap correspond multiplication by (-1) on resulting determinant
						break;
					}
				}
			}
		}

		// Diagonal scan
		for (int i = 0 ; i < row ; i++)
			if (temp.matrix[i][i] == 0.0)
				return 0.0;

		for (int i = 0 ; i < column ; i++) {
			// Multiplication Row Operation
			multiplier = temp.matrix[i][i]; 	// Saving multiplier
			det *= multiplier;					// Determinant will changed by multiplication factor x if multiplying with 1/x
			if (multiplier != 0.0)
				temp.multiplyRow(i,1/multiplier);
			// Row Addition Operation
			// No change in determinant value
			for (int j = i + 1 ; j < row ; j++) {
				multiplier = temp.matrix[j][i];
				temp.sumRow(i,j,-multiplier);
			}
		}
		if (negated)
			return (-det);
		return det;
	}



	// Inverse method
	public static Matrix inverseMatrix(Matrix M) {
		Matrix M1 = new Matrix(M.getRow(), M.getColumn()); // matriks adjoin yang nantinya menjadi matriks inverse
		Matrix kofaktor = new Matrix(M.getRow(), M.getColumn());
		int i, j;
		double determinan;
		if (M1.matrix.length == 1) {
			M1.matrix[0][0] = (1 / M1.matrix[0][0]) * M1.matrix[0][0];
			return M1;
		}
		determinan = (1 / M.cofactorDet());
		for (i = 0; i < M.getRow(); i++) {
			for (j = 0; j < M.getColumn(); j++) {
				kofaktor.matrix[i][j] = Matrix.minorMatrix(M, i, j).cofactorDet() * ((i + j) % 2 == 0 ? 1 : -1);
			}
		}
		kofaktor.transposeMatrix();
		M1 = kofaktor;
		for (i = 0; i < M.getRow(); i++) {
			for (j = 0; j < M.getColumn(); j++) {
				M1.matrix[i][j] *= determinan;
			}
		}

		return M1;
	}

	public void transposeMatrix() {
		int i, j;
		Matrix M2 = new Matrix(column, row);
		for (i = 0; i < row; i++) {
			for (j = 0; j < column; j++) {
				M2.matrix[j][i] = matrix[i][j];
			}
		}
		matrix = M2.matrix;
		int temp = row;
		row = column;
		column = temp;
	}

	public static Matrix minorMatrix(Matrix M, int k, int l) { // menghasilkan matriks minor dari entri i dan j
		Matrix Minor = new Matrix(M.getRow() - 1, M.getColumn() - 1);
		int i = 0, j = 0, m = 0, n = 0;
		while (i < M.getRow())  {
			if (i == l) {
				i++;
				continue;
			}

			while (j < M.getColumn()) {
				if (j == k) {
					j++;
					continue;
				}
				Minor.matrix[n][m] = M.matrix[i][j];
				j++;
				m++;

			}
		i++;
		n++;
		}
		return Minor;
	}
}
