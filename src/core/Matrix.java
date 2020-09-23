package core;

public class Matrix {
	// Attribute
	private int baris;
	private int kolom;
	public int matrix[][];

	// Constructor
	public Matrix(int b,int k) {
		baris = b;
		kolom = k;
		matrix = new int[b][k];
		// Zero matrix initalisation
		for (int i = 0 ; i < b ; i++)
			for (int j = 0 ; j < k ; j++)
				matrix[i][j] = 0;
	}



	// Get & Set Method
	public int getBaris() {
		return baris;
	}
	public void setBaris(int baris) {
		this.baris = baris;
	}
	public int getKolom() {
		return kolom;
	}
	public void setKolom(int kolom) {
		this.kolom = kolom;
	}



	// Other method
	public float det() {
		return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]);
	}

}
