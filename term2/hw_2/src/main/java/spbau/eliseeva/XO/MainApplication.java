package spbau.eliseeva.XO;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/** This class runs the application.*/
public class MainApplication extends Application {
    /**Starts the application.*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(new File("src/main/resources/menu.fxml").toURI().toURL());
        primaryStage.setTitle("Menu");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }

    /**
     * Runs the program.
     * @param args args were given.
     */
    public static void main(String[] args) {
        launch(args);
    }
}