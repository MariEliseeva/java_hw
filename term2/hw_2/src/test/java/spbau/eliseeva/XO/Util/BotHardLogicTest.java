package spbau.eliseeva.XO.Util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests if the bot used inside the
 * application works correct.
 */
public class BotHardLogicTest {
    @Test
    public void testGoHorizontal() {
        GameLogic gameLogic1 = new GameLogic();
        gameLogic1.move(0, 2, 'X');
        gameLogic1.move(0, 1, 'X');
        assertEquals(0, BotLogic.go(gameLogic1,'O', 2));
        GameLogic gameLogic2 = new GameLogic();
        gameLogic2.move(2, 0, 'O');
        gameLogic2.move(2, 1, 'O');
        assertEquals(8, BotLogic.go(gameLogic2, 'O', 2));
        GameLogic gameLogic3 = new GameLogic();
        gameLogic3.move(1, 0, 'X');
        gameLogic3.move(1, 2, 'X');
        assertEquals(4, BotLogic.go(gameLogic3, 'O', 2));
    }
    @Test
    public void testGoVertical() {
        GameLogic gameLogic1 = new GameLogic();
        gameLogic1.move(2, 0, 'X');
        gameLogic1.move(1, 0, 'X');
        assertEquals(0, BotLogic.go(gameLogic1,'O', 2));
        GameLogic gameLogic2 = new GameLogic();
        gameLogic2.move(0, 2, 'O');
        gameLogic2.move(1, 2, 'O');
        assertEquals(8, BotLogic.go(gameLogic2, 'O', 2));
        GameLogic gameLogic3 = new GameLogic();
        gameLogic3.move(0, 1, 'X');
        gameLogic3.move(2, 1, 'X');
        assertEquals(4, BotLogic.go(gameLogic3, 'O', 2));
    }
    @Test
    public void testGoDiagonal() {
        GameLogic gameLogic1 = new GameLogic();
        gameLogic1.move(0, 0, 'X');
        gameLogic1.move(2, 2, 'X');
        assertEquals(4, BotLogic.go(gameLogic1,'O', 2));
        GameLogic gameLogic2 = new GameLogic();
        gameLogic2.move(0, 2, 'O');
        gameLogic2.move(1, 1, 'O');
        assertEquals(6, BotLogic.go(gameLogic2, 'O', 2));
    }
    @Test
    public void testBetterWinThanNoLose() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.move(0, 0, 'X');
        gameLogic.move(0, 1, 'X');
        gameLogic.move(1, 0, 'O');
        gameLogic.move(1, 2, 'O');
        assertEquals(2, BotLogic.go(gameLogic, 'X', 2));
        assertEquals(4, BotLogic.go(gameLogic, 'O', 2));
    }
}