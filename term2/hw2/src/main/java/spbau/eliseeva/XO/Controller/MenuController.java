package spbau.eliseeva.XO.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import spbau.eliseeva.XO.Util.FXMLLoaderCreator;

/**
 * This class is a controller for the "menu.fxml" file. There are four buttons
 * about different actions which we can do in the applications, and these
 * button are controlled there.
 */
public class MenuController {

    /** Button to play with friend when clicked.*/
    @FXML
    Button button_friend;

    /** Buttons to play with PC (two different levels) when clicked.*/
    @FXML
    Button button_pc_easy;
    @FXML
    Button button_pc_hard;

    /** Button to see the previous games results.*/
    @FXML
    Button button_statistics;

    /** Gives the needed tasks to all the buttons: to open the needed window.*/
    @FXML
    public void initialize(){
        button_friend.setOnMouseClicked((event) ->
                FXMLLoaderCreator.load("names.fxml", "Text your name."));
        button_pc_easy.setOnMouseClicked((event) -> {
            NameController controller = FXMLLoaderCreator.load("name.fxml",
                    "Text your names.").getController();
            controller.mode(1);
        });
        button_pc_hard.setOnMouseClicked((event) -> {
            NameController controller = FXMLLoaderCreator.load("name.fxml",
                    "Text your names.").getController();
            controller.mode(2);
        });
        button_statistics.setOnMouseClicked((event) ->
                FXMLLoaderCreator.load("statistics.fxml", "Previous games."));
    }
}
