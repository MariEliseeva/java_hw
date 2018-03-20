package spbau.eliseeva.XO.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is the controller for the "end.fxml" file. There are
 * only one button and one text to write there. Button to close the game
 * and a text with the game results information.
 */
public class EndController {
    /**
     * Button which closes the game field and the window
     * with the results when clicked.
     */
    @FXML
    Button button_close;
    /** Text about results: who won or was it draw.*/
    @FXML
    Text text_who_won;

    /**
     * Called by another controller to give a result of a game, which
     * should be printed in this window, and also to close the game field.
     * @param text Text about the game results
     * @param gameStage Window with the game field, which must be closed.
     */
    void setText(String text, Stage gameStage) {
        text_who_won.setText(text);
        button_close.setOnMouseClicked(mouseEvent -> {
            gameStage.close();
            Stage stage = (Stage) button_close.getScene().getWindow();
            stage.close();
        });
    }
}
