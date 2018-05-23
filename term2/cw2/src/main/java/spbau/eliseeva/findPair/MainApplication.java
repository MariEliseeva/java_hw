package spbau.eliseeva.findPair;

import javafx.application.Application;
import javafx.stage.Stage;

/** This class runs the application.*/
public class MainApplication extends Application {
    /**Starts the application using given in parameters size of game board.*/
    @Override
    public void start(Stage primaryStage) {
        int n = 2;
        try {
            n = Integer.parseInt(getParameters().getRaw().get(0));
        } catch (NumberFormatException ignored) {
        }
        if (n != 0 && n % 2 == 1) {
            n++;
        }
        primaryStage.setTitle("Find Pair");
        primaryStage.setScene(new GameController(n).getScene());
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