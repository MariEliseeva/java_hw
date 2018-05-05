package spbau.eliseeva.XO.Util;

/**
 * Realises the hard bot logic. Says where to go in the given game.
 */
public class HardBotLogic {
    /** An object, that controls the game and its results.*/
    private static GameLogic gameLogic;

    /**
     * Tells where to go in the current game.
     * @param _gameLogic an object, that controls the game and its results.
     * @param symbol who goes: X ot O
     * @return number of cell from 0 to 8, where to go
     */
    public static int go(GameLogic _gameLogic, char symbol) {
        gameLogic = _gameLogic;
        return goPCHard(symbol);
    }

    /**
     * Chooses a cell which may help to win or not to lose.
     * If we can not win (or lose) on the next step, just goes on a
     * random cell, like in easy mode.
     * @param symbol what the bot plays for: X or O
     * @return where to go, if hard mode
     */
    private static int goPCHard(char symbol) {
        char otherType;
        if (symbol == 'X') {
            otherType = 'O';
        } else {
            otherType = 'X';
        }
        int ans;
        if ((ans = canWin(symbol)) != -1) {
            return ans;
        }
        if ((ans = canWin(otherType)) != -1) {
            return ans;
        }
        return EasyBotLogic.go(gameLogic);
    }

    /**
     * Checks if cell1 and cell2 are of needed type, and the cellPut is free
     * to write X or O in it.
     * @param cell1 first cell in a row
     * @param cell2 second cell in a row
     * @param cellPut third cell in a row, which we want to fill
     * @param type which type should two first cells be
     * @return number of the third cell if we can write in it, -1 if no
     */
    private static int tryWin2(int cell1, int cell2, int cellPut, char type) {
        if (gameLogic.getCell(cell1 / 3,cell1 % 3) ==
                gameLogic.getCell(cell2 / 3, cell2 % 3) &&
                gameLogic.getCell(cell1 / 3, cell1 % 3) == type &&
                gameLogic.getCell(cellPut / 3, cellPut % 3) == ' ') {
            return cellPut;
        }
        return -1;
    }

    /**
     * Takes three cells and trying to make a row from them.
     * @param cell1 first cell
     * @param cell2 second cell
     * @param cell3 third cell
     * @param type sign we want
     * @return where to put a sign or -1 if not possible
     */
    private static int tryWin(int cell1, int cell2, int cell3, char type) {
        int ans;
        if ((ans = tryWin2(cell1, cell2, cell3, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin2(cell1, cell3, cell2, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin2(cell3, cell2, cell1, type)) != -1) {
            return ans;
        }
        return -1;
    }

    /**
     * Checks if th given type can win
     * @param type X or O
     * @return where to go to win or -1 if can not
     */
    private static int canWin(char type) {
        int ans;
        if ((ans = tryWin(0, 1, 2, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(3, 4, 5, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(6, 7, 8, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(0, 3, 6, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(1, 4, 7, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(2, 5, 8, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(0, 4, 8, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(2, 4, 6, type)) != -1) {
            return ans;
        }
        return -1;
    }
}
