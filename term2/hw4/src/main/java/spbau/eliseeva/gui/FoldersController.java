package spbau.eliseeva.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import spbau.eliseeva.ftp.FTPClient;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Map;

/** Controller for a list with directories.*/
class FoldersController {
    private Scene scene;
    private FTPClient client;
    private Map<String, Boolean> names;
    private int portNumber;
    private String hostName;

    /**
     * Creates new controller and all the buttons.
     * @param root name of directory to show
     * @param portNumber number of port to connect
     * @param hostName name of host
     */
    FoldersController(String root, int portNumber, String hostName) {
        this.client = new FTPClient(portNumber, hostName);
        this.portNumber = portNumber;
        this.hostName = hostName;
        try {
            names = client.listAnswer(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button[] buttons = new Button[names.size()];
        int index = 0;
        for (Map.Entry<String, Boolean> entry : names.entrySet()) {
            buttons[index] = makeButton(root, entry.getKey(), entry.getValue());
            index++;
        }
        HBox[] rows;
        int j = 0;
        if (!root.equals("/")) {
            rows = new HBox[names.size() + 1];
            rows[0] = new HBox();
            Button backButton = new Button();
            backButton.setFocusTraversable(false);
            backButton.setMaxWidth(400);
            backButton.setMaxHeight(400);
            backButton.setText("<-");
            backButton.setStyle(" -fx-text-fill: #c90d65; -fx-opacity: 1.0;");
            backButton.setOnMouseClicked(event -> {
                Stage stage = (Stage) backButton.getScene().getWindow();
                int ind = root.length() - 1;
                while (root.charAt(ind) != '/') {
                    ind--;
                }
                if (ind == 0) {
                    ind++;
                }
                String newRoot = root.substring(0, ind);
                stage.setScene(new FoldersController(newRoot, portNumber, hostName).getScene());
                stage.show();
            });

            HBox.setHgrow(backButton, Priority.ALWAYS);
            backButton.prefWidthProperty().bind(rows[0].widthProperty().divide(2));
            rows[0].getChildren().add(backButton);
            j++;
        } else {
            rows = new HBox[names.size()];
        }
        for (int i = 0; i < names.size(); i++, j++) {
            rows[j] = new HBox();
            HBox.setHgrow(buttons[i], Priority.ALWAYS);
            buttons[i].prefWidthProperty().bind(rows[j].widthProperty().divide(2));
            rows[j].getChildren().add(buttons[i]);
        }
        VBox field = new VBox();
        for (HBox row : rows) {
            VBox.setVgrow(row, Priority.ALWAYS);
            field.getChildren().add(row);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(field);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        field.setPrefSize(380, 600);

        this.scene = new Scene(scrollPane, 400, 600);
    }

    /**
     * Creates a button for a file or directory
     * @param root name of current dir
     * @param text name of file or dir to open
     * @param isDirectory true if directory
     * @return the button
     */
    private Button makeButton(String root, String text, Boolean isDirectory) {
        Button button = new Button();
        button.setFocusTraversable(false);
        button.setMaxWidth(400);
        button.setMaxHeight(400);
        button.setStyle(" -fx-text-fill: #c90d65; -fx-opacity: 1.0;");
        button.setText(text);
        if (isDirectory) {
            button.setOnMouseClicked(event -> {
                Stage stage = (Stage) button.getScene().getWindow();
                String newRoot = root;
                if (!root.equals("/")) {
                    newRoot += '/';
                }
                stage.setScene(new FoldersController(newRoot + text, portNumber, hostName).getScene());
                stage.show();
            });
        } else {
            button.setOnMouseClicked(event -> {
                FXMLLoader fxmlLoader = new FXMLLoader();
                try {
                    fxmlLoader.setLocation(new File("src/main/resources/save.fxml").toURI().toURL());
                } catch (MalformedURLException e) {
                    Platform.exit();
                }
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load(), 400, 200);
                } catch (IOException e) {
                    Platform.exit();
                }
                ((SaveController)fxmlLoader.getController()).initialize(client, root);
                Stage stage = new Stage();
                stage.setTitle("Save");
                stage.setScene(scene);
                stage.show();
            });
        }
        return button;
    }

    /**
     * Returns scene
     * @return scene
     */
    Scene getScene() {
        return scene;
    }
}
