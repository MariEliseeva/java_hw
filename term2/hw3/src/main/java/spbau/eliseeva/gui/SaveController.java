package spbau.eliseeva.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import spbau.eliseeva.ftp.FTPClient;

import java.io.IOException;

/** Controller for saving file */
public class SaveController {
    /** button to save the file*/
    @FXML
    Button saveButton;

    /** name of file to save*/
    @FXML
    TextField fileName;

    /**
     * Gives a task to the button
     * @param client client giving the result
     * @param oldFileName name of file on server
     */
    void initialize(FTPClient client, String oldFileName) {
        saveButton.setFocusTraversable(false);
        saveButton.setOnMouseClicked((event) -> {
            try {
                client.getAnswer(oldFileName, fileName.getText());
            } catch (IOException ignored) {
            }
            ((Stage)saveButton.getScene().getWindow()).close();
        });
    }
}
