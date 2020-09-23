package core;

public class Matrix {
	// Attribute
	private int row;
	private int column;
	public double matrix[][];

	// Constructor
	public Matrix(int b,int k) {
		row = b;
		column = k;
		matrix = new double[b][k];
		// Zero matrix initalisation
		for (int i = 0 ; i < b ; i++)
			for (int j = 0 ; j < k ; j++)
				matrix[i][j] = 0;
	}



	// Get & Set Method
	public int getrow() {
		return row;
	}
	public void setrow(int row) {
		this.row = row;
	}
	public int getcolumn() {
		return column;
	}
	public void setcolumn(int column) {
		this.column = column;
	}



	// Other method
	public double cofactorDet() {
		if ((row == 2) && (column == 2))
			return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
		double det = 0;
		boolean skip = false;
		int cfsign = 0, p = 0;
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
					minor.matrix[j][k] = matrix[p][k+1];
				}
				p++;
			}
			cfsign = 1 - 2 * (i & 1);
			det += (matrix[i][0] * minor.cofactorDet() * cfsign);
		}
		return det;
	}

}
