package spbau.eliseeva.findPair;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/** Creates the field with needed size and controls the game.*/
class GameController {
     private Scene scene;
     private Button[][] buttons;
     private Button previousButton;
     private GameLogic gameLogic;

    /**
     * Creates the buttons to push.
     * @param size needed size of the board.
     */
     GameController(int size) {
            gameLogic = new GameLogic(size);
            buttons = new Button[size][];
            for (int i = 0; i < size; i++) {
                buttons[i] = new Button[size];
                for (int j = 0; j < size; j++) {
                    buttons[i][j] = makeButton(i, j);
                }
            }
            HBox[] rows = new HBox[size];
            for (int i = 0; i < size; i++) {
                rows[i] = new HBox();
                for (int j = 0; j < size; j++) {
                    Button button = buttons[i][j];
                    HBox.setHgrow(button, Priority.ALWAYS);
                    button.prefWidthProperty().bind(rows[i].widthProperty().divide(size));
                    rows[i].getChildren().add(button);
                }
            }
            VBox field = new VBox();
            for (HBox row : rows) {
                VBox.setVgrow(row, Priority.ALWAYS);
                field.getChildren().add(row);
            }
            this.scene = new Scene(field, 400, 400);
     }

     private Button makeButton(int x, int y) {
         Button button = new Button();
         button.setFocusTraversable(false);
         button.setMaxWidth(400);
         button.setMaxHeight(400);
         button.setStyle(" -fx-text-fill: #0047ff;  -fx-opacity: 1.0;");
         button.setOnMouseClicked(event -> {
             button.setDisable(true);
             button.setText(String.valueOf(gameLogic.getCell(x, y)));
             if (gameLogic.push(x, y)) {
                 previousButton = button;
             } else {
                 if (gameLogic.isGood(x, y)) {
                     if (gameLogic.isWin()) {
                         PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
                         delay.setOnFinished( event1 -> {
                             FXMLLoader fxmlLoader = new FXMLLoader();
                             try {
                                 fxmlLoader.setLocation(new File("src/main/resources/end.fxml").toURI().toURL());
                             } catch (MalformedURLException e) {
                                 Platform.exit();
                             }
                             Scene scene = null;
                             try {
                                 scene = new Scene(fxmlLoader.load(), 300, 300);
                             } catch (IOException e) {
                                 Platform.exit();
                             }
                             Stage stage = (Stage) buttons[0][0].getScene().getWindow();
                             stage.setTitle("Win!");
                             stage.setScene(scene);
                             stage.show();
                         });
                         delay.play();
                     }
                 } else {
                     PauseTransition delay = new PauseTransition(Duration.seconds(0.3));
                     delay.setOnFinished( event1 -> {
                         previousButton.setText("");
                         previousButton.setDisable(false);
                         button.setText("");
                         button.setDisable(false);
                     });
                     delay.play();
                 }
             }
         });
         return button;
     }

    /**
     * Returns the scene to set.
     * @return the scene twith the buttons
     */
    Scene getScene() {
         return scene;
     }
}
