
/*7. На шахматной доске расставить 8 ферзей так, чтобы они не били друг друга.*/

import java.util.Arrays;

public class Quen_HW3 {
    public static void main(String[] args) {
        int [][] board = new int [8][8];
        int count = 1;
        printArray(board);
        for (int i = 0; i < board.length; i++) {
            solve(board, i, 0, 0);
            System.out.println();
            printArray(board);
            clearBoard(board);
        }
    }

    public static boolean checkPosition(int[][] board, int row, int column) {
        // проверять ряд
        for (int i = 0; i < column; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }
        // проверка налево вверх
        for (int i = row - 1, j = column - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        // проверка налево вниз
        for (int i = row + 1, j = column - 1; i < board.length && j >= 0; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }
        return true;
    }
    public static boolean solve(int[][] board, int startRow, int column, int startCol) {
        if (column >= board.length) {
            return true;
        }
        if (column == startCol) {
            for (int i = startRow; i < board.length; i++) {
                if (checkPosition(board, i, column)) {
                    // стафим ферзя
                    board[i][column] = 1;
                    if (solve(board,startRow,column + 1, startCol)) {
                        return true;
                    }
                    board[startRow][column] = 0;
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            if (checkPosition(board, i, column)) {
                // стафим ферзя
                board[i][column] = 1;
                if (solve(board, i,column + 1, startCol)) {
                    return true;
                }
                board[i][column] = 0;
            }
        }
        return false;
    }

    public static void printArray (int [][] array) {
        for (int [] row : array) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void clearBoard (int [][] array) {
        for (int i = 0; i< array.length; i++) {
            for (int j = 0; j< array.length; j++){
                array[i][j] = 0;
            }
        }
    }
}
    
