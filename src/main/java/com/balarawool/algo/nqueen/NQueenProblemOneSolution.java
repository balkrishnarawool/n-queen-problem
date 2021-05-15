package com.balarawool.algo.nqueen;

import java.util.ArrayList;
import java.util.Stack;

public class NQueenProblemOneSolution {
    private int n;

    public NQueenProblemOneSolution(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        new NQueenProblemOneSolution(8).solve();
    }

    public void solve() {
        Square[][] squares = new Square[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                squares[i][j] = new Square();
                squares[i][j].coords = new Coords(i, j);
            }
        }
        Stack<Coords> solution = new Stack<>();
        ArrayList<Coords> excludes = new ArrayList<>();
        placeQueens(n, squares, excludes, solution);
    }

    private boolean placeQueens(int n, Square[][] squares, ArrayList<Coords> excludes, Stack<Coords> solution) {
        Square[][] squaresCopy = copy(squares);
        Coords coords = getRandomSquareCoords(squares, excludes);
        if (coords == null) return false;
        placeQueen(squaresCopy, coords);
        solution.push(coords);
        n = n - 1;
        if (n == 0) { System.out.println(solution); return true; }
        while (!placeQueens(n, squaresCopy, copy(excludes), solution)) {
            excludes.add(coords);
            solution.pop();
            n = n + 1;
            squaresCopy = copy(squares);
            coords = getRandomSquareCoords(squaresCopy, excludes);
            if (coords == null) return false;
            placeQueen(squaresCopy, coords);
            solution.push(coords);
            n = n - 1;
            if (n == 0) { System.out.println(solution); return true; }
        }
        return true;
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

    private Coords getRandomSquareCoords(Square[][] squares, ArrayList<Coords> excludes) {
        ArrayList<Coords> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!squares[i][j].hasQueen && !squares[i][j].isAttacked && !excludes.contains(squares[i][j].coords)) {
                    list.add(squares[i][j].coords);
                }
            }
        }
        return (list.size() > 0) ? list.get((int)(Math.random() * list.size())) : null;
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

    private static ArrayList<Coords> copy(ArrayList<Coords> list) {
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
