package spbau.eliseeva.XO.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import spbau.eliseeva.XO.Util.FXMLLoaderCreator;

/**
 * This class is a controller for the "menu.fxml" file. There are four buttons
 * about different actions which we can do in the applications, and these
 * button are controlled there.
 */
public class MenuController {

    /** Button to play with friend when clicked.*/
    @FXML
    Button buttonFriend;

    /** Buttons to play with PC (two different levels) when clicked.*/
    @FXML
    Button buttonPCEasy;
    @FXML
    Button buttonPCHard;

    /** Button to see the previous games results.*/
    @FXML
    Button buttonStatistics;

    /** Gives the needed tasks to all the buttons: to open the needed window.*/
    @FXML
    public void initialize(){
        buttonFriend.setFocusTraversable(false);
        buttonPCEasy.setFocusTraversable(false);
        buttonPCHard.setFocusTraversable(false);
        buttonStatistics.setFocusTraversable(false);
        buttonFriend.setOnMouseClicked((event) ->
                FXMLLoaderCreator.load("names.fxml", "Text your name.",
                        (Stage) buttonFriend.getScene().getWindow()));
        buttonPCEasy.setOnMouseClicked((event) -> {
            NameController controller = FXMLLoaderCreator.load("name.fxml",
                    "Text your names.", (Stage) buttonFriend.getScene().getWindow()).getController();
            controller.mode(1);
        });
        buttonPCHard.setOnMouseClicked((event) -> {
            NameController controller = FXMLLoaderCreator.load("name.fxml",
                    "Text your names.", (Stage) buttonFriend.getScene().getWindow()).getController();
            controller.mode(2);
        });
        buttonStatistics.setOnMouseClicked((event) ->
                FXMLLoaderCreator.load("statistics.fxml", "Previous games.",
                        (Stage) buttonFriend.getScene().getWindow()));
    }
}
