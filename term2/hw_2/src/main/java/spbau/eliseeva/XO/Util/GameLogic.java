package spbau.eliseeva.XO.Util;

public class GameLogic {
    private char[][] board;

    public GameLogic() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }
    public void move(int x, int y, char sign) {
        board[x][y] = sign;
    }

    public boolean isDraw() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return !isWin('X') && !isWin('O');
    }

    public boolean isWin(char player) {
        return board[0][0] == player && board[0][1] == player && board[0][2] == player
                || board[1][0] == player && board[1][1] == player && board[1][2] == player
                || board[2][0] == player && board[2][1] == player && board[2][2] == player
                || board[0][0] == player && board[1][0] == player && board[2][0] == player
                || board[0][1] == player && board[1][1] == player && board[2][1] == player
                || board[0][2] == player && board[1][2] == player && board[2][2] == player
                || board[0][0] == player && board[1][1] == player && board[2][2] == player
                || board[0][2] == player && board[1][1] == player && board[2][0] == player;
    }

    public char getCell(int x, int y) {
        return board[x][y];
    }
}
