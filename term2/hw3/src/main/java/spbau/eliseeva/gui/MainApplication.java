package spbau.eliseeva.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/** This class runs the application.*/
public class MainApplication extends Application {
    /**Starts the application using given in parameters size of game board.*/
    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/resources/menu.fxml").toURI().toURL());
        } catch (IOException e) {
            Platform.exit();
        }
        primaryStage.setTitle("Menu");
        assert root != null;
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }

    /**
     * Runs the program.
     * @param args first argument is game board size.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
