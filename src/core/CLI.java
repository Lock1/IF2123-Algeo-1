package core;

import java.util.Scanner;

public class CLI {

	public static void main(String args[]) {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		// Main menu loop
		while (true) {
			System.out.println("MENU");
			System.out.println("1. Sistem Persamaan Linier");
			System.out.println("2. Determinan");
			System.out.println("3. Matriks balikan");
			System.out.println("4. Interpolasi Polinom");
			System.out.println("5. Regresi linier berganda");
			System.out.println("6. Keluar");
			break;
		}
		
		// testing purpose
		Matrix kek = new Matrix(2,2);
		for (int i = 0 ; i < 2 ; i++)
			for (int j = 0 ; j < 2 ; j++)
				kek.matrix[i][j] = input.nextInt();
		System.out.println(Double.toString(kek.cofactorDet()));
	}

}
