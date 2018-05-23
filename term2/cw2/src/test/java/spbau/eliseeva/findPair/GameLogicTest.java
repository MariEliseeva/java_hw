package spbau.eliseeva.findPair;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameLogicTest {

    @Test
    public void test() {
        GameLogic gameLogic = new GameLogic(4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
               for (int k = 0; k < 4; k++) {
                   for (int h = 0; h < 4; h++) {
                       if ((i != k || j != h) && gameLogic.getCell(i, j) == gameLogic.getCell(k, h)) {
                           if (!gameLogic.isOpened(i, j)) {
                               assertTrue(gameLogic.push(i, j));
                               assertFalse(gameLogic.push(k, h));
                               assertTrue(gameLogic.isGood(k, h));
                           } else {
                               if (gameLogic.isOpened(i, j)) {
                                   assertFalse(gameLogic.push(i, j));
                               } else {
                                   assertFalse(gameLogic.push(h, k));
                               }
                           }
                       }
                   }
               }
            }
        }
        assertTrue(gameLogic.isWin());
    }
}