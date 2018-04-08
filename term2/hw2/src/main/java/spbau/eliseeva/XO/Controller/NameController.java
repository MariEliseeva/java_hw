package spbau.eliseeva.XO.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import spbau.eliseeva.XO.Util.FXMLLoaderCreator;

import java.util.Random;

/**
 * This class is a controller for the "name.fxml" file. This window is
 * opened when you play with the PC. This class chooses what for the PC
 * will be playing and sends the GameController information about the mode
 * and the user name.
 */
public class NameController {
    /** Button which starts the game when clicked.*/
    @FXML
    Button button_play;

    /** A text field to write a user name to.*/
    @FXML
    TextField text_name;

    /** A text where written what the user will be playing for: X ot O. */
    @FXML
    Text text_user;

    /** A text where written what the PC will be playing for: X ot O. */
    @FXML
    Text text_pc;

    /** 1 if we are playing easy, 2 if hard.*/
    private int mode;

    /**
     * Sets the mode to play.
     * @param i game mode
     */
    void mode(int i) {
        mode = i;
    }

    /**
     * Changes randomly who will be playing X: PC or user.
     * Writes the needed texts to needed fields. Starts
     * the game when the button is clicked.
     */
    @FXML
    public void initialize(){
        int PCNumber = new Random().nextInt(2);
        if (PCNumber == 0) {
            text_user.setText("O");
            text_user.setFill(Color.valueOf("#ff0035"));
            text_pc.setText("X");
            text_pc.setFill(Color.valueOf("#278c16"));

        } else {
            text_user.setText("X");
            text_user.setFill(Color.valueOf("#278c16"));
            text_pc.setText("O");
            text_pc.setFill(Color.valueOf("#ff0035"));

        }
        button_play.setOnMouseClicked((event) -> {
            GameController controller = FXMLLoaderCreator.load("game.fxml",
                    "Game").getController();
            if (PCNumber == 0) {
                controller.init(mode, "PC", text_name.getText());
            } else {
                controller.init( mode, text_name.getText(), "PC");
            }
            Stage stageCurrent = (Stage) button_play.getScene().getWindow();
            stageCurrent.close();
        });
    }
}