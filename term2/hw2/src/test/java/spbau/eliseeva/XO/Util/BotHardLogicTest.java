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
        GameLogic gameLogic1 = new GameLogic(3);
        gameLogic1.move(0, 2, 'X');
        gameLogic1.move(0, 1, 'X');
        assertEquals(0, HardBotLogic.go(gameLogic1,'O'));
        GameLogic gameLogic2 = new GameLogic(3);
        gameLogic2.move(2, 0, 'O');
        gameLogic2.move(2, 1, 'O');
        assertEquals(8, HardBotLogic.go(gameLogic2, 'O'));
        GameLogic gameLogic3 = new GameLogic(3);
        gameLogic3.move(1, 0, 'X');
        gameLogic3.move(1, 2, 'X');
        assertEquals(4, HardBotLogic.go(gameLogic3, 'O'));
    }
    @Test
    public void testGoVertical() {
        GameLogic gameLogic1 = new GameLogic(3);
        gameLogic1.move(2, 0, 'X');
        gameLogic1.move(1, 0, 'X');
        assertEquals(0, HardBotLogic.go(gameLogic1,'O'));
        GameLogic gameLogic2 = new GameLogic(3);
        gameLogic2.move(0, 2, 'O');
        gameLogic2.move(1, 2, 'O');
        assertEquals(8, HardBotLogic.go(gameLogic2, 'O'));
        GameLogic gameLogic3 = new GameLogic(3);
        gameLogic3.move(0, 1, 'X');
        gameLogic3.move(2, 1, 'X');
        assertEquals(4, HardBotLogic.go(gameLogic3, 'O'));
    }
    @Test
    public void testGoDiagonal() {
        GameLogic gameLogic1 = new GameLogic(3);
        gameLogic1.move(0, 0, 'X');
        gameLogic1.move(2, 2, 'X');
        assertEquals(4, HardBotLogic.go(gameLogic1,'O'));
        GameLogic gameLogic2 = new GameLogic(3);
        gameLogic2.move(0, 2, 'O');
        gameLogic2.move(1, 1, 'O');
        assertEquals(6, HardBotLogic.go(gameLogic2, 'O'));
    }
    @Test
    public void testBetterWinThanNoLose() {
        GameLogic gameLogic = new GameLogic(3);
        gameLogic.move(0, 0, 'X');
        gameLogic.move(0, 1, 'X');
        gameLogic.move(1, 0, 'O');
        gameLogic.move(1, 2, 'O');
        assertEquals(2, HardBotLogic.go(gameLogic, 'X'));
        assertEquals(4, HardBotLogic.go(gameLogic, 'O'));
    }
}