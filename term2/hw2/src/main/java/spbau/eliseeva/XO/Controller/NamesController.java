package spbau.eliseeva.XO.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import spbau.eliseeva.XO.Util.FXMLLoaderCreator;

/**
 * This class is a controller for the "names.fxml" file. This window is
 * opened when you play with your friend. This class sends the GameController
 * information about the mode and the users names.
 */
public class NamesController {
    /** Button which starts the game when clicked.*/
    @FXML
    Button buttonPlay;

    /** The text field with the X player name.*/
    @FXML
    TextField textX;

    /** The text field with the O player name.*/
    @FXML
    TextField textO;

    /** Starts the game with two players.*/
    @FXML
    public void initialize(){
        buttonPlay.setOnMouseClicked((event) -> {
            GameController controller = FXMLLoaderCreator.load("game.fxml",
                    "Game", (Stage) buttonPlay.getScene().getWindow()).getController();
            controller.init(0, textX.getText(), textO.getText());
        });
        buttonPlay.setFocusTraversable(false);
    }
}
