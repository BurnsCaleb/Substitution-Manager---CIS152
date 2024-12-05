package substitution.manager.cis152.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Bench;
import model.Court;
import model.GameSetup;
import model.Player;
import model.Roster;

/**
 * FXML Controller class
 *
 * @author burns
 */
public class GameSetupScreenController implements Initializable {

    private Roster roster;
    private GameSetup newGame;
    
    private Court onCourt;
    private Bench onBench;

    private ComboBox<Integer> numQuartersIn;
    private TextField quarterLength;
    private Alert errorAlert = new Alert(AlertType.ERROR);

    @FXML
    private VBox inContainer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initData(Roster roster) {
        this.roster = roster;

        populateView();
    }
    
    private void populateView(){
        displayInputFields();
        displayButtons();
    }

    private void displayInputFields() {
        if (inContainer != null) {
            inContainer.getChildren().clear();
        }

        Label numQuarterLabel = new Label("Number of Quarters:");

        Integer quarters[] = {1, 2, 3, 4};

        numQuartersIn = new ComboBox(FXCollections.observableArrayList(quarters));
        numQuartersIn.setPromptText("Select Number of Quarters");

        Label quarterLengthLabel = new Label("Length of Quarters:");

        quarterLength = new TextField();
        quarterLength.setPromptText("Enter Quarter Length");
        quarterLength.setMaxWidth(130);

        inContainer.getChildren().addAll(numQuarterLabel, numQuartersIn, quarterLengthLabel, quarterLength);
    }

    private void displayButtons() {
        HBox buttons = new HBox(10);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> backToRosterSelection());

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> startGame());

        buttons.getChildren().addAll(backButton, submitButton);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        inContainer.getChildren().add(buttons);
    }

    private void backToRosterSelection() {
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

    private void startGame() {
        boolean goodValue = false;
        try {   
            if (numQuartersIn.getValue() == null) {
                errorAlert.setContentText("Please Select the Number of Quarters");
                errorAlert.show();
            } else if (quarterLength.getText().isEmpty()) {
                errorAlert.setContentText("Please Enter Quarter Length");
                errorAlert.show();
            } else {
                try {
                    int quarterLengthInt = Integer.parseInt(quarterLength.getText());

                    newGame = new GameSetup(numQuartersIn.getValue(), quarterLengthInt);
                    goodValue = true;
                } catch (NumberFormatException e) {
                    errorAlert.setContentText("Only Enter Numeric Values.");
                    errorAlert.show();
                }
            }

            if (goodValue == true) {
                setRoster();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/substitution/manager/cis152/fxml/GameScreen.fxml"));
                StackPane newRoot = loader.load();

                GameScreenController gameController = loader.getController();
                gameController.initData(roster, newGame, onCourt, onBench);

                Scene newScene = new Scene(newRoot, 600, 850);

                Stage currentStage = (Stage) inContainer.getScene().getWindow();

                currentStage.setScene(newScene);
                currentStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void setRoster() {
        /*
        First 5 on roster are default starters
        Rest of team are on the bench
        
        Create the stack and queue by sorting through the array list of players
        Add to table
         */
        
        ArrayList<Player> players = roster.getRoster();
        ArrayList<Player> starting5 = new ArrayList<>();
        ArrayList<Player> bench = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            starting5.add(players.get(i));
        }

        onCourt = new Court(starting5);

        for (int x = 5; x < players.size(); x++) {
            bench.add(players.get(x));
        }
        onBench = new Bench(bench);
    }

}
