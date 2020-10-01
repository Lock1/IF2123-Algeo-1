package core;

import java.lang.Math;

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
				tempMString = tempMString + Matrix.doubleToStringFilter(matrixData.matrix[i][j]);
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
				String rawString = Matrix.doubleToStringFilter(matrix[i][j]);
				System.out.print(rawString + " ");
			}
			System.out.println();
		}
	}

	private static String doubleToStringFilter(double entry) {
		String doubleString = Double.toString(entry);
		if (doubleString.endsWith(".0"))
			doubleString = doubleString.replace(".0","");
		if (doubleString.equals("-0"))
			doubleString = doubleString.replace("-0","0");
		return doubleString;
	}

	private static String doubleToSolutionString(double entry) {
		String doubleStringConvert = Double.toString(entry);
		if (doubleStringConvert.startsWith("-"))
			doubleStringConvert = doubleStringConvert.replace("-","- ");
		else
			doubleStringConvert = "+ " + doubleStringConvert;
		if (doubleStringConvert.endsWith(".0"))
			doubleStringConvert = doubleStringConvert.replace(".0","");
		return doubleStringConvert;
	}

	public boolean isDiagonalNonZero() {

		for (int i = 0 ; i < row ; i++) {
			if (matrix[i][i] == 0.0)
				return false;
		}
		return true;
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
		for (int i = 0 ; i < row ; i++)
			for (int j = 0 ; j < column - 1 ; j++) // Assuming input are augmented matrix
				if (matrix[i][j] != 0.0) {
					for (int a = i + 1 ; a < row ; a++)
						this.sumRow(i,a,((-1) * (matrix[a][j] / matrix[i][j])));
					break;
				}
	}

	public void gaussJordanElimination() {
		this.gaussianElimination();
		for (int i = 0 ; i < row ; i++)
			for (int j = 0 ; j < column - 1 ; j++)
				if (matrix[i][j] != 0.0) {
					this.multiplyRow(i,1/(matrix[i][j]));
					break;
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


	// Complete elimination
	public void completeGaussJordanElimination() {
		// Make sure this matrix already reduced row echelon form
		this.gaussJordanElimination();
		// Gauss-Jordan Elimination for upper diagonal
		for (int i = row-1 ; i >= 0 ; i--)
			for (int j = 0 ; j < column - 1 ; j++)
				if (matrix[i][j] == 1.0) {
					for (int a = i - 1 ; a >= 0 ; a--)
						this.sumRow(i,a,((-1) * (matrix[a][j])));
					break;
				}
	}


	public String eliminationRREFMatrix() {
		this.completeGaussJordanElimination();
		// Sorting matrix
		int minBox = (row > column) ? column - 1 : row;
		for (int i = 0 ; i < minBox ; i++)
			if (matrix[i][i] == 0)
				for (int a = 0 ; a < row ; a++)
					if (matrix[a][i] != 0.0) {
						this.swapRow(a, i);
						break;
					}
		String writeString = "";
		this.printMatrix();
		// Rounding, disable this block if not using rounding
		for (int i = 0 ; i < row ; i++)
			for (int j = 0 ; j < column ; j++)
				if ((matrix[i][j] > 1E10) || (matrix[i][j] < -1E10) || ((matrix[i][j] < 1E-10) && (matrix[i][j] > -1E-10)))
					matrix[i][j] = 0;
		// Printing elimination result
		writeString = "Hasil operasi eliminasi\n" + Matrix.matrixToString(this) + "\n";




		// Scan for inconsistent equation
		int zeroRowCount = 0;
		for (int i = 0 ; i < row ; i++) {
			boolean isRowNonZero = false;
			for (int j = 0 ; j < column - 1 ; j++)
				if (matrix[i][j] != 0.0)
					isRowNonZero = true;
			if ((matrix[i][column-1] != 0.0) && (!isRowNonZero))
				return (writeString + "Sistem persamaan tidak konsisten\n");
			else if (!isRowNonZero)
				zeroRowCount++;
		}

		// Case when zero matrix
		if (zeroRowCount == row)
			return "Matriks adalah matriks nol\n";

		// Checking relation with other variable
		boolean freeVariable[] = new boolean[column-1];
		for (int i = 0 ; i < column - 1 ; i++)
			freeVariable[i] = true;

		for (int i = 0 ; i < row ; i++)
			for (int j = 0 ; j < (column - 1) ; j++)
				if (matrix[i][j] != 0.0) {
					freeVariable[j] = false;
					break;
				}

		// Solution maker
		for (int j = 0 ; j < (column - 1) ; j++) {
			if (freeVariable[j])
				writeString = String.format("%sx%d = %s\n",writeString,(j+1), Character.toString((char) (j+97))); // 97 is ASCII for 'a', use 65 for capital 'A'
			else {
				String solutionBuilder = " ";
				for (int i = 0 ; i < row ; i++)
					if (matrix[i][j] != 0) {
						for (int col = 0 ; col < (column - 1) ; col++)
							if ((matrix[i][col] != 0) && (col != j))
								solutionBuilder = String.format("%s%s%s ", solutionBuilder, Matrix.doubleToSolutionString(-matrix[i][col]), Character.toString((char) (col+97)));
						writeString = String.format("%sx%d = %s%s\n",writeString,(j+1),Matrix.doubleToStringFilter(matrix[i][column-1]),solutionBuilder);
						break;
					}
			}
		}
		return writeString;
	}



	// Determinant method
	public double cofactorDet() {
		// NaN flag if not square matrix
		if (row != column)
			return Double.NaN;

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

	public double reducedRowDet() {
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
		int checkCount = 0, indexPrevSwap = -1;
		while ((!temp.isDiagonalNonZero()) && (checkCount < 50)) { // Brute force but whatever
			for (int i = 0 ; i < row ; i++) {
				if (temp.matrix[i][i] == 0.0) {
					for (int a = 0 ; a < row ; a++) {
						if ((temp.matrix[a][i] != 0.0) && (a != indexPrevSwap)) {
							temp.swapRow(a,i);
							indexPrevSwap = a;
							negated = !negated;			// Single swap correspond multiplication by (-1) on resulting determinant
							break;

						}
					}
				}
			}
			checkCount++;
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

	// Interpolation method
	public static double[] polynomialInterpolation(Matrix pointMatrix) {
		// Point Matrix is Nx2 matrix, holding point on every row
		double coefficientVector[] = new double[pointMatrix.getRow()];
		int polyDegree = pointMatrix.getRow() - 1;
		Matrix coefficientMatrix = new Matrix(polyDegree + 1,polyDegree + 2);
		// Copying information on point matrix and calculating coefficient
		for (int i = 0 ; i < polyDegree + 1; i++)
			for (int j = 0 ; j < polyDegree + 1 ; j++)
				coefficientMatrix.matrix[i][j] = Math.pow(pointMatrix.matrix[i][0], j);
		for (int i = 0 ; i < polyDegree + 1 ; i++)
			coefficientMatrix.matrix[i][polyDegree + 1] = pointMatrix.matrix[i][1];
		coefficientMatrix.completeGaussJordanElimination();
		for (int i = 0 ; i < coefficientMatrix.getRow() ; i++)
			coefficientVector[i] = coefficientMatrix.matrix[i][polyDegree + 1];
		return coefficientVector;
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
				kofaktor.matrix[i][j] = Matrix.minorMatrix(M, i, j).cofactorDet() * (((i + j) % 2 == 0) ? 1 : -1);
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
			if (i == k) {
				i++;
				continue;
			}
			j = 0;
			n = 0;
			while (j < M.getColumn()) {
				if (j == l) {
					j++;
					continue;
				}
				Minor.matrix[m][n] = M.matrix[i][j];
				j++;
				n++;
			}
		i++;
		m++;
		}
		return Minor;
	}

	// Regression method
	public static Matrix regresi(Matrix m) {
		// asumsi matriks sudah augmented
		Matrix temp;
		Matrix temp2;
		Matrix x = new Matrix(m.getRow(), m.getColumn());
		Matrix y = new Matrix(m.getRow(), 1);
		Matrix t = new Matrix(m.getColumn(), m.getRow());
		int n, i, j = 0;
		n = m.getRow();
		x.printMatrix();
		// mengisi matrix x kolom 1
		for (i = 0; i < n; i++) {
			x.matrix[i][0] = 1;

		}
		// mengisi matriks y
		for (i = 0; i < n; i++) {
			y.matrix[i][0] = m.matrix[i][m.getColumn() - 1];

		}
		// mengisi matrix x sisanya
		for (i = 0; i < n; i++) {
			for (j = 1; j < m.getColumn(); j++) {
				x.matrix[i][j] = m.matrix[i][j - 1];
			}
		}
		x.transposeMatrix();
		copyMatrix(x, t);
		x.transposeMatrix();
		System.out.println("---- matrix y----");
		y.printMatrix();
		System.out.println("---- matrix x ----");
		x.printMatrix();
		System.out.println("---- matrix hasil kali transpose dengan x  ----");
		temp = Matrix.kaliMatrix(t, x);
		temp.printMatrix();
		System.out.println("------ kemudian di invese------");
		temp = inverseMatrix(temp);
		temp.printMatrix();
		System.out.println("---- setelah di inverse, proses perkalian transpose x dengan matrix y ----");
		temp2 = Matrix.kaliMatrix(t, y);
		System.out.println("------ setelah itu kalikan matrrix hasil inverse dengan hasip proses tadi ------- ");
		temp = Matrix.kaliMatrix(temp, temp2);
		return temp;
	}

	public static Matrix kaliMatrix(Matrix M1, Matrix M2) {
		// asumsi jumlah kolom M1 dan jumlah baris M2 adalah sama
		int i, j, k;
		Matrix M3 = new Matrix(M1.getRow(), M2.getColumn());
		M3.row = M1.getRow();
		M3.column = M2.getColumn();
		for (i = 0; i < M1.getRow(); i++) {
			for (j = 0; j < M2.getColumn(); j++) {
				for (k = 0; k < M2.getRow(); k++) {
					M3.matrix[i][j] += M1.matrix[i][k] * M2.matrix[k][j];
				}
			}
		}
		return M3;
	}

	public static Matrix copyMatrix(Matrix M1, Matrix Mhasil) {
		int i, j;
		Mhasil.row = M1.getRow();
		Mhasil.column = M1.getColumn();
		for (i = 0; i < M1.getRow(); i++) {
			for (j = 0; j < M1.getColumn(); j++) {
				Mhasil.matrix[i][j] = M1.matrix[i][j];
			}
		}
		return Mhasil;

	}

}
