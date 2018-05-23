package spbau.eliseeva.findPair;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * The window which is shown in the end.
 */
public class EndController {
    /**Button to push for closing everything. */
    @FXML
    Button buttonClose;

    /** Sets the closing action to the button.*/
    public void initialize() {
        buttonClose.setOnMouseClicked(mouseEvent -> Platform.exit());
        buttonClose.setFocusTraversable(false);
    }
}

