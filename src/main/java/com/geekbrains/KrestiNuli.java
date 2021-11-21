package com.geekbrains;

import java.util.Random;
import java.util.Scanner;

public class KrestiNuli {
    private final static int SIZE = 5;
    private final static int DOTS_TO_WIN = 4;
    private final static char DOT_EMPTY = '.';
    private final static char DOT_X = 'X';
    private final static char DOT_0 = '0';
    private static char[][] MAP;
    private final static Scanner SCANNER = new Scanner(System.in);
    private final static Random RANDOM = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();
        while (true) {
            humanTurn();
            printMap();
            if (checkWin(DOT_X)) {
                System.out.println("Победил человек");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (checkWin(DOT_0)) {
                System.out.println("Победил компьютер");
                break;
            }
            if (isMapFull()){
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра окончена");

    }

    private static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (MAP[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void aiTurn() {
        int x;
        int y;
        do {
            x = RANDOM.nextInt(SIZE);
            y = RANDOM.nextInt(SIZE);
        } while (availableCell(x, y));
        System.out.println("Компьютер сходил в точку" + (x + 1) + " " + (y + 1));
        MAP[y][x] = DOT_0;
    }

    private static boolean checkWin(char symbol) {
        return horizWin(symbol) || vertWin(symbol) || diagWin(symbol);
    }

    private static boolean diagWin(char symbol) {
        // in this method we are trying
        // to catch 4 units in a row in each array,
        // which will mean four identical symbols on the diagonal
        int[] countDiag_1 = new int[SIZE];
        int[] countDiag_2 = new int[SIZE];
        int[] countDiag_3 = new int[SIZE];
        int[] countDiag_4 = new int[SIZE];
        int[] countDiag_5 = new int[SIZE];
        int[] countDiag_6 = new int[SIZE];
        int[] counts = new int[8];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (MAP[i][j] == symbol && i == j) {
                    countDiag_1[i] += 1;
                }
                if (MAP[i][j] == symbol && i == j - 1) {
                    countDiag_2[i] += 1;
                }
                if (MAP[i][j] == symbol && i == j + 1) {
                    countDiag_3[i] += 1;
                }
                if (MAP[i][j] == symbol && i + j == SIZE - 1) {
                    countDiag_4[i] += 1;
                }
                if (MAP[i][j] == symbol && i + j == SIZE - 2) {
                    countDiag_5[i] += 1;
                }
                if (MAP[i][j] == symbol && i + j == SIZE) {
                    countDiag_6[i] += 1;
                }
            }
        }
        for (int i = 0; i < SIZE - 1; i++) {
            counts[0] += countDiag_1[i];
            counts[1] += countDiag_2[i];
            counts[2] += countDiag_3[i];
            counts[3] += countDiag_4[i];
            counts[4] += countDiag_5[i];
            counts[5] += countDiag_6[i];
        }
        for (int i = 0; i < SIZE - 1; i++) {
            counts[6] += countDiag_1[i + 1];
            counts[7] += countDiag_4[i + 1];
        }
        for (int i = 0; i < 8; i++) {
            if (counts[i] == DOTS_TO_WIN) {
                return true;
            }
        }
        return false;
    }

    private static boolean vertWin(char symbol) {
        // In this method we are trying to find 4
        // identical symbol on verticals
        int[] countVert = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (MAP[i][j] == symbol) {
                    countVert[j] += 1;
                } else if (MAP[i][j] != symbol && countVert[j] < DOTS_TO_WIN) {
                    countVert[j] = 0;
                }
            }
        }
        for (int i = 0; i < SIZE; i++) {
            if (countVert[i] >= DOTS_TO_WIN) {
                return true;
            }
        }
        return false;
    }

    private static boolean horizWin(char symbol) {
        int[] countHoriz = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (MAP[i][j] == symbol) {
                    countHoriz[i] += 1;
                } else if (MAP[i][j] != symbol && countHoriz[i] < DOTS_TO_WIN) {
                    countHoriz[i] = 0;
                }
            }
        }
        for (int i = 0; i < SIZE; i++) {
            if (countHoriz[i] >= DOTS_TO_WIN) {
                return true;
            }
        }
        return false;
    }

    private static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты хода в формате (X,Y) (X - горизонтальная координата, Y - вертикальная)");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (availableCell(x, y));
        MAP[y][x] = DOT_X;
    }

    private static boolean availableCell(int x, int y) {
        return x < 0 || x > SIZE - 1 || y < 0 || y > SIZE - 1 || MAP[y][x] != DOT_EMPTY;
    }

    public static void initMap() {
        MAP = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                MAP[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(MAP[i][j] + " ");
            }
            System.out.println();
        }
    }
}
