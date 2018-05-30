package spbau.eliseeva.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/** Controller for the menu.*/
public class MenuController {
    /** Button to push to connect to server.*/
    @FXML
    Button connectButton;

    /** Name of host*/
    @FXML
    TextField hostName;

    /** Number of port */
    @FXML
    TextField portNumber;

    /** Gives the needed tasks to all the buttons: to open the needed window.*/
    @FXML
    public void initialize() {
        connectButton.setFocusTraversable(false);
        connectButton.setOnMouseClicked((event) -> {
            Stage stage = (Stage) connectButton.getScene().getWindow();
            stage.setScene(new FoldersController("/", Integer.parseInt(portNumber.getText()), hostName.getText()).getScene());
            stage.show();
        });
    }
}