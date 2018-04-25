package spbau.eliseeva.XO.Controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import spbau.eliseeva.XO.Util.*;


/**
 * This class is a controller for the "game.fxml" file. It looks for the game
 * results and update cells then was made a move.
 */
public class GameController {
    /** Nine buttons for all nine cells of the game field.*/
    @FXML
    Button button00;
    @FXML
    Button button01;
    @FXML
    Button button02;
    @FXML
    Button button10;
    @FXML
    Button button11;
    @FXML
    Button button12;
    @FXML
    Button button20;
    @FXML
    Button button21;
    @FXML
    Button button22;

    /** A text, giving an information about who should go: X or O.*/
    @FXML
    Text who;

    /** Game mode: 0 if with two players, 1 if with easy bot, 2 if with hard bot.*/
    private int mode;

    /** Name of the player, who is responsible for 'X'.*/
    private String nameX;

    /** Name of the player, who is responsible for 'O'.*/
    private String nameO;

    /** An object, that controls the game and its results.*/
    private final GameLogic gameLogic = new GameLogic(3);

    /** Who is going now: X ot O. X are always first. */
    private char turn = 'X';

    /**
     * Blocks all buttons and open results.
     * @param winText information about results
     * @param isXWon true if X won
     * @param isDraw true if draw
     */
    private void end(String winText, boolean isXWon, boolean isDraw) {
        button00.setDisable(true);
        button01.setDisable(true);
        button02.setDisable(true);
        button10.setDisable(true);
        button11.setDisable(true);
        button12.setDisable(true);
        button20.setDisable(true);
        button21.setDisable(true);
        button22.setDisable(true);
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished( event -> {
            EndController controller = FXMLLoaderCreator.load("end.fxml", winText,
                    (Stage) button00.getScene().getWindow()).getController();
            controller.setText(winText);
            updateStatistics(isXWon, isDraw);
        });
        delay.play();
    }

    /**
     * Remembers statistics.
     * @param isXWon true is X won
     * @param isDraw true is draw
     */
    private void updateStatistics(boolean isXWon, boolean isDraw) {
        if (isXWon) {
            Information.setStatistic(nameX, nameO, isDraw);
        } else {
            Information.setStatistic(nameO, nameX, isDraw);
        }
    }

    /**
     * Changes 'X' to 'O' and back. Asks the bot to make a move, if
     * the mode is appropriate. Ends the game if someone has won or there is draw.
     * @param isBot false if the player is going and the bot should be called,
     *              true if the bot is going and the next move i for the player
     */
    private void changeTurn(boolean isBot) {
        if (turn == 'X') {
            turn = 'O';
            who.setText("O goes");
        } else {
            turn = 'X';
            who.setText("X goes");
        }
        if (gameLogic.isWin('X')) {
            end(nameX + " wins!", true, false);
            return;
        }
        if (gameLogic.isWin('O')) {
            end(nameO + " wins!", false, false);
            return;
        }
        if (gameLogic.isDraw()) {
            end("Draw.", false, true);
            return;
        }
        if (mode != 0 && !isBot) {
            if (mode == 1) {
                go(EasyBotLogic.go(gameLogic), true);
            } else {
                go(HardBotLogic.go(gameLogic, turn), true);
            }
        }
    }

    /**
     * Makes a move in a game logic, changes the connected button,
     * changes the turn to the next player.
     * @param index the number of a cell to move (all cells are given a number from 0 to 8)
     * @param isBot if the bot is going or the player.
     */
    private void go(int index, boolean isBot) {
        gameLogic.move(index / 3, index % 3, turn);
        updateButton(index);
        changeTurn(isBot);
    }

    /**
     * Updates the button, connected with needed cell.
     * @param index the number of a cell to move (all cells are given a number from 0 to 8)
     */
    private void updateButton(int index) {
        Button button = null;
        switch (index) {
            case 0: button = button00;
                    break;
            case 1: button = button01;
                    break;
            case 2: button = button02;
                    break;
            case 3: button = button10;
                    break;
            case 4: button = button11;
                    break;
            case 5: button = button12;
                    break;
            case 6: button = button20;
                    break;
            case 7: button = button21;
                    break;
            case 8: button = button22;
                    break;
        }
        assert button != null;
        if (!button.isDisabled()) {
            if (gameLogic.getCell(index / 3, index % 3) == 'X') {
                button.setText("X");
                button.setStyle("-fx-text-fill: #278c16; -fx-opacity: 1.0;");
            } else {
                button.setText("O");
                button.setStyle("-fx-text-fill: #ff0035; -fx-opacity: 1.0;");
            }
            button.setDisable(true);
        }
    }

    /**
     * Starts the game: checks if names are not empty, does a first move
     * if the PC is playing for X, gives tasks to all the buttons.
     * @param i mode to play
     * @param n1 name of the X player
     * @param n2 name of the O player
     */
    void init(int i, String n1, String n2) {
        mode = i;
        if (n1.equals("")) {
            nameX = "playerX";
        } else {
            nameX = n1;
        }
        if (n2.equals("")) {
            nameO = "playerO";
        } else {
            nameO = n2;
        }
        if (nameX.equals("PC-easy") || nameX.equals("PC-hard")) {
            who.setText("O goes");
            if (mode == 1) {
                go(EasyBotLogic.go(gameLogic), true);
            } else {
                go(HardBotLogic.go(gameLogic, turn), true);
            }
        } else {
            who.setText("X goes");
        }
        button00.setOnMouseClicked(mouseEvent -> go(0, false));
        button00.setFocusTraversable(false);
        button01.setOnMouseClicked(mouseEvent -> go(1, false));
        button01.setFocusTraversable(false);
        button02.setOnMouseClicked(mouseEvent -> go(2, false));
        button02.setFocusTraversable(false);
        button10.setOnMouseClicked(mouseEvent -> go(3, false));
        button10.setFocusTraversable(false);
        button11.setOnMouseClicked(mouseEvent -> go(4, false));
        button11.setFocusTraversable(false);
        button12.setOnMouseClicked(mouseEvent -> go(5, false));
        button12.setFocusTraversable(false);
        button20.setOnMouseClicked(mouseEvent -> go(6, false));
        button20.setFocusTraversable(false);
        button21.setOnMouseClicked(mouseEvent -> go(7, false));
        button21.setFocusTraversable(false);
        button22.setOnMouseClicked(mouseEvent -> go(8, false));
        button22.setFocusTraversable(false);
    }
}
