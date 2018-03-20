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
    Button button_play;

    /** The text field with the X player name.*/
    @FXML
    TextField text_x;

    /** The text field with the O player name.*/
    @FXML
    TextField text_o;

    /** Starts the game with two players.*/
    @FXML
    public void initialize(){
        button_play.setOnMouseClicked((event) -> {
            GameController controller = FXMLLoaderCreator.load("game.fxml",
                    "Game").getController();
            controller.init(0, text_x.getText(), text_o.getText());
            Stage stageCurrent = (Stage) button_play.getScene().getWindow();
            stageCurrent.close();
        });
    }
}
