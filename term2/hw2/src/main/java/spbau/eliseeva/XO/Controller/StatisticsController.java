package spbau.eliseeva.XO.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import spbau.eliseeva.XO.Util.StatisticsReaderWriter;

import java.util.ArrayList;

/**
 * This class is a controller for the "statistics.fxml" file. This window is
 * opened when you want to see the statistics which is saved in a file.
 */
public class StatisticsController {
    /** The list with the ended games.*/
    @FXML
    ListView<String> list_results;

    /** Shows the list with the ended games.*/
    @FXML
    public void initialize() {
        ArrayList<String> readLines = StatisticsReaderWriter.read();
        ObservableList<String> lines = FXCollections.observableArrayList(readLines);
        list_results.setItems(lines);
    }
}
