/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3x3amobajatek;

import java.util.Scanner;

/**
 *
 * @author tamastokos
 */
public class Main {

    public static void printIntro() {
        System.out.println("3x3-as amőba játék");
        System.out.println("2 játékossal");
        System.out.println("Az nyer, akinek sikerül 3 jelet tennie egy sorba, vagy egy oszlopba, vagy átlósan.");
    }

    public static void fillTableWithString(String[][] table, String value) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = value;
            }
        }
    }

    public static void drawTableWithFixedIndexes(String[][] table, String[] rowLabels, String[] columnLabels) {
        String leadInLeadOut = "+---+---+---+";

        System.out.print(" ");
        for (int i = 0; i < columnLabels.length; i++) {
            System.out.print("   " + columnLabels[i]);
        }
        System.out.println("");

        for (int i = 0; i < table.length; i++) {
            System.out.println("  " + leadInLeadOut);
            System.out.print(rowLabels[i] + " |");
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(" " + table[i][j] + " |");
            }
            System.out.println("");
        }
        System.out.println("  " + leadInLeadOut);
    }

    public static boolean move(String[][] table, String signOfPlayer, String emptyField, String inputString, String[] rowLabels, String[] columnLabels) {
        inputString = inputString.trim();
        inputString = inputString.toUpperCase();

        boolean rowIsOk = false;
        int rowIndex = 0, columnIndex = 0;
        boolean columnIsOk = false;

        for (int i = 0; i < rowLabels.length; i++) {
            if (rowLabels[i].equals(inputString.substring(0, 1))) {
                rowIsOk = true;
                rowIndex = i;
            }
            if (columnLabels[i].equals(inputString.substring(1, 2))) {
                columnIsOk = true;
                columnIndex = i;
            }
        }

        if (columnIsOk && emptyField.equals(table[rowIndex][columnIndex]) && rowIsOk) {
            table[rowIndex][columnIndex] = signOfPlayer;
            return true;

        }

        return false;
    }

    public static boolean isAWinner(String[][] table, String Player) {
        int rowHit = 0;
        int columnHit = 0;
        int diagonalHit = 0;

//        soronkénti vizsgálat
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j].equals(Player)) {
                    rowHit++;
                }
                if (rowHit == table[i].length) {
                    return true;
                }
            }
            rowHit = 0;
        }
//        oszloponkénti vizsgálat
        for (int j = 0; j < table[0].length; j++) {
            for (int k = 0; k < table.length; k++) {
                if (table[k][j].equals(Player)) {
                    columnHit++;
                }
                if (columnHit == table.length) {
                    return true;
                }
//                columnHit = 0;
            }
            columnHit = 0;
        }

//         a főátló vizsgálata
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (i == j && table[i][j].equals(Player)) {
                    diagonalHit++;
                }
            }
        }
        if (diagonalHit == table.length) {
            return true;
        }

//            a mellékátló vizsgálata
        diagonalHit = 0;

        for (int j = 0; j < table.length; j++) {
            for (int k = 0; k < table[j].length; k++) {
                if (j + k + 1 == table.length && table[j][k].equals(Player)) {
                    diagonalHit++;
                }
            }
        }
        if (diagonalHit == table.length) {
            return true;
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[][] table = new String[3][3];
        String[] rowIndex = {"A", "B", "C"};
        String[] columnIndex = {"1", "2", "3"};
        String[] playerMarks = {"x", "o"};
        String emptyField = " ";
        String moveStr;

        printIntro();
        fillTableWithString(table, emptyField);
        drawTableWithFixedIndexes(table, rowIndex, columnIndex);

        boolean rightMove;
        int playerIndex = 0;
        int validStep = 0;

        do {
            do {
                System.out.print("Kérem a(z) " + (playerIndex + 1) + ". játékos (" + playerMarks[playerIndex] + ") lépését!: ");
                moveStr = sc.nextLine();
                rightMove = move(table, playerMarks[playerIndex], emptyField, moveStr, rowIndex, columnIndex);

                if (!rightMove) {
                    System.out.println("Hibás lépés!");
                } else {
                    drawTableWithFixedIndexes(table, rowIndex, columnIndex);
                }
            } while (!rightMove);

            validStep++;

            if (isAWinner(table, playerMarks[playerIndex])) {
                System.out.println("Az " + (playerIndex + 1) + "(" + playerMarks[playerIndex] + ") játékos győzött!");
                return;
            }

            if (validStep == 9) {
                System.out.println("Vége a játéknak, az eredmény döntetlen!");
                return;
            }
            if (playerIndex == 1) {
                playerIndex = 0;
            }else playerIndex = 1;
        } while (true);
    }

}
