package spbau.eliseeva.findPair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Logic of the game with paired numbers.
 * The player push two buttons. If they are the same and were not previously pushed
 * they became "open". When all buttons are open -- game ends.
 */
class GameLogic {
    /**
     * Creates a board and fills if by numbers. Each number mush have a pair, so we generate
     * numbers only by two and then shuffle them.
     * @param size size of the board, must be even and positive
     */
    GameLogic(int size) {
        if (size < 0 || size % 2 == 1) {
            throw new IllegalArgumentException();
        }
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size * size / 2; i++) {
            numbers.add(i);
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        board = new int[size][];
        isOpened = new boolean[size][] ;
        for (int i = 0; i < size; i++) {
            board[i] = new int[size];
            isOpened[i] = new boolean[size];
            for (int j = 0; j < size; j++) {
                board[i][j] = numbers.get(i * size + j);
                isOpened[i][j] = false;
            }
        }
        left = size * size;
        isPushed = false;
    }

    private int[][] board;
    private boolean[][] isOpened;
    private int pushedX;
    private int pushedY;
    private boolean isPushed;
    private int left;

    /**
     * Return the needed cell value.
     * @param x row of needed cell
     * @param y column of needed cell
     * @return value inside the cell
     */
    int getCell(int x, int y) {
        return board[x][y];
    }

    /**
     * Push the cell if possible. Impossible if the other cell is already pushed of this one is open.
     * @param x row of needed cell
     * @param y column of needed cell
     * @return true if the cell was pushed, false if impossible
     */
    boolean push(int x, int y) {
        if (isPushed || isOpened[x][y]) {
            isPushed = false;
            return false;
        }
        isPushed = true;
        pushedX = x;
        pushedY = y;
        return true;
    }

    /**
     * Check if the cell can be the one to make good turn.
     * @param x row of needed cell
     * @param y column of needed cell
     * @return true if another cell is pushed and the values with current matches
     */
    boolean isGood(int x, int y) {
        if (isPushed || isOpened[x][y]) {
            return false;
        }
        if (board[x][y] == board[pushedX][pushedY] && (x != pushedX || y != pushedY)) {
            isOpened[x][y] = true;
            isOpened[pushedX][pushedY] = true;
            left -= 2;
            return true;
        }
        return false;
    }

    /**
     * Checks if the game is ended or not.
     * @return true if all cells are open
     */
    boolean isWin() {
        return left == 0;
    }

    /**
     * Checks if the cell is opened or not.
     * @param x row of needed cell
     * @param y column of needed cell
     * @return true if the cell is opened
     */
    boolean isOpened(int x, int y) {
        return isOpened[x][y];
    }
}
