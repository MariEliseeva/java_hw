package spbau.eliseeva.XO.Util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests if the logic of the game is correct: wins
 * the expected person, the expected signs are put in the game board.
 */
public class GameLogicTest {
    @Test
    public void testEmptyGame() {
        GameLogic gameLogic = new GameLogic();
        assertEquals(' ', gameLogic.getCell(0, 0));
        assertFalse(gameLogic.isDraw());
        assertFalse(gameLogic.isWin('X'));
    }

    @Test
    public void testMove() {
        GameLogic gameLogic = new GameLogic();
        gameLogic.move(1, 2, 'X');
        gameLogic.move(0, 1, 'O');
        assertEquals('X', gameLogic.getCell(1, 2));
        assertEquals('O', gameLogic.getCell(0, 1));
    }

    @Test
    public void testIsWinHorizontal() {
        GameLogic gameLogic1 = new GameLogic();
        gameLogic1.move(0, 0, 'X');
        gameLogic1.move(0, 1, 'X');
        gameLogic1.move(0, 2, 'X');
        assertTrue(gameLogic1.isWin('X'));
        GameLogic gameLogic2 = new GameLogic();
        gameLogic2.move(2, 0, 'O');
        gameLogic2.move(2, 1, 'O');
        gameLogic2.move(2, 2, 'O');
        assertTrue(gameLogic2.isWin('O'));
        GameLogic gameLogic3 = new GameLogic();
        gameLogic3.move(1, 0, 'X');
        gameLogic3.move(1, 1, 'X');
        gameLogic3.move(1, 2, 'X');
        assertTrue(gameLogic3.isWin('X'));
    }

    @Test
    public void testIsWinVertical() {
        GameLogic gameLogic1 = new GameLogic();
        gameLogic1.move(0, 0, 'O');
        gameLogic1.move(1, 0, 'O');
        gameLogic1.move(2, 0, 'O');
        assertTrue(gameLogic1.isWin('O'));
        GameLogic gameLogic2 = new GameLogic();
        gameLogic2.move(0, 1, 'X');
        gameLogic2.move(1, 1, 'X');
        gameLogic2.move(2, 1, 'X');
        assertTrue(gameLogic2.isWin('X'));
        GameLogic gameLogic3 = new GameLogic();
        gameLogic3.move(0, 2, 'X');
        gameLogic3.move(1, 2, 'X');
        gameLogic3.move(2, 2, 'X');
        assertTrue(gameLogic3.isWin('X'));
    }

    @Test
    public void testIsWinDiagonal() {
        GameLogic gameLogic1 = new GameLogic();
        gameLogic1.move(0, 0, 'O');
        gameLogic1.move(1, 1, 'O');
        gameLogic1.move(2, 2, 'O');
        assertTrue(gameLogic1.isWin('O'));
        GameLogic gameLogic2 = new GameLogic();
        gameLogic2.move(0, 2, 'X');
        gameLogic2.move(1, 1, 'X');
        gameLogic2.move(2, 0, 'X');
        assertTrue(gameLogic2.isWin('X'));
    }

    @Test
    public void testIsDraw() {
        GameLogic gameLogic1 = new GameLogic();
        gameLogic1.move(0, 0, 'O');
        gameLogic1.move(1, 0, 'O');
        gameLogic1.move(2, 0, 'O');
        assertFalse(gameLogic1.isDraw());
        GameLogic gameLogic2 = new GameLogic();
        gameLogic2.move(0, 0, 'O');
        gameLogic2.move(0, 1, 'X');
        gameLogic2.move(0, 2, 'O');
        assertFalse(gameLogic2.isDraw());
        gameLogic2.move(1, 0, 'X');
        gameLogic2.move(1, 1, 'X');
        gameLogic2.move(1, 2, 'O');
        gameLogic2.move(2, 0, 'X');
        gameLogic2.move(2, 1, 'O');
        gameLogic2.move(2, 2, 'X');
        assertTrue(gameLogic2.isDraw());
    }
}