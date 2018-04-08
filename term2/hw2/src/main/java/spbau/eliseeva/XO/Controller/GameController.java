package spbau.eliseeva.XO.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import spbau.eliseeva.XO.Util.BotLogic;
import spbau.eliseeva.XO.Util.FXMLLoaderCreator;
import spbau.eliseeva.XO.Util.GameLogic;
import spbau.eliseeva.XO.Util.StatisticsReaderWriter;

import java.util.ArrayList;

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
    private final GameLogic gameLogic = new GameLogic();

    /** Who is going now: X ot O. X are always first. */
    private char turn = 'X';

    /**
     * Opens the new window and sends to it the game results.
     * @param winText text with an information about the game results
     * @param statisticsText text which should be written in the game statistics, to watch later
     */
    private void end(String winText, String statisticsText) {
        EndController controller = FXMLLoaderCreator.load("end.fxml", winText).getController();
        controller.setText(winText, (Stage) button00.getScene().getWindow());
        updateStatistics(statisticsText);
    }

    /**
     * Whites the current game results in the begin of the file, saving the statistics.
     * @param winText information about the results.
     */
    private void updateStatistics(String winText) {
        ArrayList<String> results = new ArrayList<>();
        results.add(nameX + " vs " + nameO + ": " + winText);
        results.addAll(StatisticsReaderWriter.read());
        StatisticsReaderWriter.write(results);
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
            end(nameX + " wins!", nameX + " won.");
            return;
        }
        if (gameLogic.isWin('O')) {
            end(nameO + " wins!", nameO + " won.");
            return;
        }
        if (gameLogic.isDraw()) {
            end("Draw.", "Draw.");
            return;
        }
        if (mode != 0 && !isBot) {
            go(BotLogic.go(gameLogic, turn, mode), true);
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
        if (nameX.equals("PC")) {
            who.setText("O goes");
            go(BotLogic.go(gameLogic, turn, mode), true);
        } else {
            who.setText("X goes");
        }
        button00.setOnMouseClicked(mouseEvent -> go(0, false));
        button01.setOnMouseClicked(mouseEvent -> go(1, false));
        button02.setOnMouseClicked(mouseEvent -> go(2, false));
        button10.setOnMouseClicked(mouseEvent -> go(3, false));
        button11.setOnMouseClicked(mouseEvent -> go(4, false));
        button12.setOnMouseClicked(mouseEvent -> go(5, false));
        button20.setOnMouseClicked(mouseEvent -> go(6, false));
        button21.setOnMouseClicked(mouseEvent -> go(7, false));
        button22.setOnMouseClicked(mouseEvent -> go(8, false));
    }
}
