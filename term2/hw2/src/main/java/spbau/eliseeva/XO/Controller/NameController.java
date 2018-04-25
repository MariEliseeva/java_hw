package spbau.eliseeva.XO.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import spbau.eliseeva.XO.Util.FXMLLoaderCreator;
import spbau.eliseeva.XO.Util.Information;

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
    Button buttonPlay;

    /** A text field to write a user name to.*/
    @FXML
    TextField textName;

    /** A text where written what the user will be playing for: X ot O. */
    @FXML
    Text textUser;

    /** A text where written what the PC will be playing for: X ot O. */
    @FXML
    Text textPC;

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
        buttonPlay.setFocusTraversable(false);
        int PCNumber = new Random().nextInt(2);
        if (PCNumber == 0) {
            textUser.setText("O");
            textUser.setFill(Color.valueOf(Information.RED));
            textPC.setText("X");
            textPC.setFill(Color.valueOf(Information.GREEN));

        } else {
            textUser.setText("X");
            textUser.setFill(Color.valueOf(Information.GREEN));
            textPC.setText("O");
            textPC.setFill(Color.valueOf(Information.RED));

        }
        buttonPlay.setOnMouseClicked((event) -> {
            GameController controller = FXMLLoaderCreator.load("game.fxml",
                    "Game", (Stage) buttonPlay.getScene().getWindow()).getController();
            String PCName;
            if (mode == 1) {
                PCName = "PC-easy";
            } else {
                PCName = "PC-hard";
            }
            if (PCNumber == 0) {
                controller.init(mode, PCName, textName.getText());
            } else {
                controller.init( mode, textName.getText(), PCName);
            }
        });
    }
}