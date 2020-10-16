package core;

import java.io.*;
import java.ult.*;

public class regresi {
    public static void regresi() {
        // asumsi matriks sudah augmented
        Matrix temp ;
        Matrix regres ;
        int n , i , j = 0, sum = 0, k = 0, l = 0;
        double kali = 0;
        temp = CLI.matrixInput() ;
        n = temp.getRow() ;
        // mengisi sisanya
        while ( l < n) {
            for (i = 0 ; i <= n  ; i++) {
                kali = 0 ;
                j = 0 ;
                while (j < n   ) {
                    if (j == 0 && i == 0 ) {
                        kali = n ;
                    }
                    else if ( j == 0 || i == 0 ) {
                        kali += temp[j][i] ;
                    }
                    else {

                        kali += temp[j][i]*temp[j][l] ;
                    }

                    j++;

                }
                regres.matrix[l][i] = kali ;



            }
            l++;
        }
        regres.completeGaussJordanElimination() ;

    }

}
