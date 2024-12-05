package substitution.manager.cis152.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.RosterIO;

/**
 * FXML Controller class
 *
 * @author burns
 */
public class CreateRosterScreenController implements Initializable {

    @FXML
    private VBox inContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        displayInputFields();
    }

    private void displayInputFields() {
        if (inContainer != null) {
            inContainer.getChildren().clear();
        }
        
        ArrayList<TextField> names = new ArrayList<>();

        Label instructions1 = new Label("Enter up to 15 player names");
        Label instructions2 = new Label("Leave the extra input fields blank if needed");

        inContainer.getChildren().addAll(instructions1, instructions2);

        for (int i = 0; i < 15; i++) {
            HBox fieldBox = new HBox(10);
            fieldBox.setAlignment(Pos.CENTER);

            Label count = new Label((i + 1) + "");

            TextField nameInput = new TextField();
            nameInput.setPromptText("Enter Player Name");
            nameInput.maxWidth(200);
            names.add(nameInput);

            fieldBox.getChildren().addAll(count, nameInput);
            inContainer.getChildren().add(fieldBox);
        }

        HBox buttons = new HBox(10);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> backToRosterOptions());

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> processInput(names));

        buttons.getChildren().addAll(backButton, submitButton);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        inContainer.getChildren().add(buttons);
    }

    private void backToRosterOptions() {
        try {
            StackPane newRoot = FXMLLoader.load(getClass().getResource("/substitution/manager/cis152/fxml/RosterOptionScreen.fxml"));

            Scene newScene = new Scene(newRoot, 600, 850);

            Stage currentStage = (Stage) inContainer.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validate input
    // Go to game setup
    private void processInput(ArrayList<TextField> names) {
        ArrayList<String> sNames = new ArrayList<>();
        for (TextField name : names) {
            if (name.getText() != null && !name.getText().isEmpty()){
                sNames.add(name.getText());
            }         
        }
        RosterIO.save(sNames);
        
        try {
            StackPane newRoot = FXMLLoader.load(getClass().getResource("/substitution/manager/cis152/fxml/SelectRosterScreen.fxml"));

            Scene newScene = new Scene(newRoot, 600, 850);

            Stage currentStage = (Stage) inContainer.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
