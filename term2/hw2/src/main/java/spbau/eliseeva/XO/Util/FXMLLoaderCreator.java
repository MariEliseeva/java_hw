package spbau.eliseeva.XO.Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/** Opens a needed window by given name.*/
public class FXMLLoaderCreator {
    /**
     * Opens a needed window by given name and with given title.
     * @param fileName name of a file to download
     * @param title title of the new window
     * @return FXMLLoader for next actions (like calling the controller)
     */
    public static FXMLLoader load(String fileName, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(new File("src/main/resources/" + fileName).toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 300, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        return fxmlLoader;
    }
}