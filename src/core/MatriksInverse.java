package core;

import java.io.*;

public class MatriksInverse {
    public static Matrix inverse(Matrix M) {
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
                kofaktor.matrix[i][j] = Minormat(M, i, j).cofactorDet() * ((i + j) % 2 == 0 ? 1 : -1);
            }
        }

        M1 = Transpose(kofaktor);
        for (i = 0; i < M.getRow(); i++) {
            for (j = 0; j < M.getColumn(); j++) {
                M1.matrix[i][j] *= determinan;
            }
        }

        return M1;
    }

    public static Matrix Transpose(Matrix M) {
        int i, j;
        Matrix M2 = new Matrix(M.getRow(), M.getColumn());
        for (i = 0; i < M.getRow(); i++) {
            for (j = 0; j < M.getColumn(); j++) {
                M2.matrix[j][i] = M.matrix[i][j];
            }
        }
        return M2;
    }

    public static Matrix Minormat(Matrix M, int k, int l) { // menghasilkan matriks minor dari entri i dan j
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

        return Minor;
    }
}
