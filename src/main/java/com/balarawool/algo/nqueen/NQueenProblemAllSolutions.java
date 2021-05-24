package com.balarawool.algo.nqueen;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds all solutions to N-Queen Problem using brute-force algorithm.
 */
public class NQueenProblemAllSolutions {
    private int n;

    private List<List<Coords>> solutions = new ArrayList<>();

    public NQueenProblemAllSolutions(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        new NQueenProblemAllSolutions(8).solve();
    }

    public void solve() {
        Square[][] squares = new Square[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                squares[i][j] = new Square();
                squares[i][j].coords = new Coords(i, j);
            }
        }
        List<Coords> solution = new ArrayList<>();
        placeQueens(n, squares, solution);
        System.out.println(solutions);
    }

    private void placeQueens(int n, Square[][] squares, List<Coords> solution) {
        // For each row, i
        for (int i = 0; i < squares.length; i++) {
            // For each column, j
            for (int j = 0; j < squares[i].length; j++) {
                // If squares[i][j] is unoccupied and un-attacked
                if (!squares[i][j].hasQueen && !squares[i][j].isAttacked) {
                    // Place queen at squares[i][j] and mark attacked squares
                    Coords coords = squares[i][j].coords;
                    Square[][] squaresCopy = copy(squares);
                    placeQueen(squaresCopy, coords);
                    solution.add(coords);
                    // If (n == 1) then "Solution found"
                    if (n == 1) { System.out.println(solution); addToSolutions(copy(solution)); }
                    // Else place n-1 queens
                    else { placeQueens(n - 1, squaresCopy, solution); }
                    solution.remove(coords);
                }
            }
        }
    }

    private void addToSolutions(List<Coords> solution) {
        for (List<Coords> sol: solutions) {
            if (sol.containsAll(solution)) {
                return;
            }
        }
        solutions.add(solution);
    }

    private void placeQueen(Square[][] squares, Coords coords) {
        squares[coords.row][coords.col].hasQueen = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == coords.row || j == coords .col || (i - coords.row) == (j - coords.col) || (i - coords.row) == -(j - coords.col)) {
                    squares[i][j].isAttacked = true;
                }
            }
        }
    }
    private Square[][] copy(Square[][] squares) {
        Square[][] copy = new Square[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = squares[i][j].copy();
            }
        }
        return copy;
    }

    private static ArrayList<Coords> copy(List<Coords> list) {
        return new ArrayList<>(list);
    }

    private static class Square {
        private boolean hasQueen = false;
        private boolean isAttacked = false;
        private Coords coords;

        private Square copy() {
            Square copy = new Square();
            copy.hasQueen = this.hasQueen;
            copy.isAttacked = this.isAttacked;
            copy.coords = this.coords;
            return copy;
        }
    }

    private static class Coords {
        private int row;
        private int col;

        private Coords(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "{" + row + ", " + col + "}";
        }
    }

}
