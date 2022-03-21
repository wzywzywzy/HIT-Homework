package P1;

import java.io.*;
import java.util.*;

public class MagicSquare {

    static int[][] square;
    private static Scanner in;

    public static boolean isLegalMagicSquare(String fileName) {
        File f = new File(fileName);
        BufferedReader top = null;


        try {
            top = new BufferedReader(new FileReader(f));
            String[][] Square;
            String my = null,que = null;

            int ll = 0,rr = 0,gu;
            my = top.readLine();

            String[] tmp = my.split("\t|\n");
            rr = tmp.length;
            gu = rr;
            Square = new String[rr][rr];
            Square[ll++] = tmp;

            while ((my = top.readLine())!=null) {
                tmp = my.split("\t|\n");
                if (tmp.length != rr) {
                    System.out.print("The number of rows is not equal to the number of columns. ");
                    return false;
                }
                Square[ll++] = tmp;
            }
            if (ll != rr) {
                System.out.print("The number of rows is not equal to the number of columns. ");
                return false;
            }
            square = new int[rr][rr];
            String ss;
            for (int i=0; i<rr; i++) {
                for (int j=0; j<rr; j++) {
                    ss = "Square" + "[" + (i+1) + "]" + "[" + (j+1) + "]";
                    try {
                        square[i][j] = Integer.valueOf(Square[i][j]);
                    } catch (NumberFormatException e) {
                        ss = ss + "is not integer. ";
                        System.out.print(ss);
                        return false;
                    }
                    if (square[i][j] <= 0) {
                        ss = ss + " is negative integer. ";
                        System.out.print(ss);
                        return false;
                    }
                }
            }

            int sigmaRow,sigmaCol,sigmaDia1 = 0, sigmaDia2 = 0;


            for(int i=0; i<rr; i++) {
                sigmaRow = 0;
                sigmaCol = 0;
                for(int j=0,t; j<rr; j++) {
                    t = square[i][j];
                    sigmaRow += t;
                    sigmaCol += t;
                }

                if((sigmaRow != sigmaCol) || (sigmaCol != sigmaDia2))
                    return false;
            }

            for(int i=0,j = rr-1,t; i<rr; i++,j--) {
                t = square[i][j];
                sigmaDia1 += t;
                sigmaDia2 += t;

            }
            gu += sigmaDia1;
            if(sigmaDia1 != sigmaDia2)
                return false;

            top.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (top != null) {
                try {
                    top.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean generateMagicSquare(int n) {
        if (n <= 0) {
            System.out.println("Input a negative number!");
            return false;
        }
        if (n % 2 == 0) {
            System.out.println("Input an even number!");
            return false;
        }

        try {
            System.setOut(new PrintStream("src\\P1\\txt\\6.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int r = 0, c = n >> 1;
        int m[][] = new int[n][n];

        for (int i=1; i<=n*n; i++) {
            m[r][c] = i;
            if (i % n == 0)
                r++;
            else {
                r = (r==0)?n-1:r-1;
                c = (c==n-1)?0:c+1;
            }
        }
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++)
                System.out.print(m[i][j] + "\t");
            System.out.println();
        }
        return true;
    }

    public static void main(String[] args) {
        int dim;
        boolean ans;

        String ss, tt;
        tt = "src\\P1\\txt\\6.txt";
        PrintStream out = System.out;
        in = new Scanner(System.in);

        for (int i = 1; i <= 5; i++) {
            ss = "src\\P1\\txt\\" + String.valueOf(i) + ".txt";
            ans = isLegalMagicSquare(ss);
            System.out.println(String.valueOf(i) + ".txt " + ans);
        }
        try {
            dim = in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Something input is not an integer!");
            return;
        }


        ans = generateMagicSquare(dim) & isLegalMagicSquare(tt);
        System.setOut(out);
        if (!ans) {
            System.out.println(ans);
        } else
            System.out.println("6.txt " + ans);
    }
}

