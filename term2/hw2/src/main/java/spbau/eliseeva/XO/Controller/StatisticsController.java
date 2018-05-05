package spbau.eliseeva.XO.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import spbau.eliseeva.XO.Util.FXMLLoaderCreator;
import spbau.eliseeva.XO.Util.Information;

/**
 * This class is a controller for the "statistics.fxml" file. This window is
 * opened when you want to see the statistics which is saved in a file.
 */
public class StatisticsController {
    /** The list with the ended games.*/
    @FXML
    ListView<String> listResults;

    /** Button to close the e statistics.*/
    @FXML
    Button closeButton;

    /** Shows the list with the ended games.*/
    @FXML
    public void initialize() {
        listResults.setItems(FXCollections.observableArrayList(Information.getStatistics()));
        closeButton.setOnMouseClicked(event -> FXMLLoaderCreator.load("menu.fxml", "Menu",
                (Stage) closeButton.getScene().getWindow()));
        closeButton.setFocusTraversable(false);
    }
}
