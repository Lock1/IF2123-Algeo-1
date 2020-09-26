package core;

public class Matrix {
	// Attribute
	private int row;
	private int column;
	public double matrix[][];


	// Constructor
	public Matrix(int r,int c) {
		row = r;
		column = c;
		matrix = new double[r][c];
		// Zero matrix initalisation
		for (int i = 0 ; i < r ; i++)
			for (int j = 0 ; j < c ; j++)
				matrix[i][j] = 0;
	}



	// Get & Set Method
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}


	// Conversion method
	public static Matrix stringToMatrix(String stream) {
		// FIXME : Just make it shorter
		// Count Size
		int rRow = 0, rCol = 0;
		boolean readingN = false, firstRow = true;
		for (int i = 0 ; i < stream.length() ; i++) { // maybe it split to row & column read for lower instruction
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
		Matrix readMatrix = new Matrix(rRow,rCol);

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
		}
		catch (NumberFormatException emptystring) {
			return new Matrix(0,0);
		}
		return readMatrix;
	}

	public static String matrixToString(Matrix matrixData) {
		String tempMString = "";
		for (int i = 0 ; i < matrixData.getRow() ; i++) {
			for (int j = 0 ; j < matrixData.getColumn() ; j++) {
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
		for (int i = 0 ; i < row ; i++) {
			for (int j = 0 ; j < column ; j++)
				System.out.print(Double.toString(matrix[i][j]) + " ");
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
		boolean found = true; // TODO : Complete elimination
		int minBox = (row < column) ? row : column, bound = 0; // Lowest from row or column
		double multiplier = 1;
		for (int i = 0 ; i < minBox ; i++) {
			found = true;
			if (matrix[i][i] == 0.0) {
				found = false;
				if (i > 0) {
					if (matrix[i-1][i-1] == 0.0)
						bound = 0;
				}
				else
					bound = i + 1;
				for (int a = bound ; a < row ; a++) {
					if (matrix[a][i] != 0.0) {
						this.swapRow(a,i);
						found = true;
					}
				}
			}
			if (!found)
				continue;
			for (int b = i + 1 ; b < row ; b++) {
				multiplier = (-1) * (matrix[b][i] / matrix[i][i]);
				this.sumRow(i,b,multiplier);
			}
		}
	}

	public void gaussJordanElimination() {
		this.gaussianElimination();
		double multiplier = 1;
		int minBox = (row < column) ? row : column;
		for (int i = 0 ; i < minBox ; i++) {
			if (matrix[i][i] == 0.0)
				continue;
			multiplier = matrix[i][i];
			this.multiplyRow(i,1/multiplier);
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
		Matrix minor = new Matrix(row-1,column-1);
		for (int i = 0 ; i < row ; i++) {
			p = 0;
			skip = false;
			for (int j = 0 ; j < row-1 ; j++) {
				for (int k = 0 ; k < column-1 ; k++) {
					if ((i == j) && !skip) {
						p++;
						skip = true;
					}
					minor.matrix[j][k] = this.matrix[p][k+1];
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
}
