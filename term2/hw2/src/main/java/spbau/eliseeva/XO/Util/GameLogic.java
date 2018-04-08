package spbau.eliseeva.XO.Util;

/** The class realises game logic for XO game.*/
public class GameLogic {
    /** Board with the game.*/
    private char[][] board;

    /** Size of the board, usually 3.*/
    private int size;

    /**
     * Creates en empty board.
     * @param n size of the needed board
     */
    public GameLogic(int n) {
        size = n;
        board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Puts a sign into the cell.
     * @param x number of a row
     * @param y number of a column
     * @param sign X or O to put
     */
    public void move(int x, int y, char sign) {
        board[x][y] = sign;

    }

    /**
     * Checks if it is draw or not
     * @return true if draw
     */
    public boolean isDraw() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return !isWin('X') && !isWin('O');
    }

    /**
     * Checks if a player won.
     * @param player sign wanted to win
     * @return true in the sign won
     */
    public boolean isWin(char player) {
        boolean answer = false;
        boolean diag1 = true;
        boolean diag2 = true;
        for (int i = 0; i < size; i++) {
            boolean row = true;
            boolean column = true;
            for (int j = 0; j < size; j++) {
                row &= board[i][j] == player;
                column &= board[j][i] == player;
            }
            answer |= row || column;
            diag1 &= board[i][i] == player;
            diag2 &= board[size - i - 1][i] == player;
        }
        answer |= diag1 || diag2;
        return answer;
    }

    /**
     * Returns the needed cell.
     * @param x row number
     * @param y column number
     * @return X, O or space from the cell
     */
    public char getCell(int x, int y) {
        return board[x][y];
    }
}
