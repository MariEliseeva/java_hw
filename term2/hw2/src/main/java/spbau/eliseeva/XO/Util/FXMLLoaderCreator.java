package spbau.eliseeva.XO.Util;

import javafx.application.Platform;
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
    public static FXMLLoader load(String fileName, String title, Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.setLocation(new File("src/main/resources/" + fileName).toURI().toURL());
        } catch (MalformedURLException e) {
            Platform.exit();
        }
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 300, 300);
        } catch (IOException e) {
            Platform.exit();
        }
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        return fxmlLoader;
    }
}
