package spbau.eliseeva.XO.Util;

import java.util.ArrayList;
import java.util.Random;

/**
 * Realises the bot logic. Says where to go in the given game.
 */
public class BotLogic {
    /** An object, that controls the game and its results.*/
    private static GameLogic gameLogic;

    /**
     * Tells where to go in the current game.
     * @param _gameLogic an object, that controls the game and its results.
     * @param symbol who goes: X ot O
     * @param mode easy or hard mode is playing
     * @return number of cell from 0 to 8, where to go
     */
    public static int go(GameLogic _gameLogic, char symbol, int mode) {
        gameLogic = _gameLogic;
        if (mode == 1) {
            return goPCEasy();
        } else {
            return goPCHard(symbol);
        }
    }

    /**
     * Checks if the cell is free and adds the number to the arrayList if yes.
     * @param cellNumber number of a sell to check
     * @param arrayList arrayList to add free cells
     */
    private static void checkFree(int cellNumber, ArrayList<Integer> arrayList) {
        if (gameLogic.getCell(cellNumber / 3, cellNumber % 3) == ' ') {
            arrayList.add(cellNumber);
        }
    }

    /**
     * Chooses a random cell from free ones.
     * @return where to go, if easy mode
     */
    private static int goPCEasy() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        checkFree(0, arrayList);
        checkFree(1, arrayList);
        checkFree(2, arrayList);
        checkFree(3, arrayList);
        checkFree(4, arrayList);
        checkFree(5, arrayList);
        checkFree(6, arrayList);
        checkFree(7, arrayList);
        checkFree(8, arrayList);
        return arrayList.get(new Random().nextInt(arrayList.size()));
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
        return goPCEasy();
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
    private static int tryWin(int cell1, int cell2, int cellPut, char type) {
        if (gameLogic.getCell(cell1 / 3,cell1 % 3) ==
                gameLogic.getCell(cell2 / 3, cell2 % 3) &&
                gameLogic.getCell(cell1 / 3, cell1 % 3) == type &&
                gameLogic.getCell(cellPut / 3, cellPut % 3) == ' ') {
            return cellPut;
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
        if ((ans = tryWin(0, 2,1, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(2, 1, 0, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(3, 4, 5, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(3, 5, 4, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(5, 4, 3, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(6, 7, 8, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(6, 8, 7, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(8, 7, 6, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(0, 3, 6, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(0, 6, 3, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(6, 3, 0, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(1, 4, 7, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(1, 7, 4, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(7, 4, 1, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(2, 5, 8, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(2, 8, 5, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(8, 5, 2, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(0, 4, 8, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(0, 8, 4, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(4, 8, 0, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(2, 4, 6, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(4, 6, 2, type)) != -1) {
            return ans;
        }
        if ((ans = tryWin(2, 6, 4, type)) != -1) {
            return ans;
        }
        return -1;
    }
}
