import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/** Class GaussianElimination **/
public class Main
{


// The Point of the game
    public static int  Point(double[][] matr) {
        int n = matr[0].length;
        // MIN in row (A)
        System.out.print("\nMin(A) = (");
        int[] min = new int[n];
        for (int i = 0; i < n; i++) {
            min[i] = (int)matr[i][0];
            for (int j = 1; j < n; j++) {
                if (matr[i][j] < min[i]) {
                    min[i] = (int)matr[i][j];
                }
            }
            if (i==n-1){
                System.out.print(min[i] + ") \n");
            }
            else
            // print the smallest element of the row
            System.out.print(min[i] + ", ");

        }
        // MAX in col (B)
        System.out.print("Max(B) = (");
        int[] max = new int[n];
        for (int i = 0; i < n; i++) {
            max[i]= (int)matr[0][i];
            for (int j = 1; j < n; j++) {

                if (matr[j][i] > max[i]) {
                    max[i] = (int)matr[j][i];
                }}
                if (i==n-1){
                    System.out.print(max[i] + ") \n");
                }
                else
                // print the biggest element of the row
                System.out.print(max[i] + ", ");
            }
int maxmin = min[0];
        for (int i = 1; i < n; i++) {
            if (min[i] > maxmin) {
                maxmin = min[i];
            }
        }
        System.out.print("MAX(Min(A)) = "+maxmin);
int minmax = max[0];
        for (int i = 1; i < n; i++) {
            if (max[i] < minmax) {
                minmax = max[i];
            }
        }
        System.out.print("\nMIN(Max(B)) = "+minmax);

        if (minmax == maxmin){
            System.out.print("\nСідлова точка = "+minmax);
            return minmax;
        }
        else
        {
            System.out.print("\nВ заданій матриці гри немає сідлової точки!\n");
            return 0;
        }
    }



    // Dominante  rows and columns
    public static double[][] DomMatr(double[][] matr) {
        int n = matr[0].length;
        int[] domA = new int[n];
        for (int i=0; i<n; i++) {
            for (int k = 0; k < n; k++) {
                for (int j = 0; j < n; j++) {
            if (matr[i][j]>matr[k][j])
                domA[j]=i;
            }
                }
            }

        int[] domB = new int[n];
        for (int i=0; i<n; i++) {
            for (int k = 0; k < n; k++) {
                for (int j = 0; j < n; j++) {
                    if (matr[j][i]<matr[j][k])
                        domB[j]=i;
                }
            }
        }

            // Index of dominant Col
int delCol =0;
        for (int i=0; i<n-1; i++) {
            if (domA[i] == domA[i+1])
                delCol = domA[i];
            else {
                delCol =0;
                break;}
        }


        // Index of dominant Row
        int delRow =0;
        for (int i=0; i<n-1; i++) {
            if (domB[i] == domB[i+1])
                delRow = domB[i];
            else {
                delRow =0;
                break;}
        }
        delCol = delRow;
        System.out.print("\n_______________________________________________\nЗнаходження рішення гри в змішаних стратегіях \nЗнаходження домінуючих рядків...");
        System.out.print("\nВидалений стовпець: "+(int)(delCol+1) +"\nВидалений рядок: "+ (int)(delRow+1) +"\n");
        // New matrix without dominant row and col
        int m = n-1;
        int p=m;
double[][] result = new double [m][p];
m=0; p=0;

        for( int i = 0; i <matr.length; ++i)
        {
            if ( i == delCol)
                continue;
            int q = 0;
            for( int j = 0; j <matr[0].length; ++j)
            {
                if (j == delRow)
                    continue;
                result[p][q] = matr[i][j];
                ++q;
            }
            ++p;
        }

        /*
        for (int i=0; i<n; i++) {
            if (i != delCol){
                for (int j = 0; j < n; j++) {
                    if (j != delRow)
                    {
                        result[m][p] = matr[i][j];
                        p++;
                    }
                }
                m++;
            }
        }*/
        System.out.print("\nМатриця з видаленими домінуючими рядками та стовпцями: \n");
        for (int i=0; i<n-1; i++) {
                for (int j = 0; j < n-1; j++) {
                    System.out.print((int)result[i][j]+" ");
                }
            System.out.println();
        }
        System.out.println();
return result;
        }



    public void solve(double[][] A, double[] B)
    {
        int N = B.length;
        for (int k = 0; k < N; k++)
        {
            /** find pivot row **/
            int max = k;
            for (int i = k + 1; i < N; i++)
                if (Math.abs(A[i][k]) > Math.abs(A[max][k]))
                    max = i;

            /** swap row in A matrix **/
            double[] temp = A[k];
            A[k] = A[max];
            A[max] = temp;

            /** swap corresponding values in constants matrix **/
            double t = B[k];
            B[k] = B[max];
            B[max] = t;

            /** pivot within A and B **/
            for (int i = k + 1; i < N; i++)
            {
                double factor = A[i][k] / A[k][k];
                B[i] -= factor * B[k];
                for (int j = k; j < N; j++)
                    A[i][j] -= factor * A[k][j];
            }
        }

        /** Print row echelon form **/
        printRowEchelonForm(A, B);

        /** back substitution **/
        double[] solution = new double[N];
        for (int i = N - 1; i >= 0; i--)
        {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++)
                sum += A[i][j] * solution[j];
            solution[i] = (B[i] - sum) / A[i][i];
        }
        /** Print solution **/
        printSolution(solution);
    }
    /** function to print in row    echleon form **/
    public void printRowEchelonForm(double[][] A, double[] B)
    {
        int N = B.length;
        System.out.println("\nРозв'язана матриця: ");
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                System.out.printf("%.3f ", A[i][j]);
            System.out.printf("| %.3f\n", B[i]);
        }
        System.out.println();
    }
    /** function to print solution **/
    public void printSolution(double[] sol)
    {
        DecimalFormat df = new DecimalFormat("###.##");
        int N = sol.length;
        double V=0;
        System.out.print("\nЙмовірності використання змішаних стратегій): \nP(");
        for (int i = 0; i < N; i++){
            V=sol[i]+V;
        }
        V = 1/V;
        for (int i = 0; i < N; i++){
            if (sol[i]<0) {
                sol[i] = 0;
            }
            //sol[i] = sol[i]*10;
            System.out.printf("%.3f ", sol[i]);}
        System.out.print(")\n");
        System.out.println("V = "+df.format(V));

        double[] rez = new double[N];
        for (int i = 0; i < N; i++){
            rez[i] = V*sol[i];
            }
        System.out.println("\nРозв'язок");
        for (int i = 0; i < N; i++){
            System.out.print(df.format(rez[i])+" ");
        }

    }



    /** function to transpose matrix **/
    public static double[][] TransposeMatr(double[][] matrix, int n) {
        double [][] transpose = new double[n][n];
        for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
        transpose[j][i]=matrix[i][j];
    }
}
    return transpose;
    }

    public static void system(double[][] matr, double[] b, int n, char p) {
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++) {
                if (j==n-1){
                    System.out.print((int)matr[i][j]+"*"+p+(i+1) + " = " + b[i]);
                }
                else
                System.out.print((int)matr[i][j]+"*"+p+(i+1)+" + ");
            }
            System.out.println();
        }
    }


    /** Main function **/
    public static void main (String[] args) throws Exception
    {

        Scanner sc = new Scanner(new BufferedReader(new FileReader("C:\\Users\\User\\Desktop\\4 курс\\Теорія прийняття рішень\\lab5.txt")));
        //  File f = new File("C:\\Users\\User\\Desktop\\4 курс\\Теорія прийняття рішень\\lab4.txt");     //Creation of File Descriptor for input file
        StringBuffer sb = new StringBuffer();
        String input = new String();

        //DecimalFormat df = new DecimalFormat("###.#"); //Округлення числа

        while (sc.hasNextLine()) {
            input = sc.nextLine();
            sb.append(input);
        }
        String source = sb.toString();
        String str[] = source.split(" ");

        int N;
        N  = Integer.parseInt(str[0]);
        double[][] A = new double[N][N];

        System.out.println("Матриця гри:\t");
        int f = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                A[i][j] = Double.parseDouble(str[f]);
                System.out.print((int)A[i][j] + "\t");
                f++;
            }
            System.out.println();
        }
        //---------------------------------------------
        Scanner scan = new Scanner(System.in);
        /*System.out.println("Gaussian Elimination Algorithm Test\n");
        Main ge = new Main();

        System.out.println("\nEnter number of variables");

        System.out.println("\nEnter "+ N +" equations coefficients ");
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                A[i][j] = scan.nextDouble();
*/

        int k = Point(A);

        double[][] M = new double[N-1][N-1];
        if (k==0) {
            M = DomMatr(A);
        }
        double[][] trM = TransposeMatr(M, N-1);
       /* System.out.println("Matrix M");
        for (int i = 0; i < N-1; i++){
            for (int j = 0; j < N-1; j++){
            System.out.print(M[i][j]+" ");    }
            System.out.println();
        }*/

        double[] B = new double[N-1];
        double[] B2 = new double[N-1];
        //double b = scan.nextDouble();
        for (int i = 0; i < N-1; i++){
            B[i] = 1;
            B2[i] = 1;  }
        Main ge = new Main();
        System.out.println("\nМетод Гауса для розв'язку СЛАР:");
        //System.out.println("V = 1");
        System.out.println("_____________________________\nСЛАР для гравця A");
        system(trM, B2, N-1, 'p');
        ge.solve(trM,B2);
        System.out.println();
        System.out.println("\nСЛАР для гравця B");
        system(M, B, N-1, 'q');
        ge.solve(M,B);


    }
}