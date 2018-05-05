package spbau.eliseeva.XO.Util;

import java.util.ArrayList;
import java.util.Random;

/** Realises the easy bot logic. Says where to go in the given game. */
public class EasyBotLogic {
    private static GameLogic gameLogic;

    /**
     * Tells where to go in the current game.
     * @param _gameLogic an object, that controls the game and its results.
     * @return number of cell from 0 to 8, where to go
     */
    public static int go(GameLogic _gameLogic) {
        gameLogic = _gameLogic;
        return goPCEasy();
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
        for (int i = 0; i < 9; i++) {
            checkFree(i, arrayList);
        }
        return arrayList.get(new Random().nextInt(arrayList.size()));
    }

}
