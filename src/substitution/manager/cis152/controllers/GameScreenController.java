package substitution.manager.cis152.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Bench;
import model.GameSetup;
import model.Court;
import model.Player;
import model.Roster;

/**
 * FXML Controller class
 *
 * @author burns
 */
public class GameScreenController implements Initializable {

    // Selected Roster and Game Settings
    private Roster roster;
    private GameSetup currGame;

    // Stack and Queue
    private Court onCourt;
    private Bench onBench;

    @FXML
    private VBox tableView;
    @FXML
    private VBox buttonView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initData(Roster roster, GameSetup currGame, Court onCourt, Bench onBench) {
        this.roster = roster;
        this.currGame = currGame;
        this.onCourt = onCourt;
        this.onBench = onBench;

        populateView();
    }

    private void populateView() {
        createTable();
        createButtons();
    }

    private void createTable() {
        if (tableView != null) {
            tableView.getChildren().clear();
        }

        // Number of minutes each player should play to have even minutes
        int targetMins = ((currGame.getNumQuarters() * currGame.getQuarterLength()) * 5) / roster.size();
        Label targetMinutes = new Label("Target Minutes For Each Player: " + targetMins);

        TableView playerTable = new TableView();

        // Table Column Names 
        TableColumn<Player, String> column1 = new TableColumn<>("Name");
        TableColumn<Player, Double> column2 = new TableColumn<>("Minutes Played");

        column1.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        column1.setMinWidth(448);
        column2.setCellValueFactory(new PropertyValueFactory<>("minsPlayed"));
        column2.setMinWidth(148);

        playerTable.getColumns().addAll(column1, column2);

        

        ObservableList<Player> allPlayers = FXCollections.observableArrayList();
        allPlayers.addAll(onCourt.getOnCourt());
        allPlayers.addAll(onBench.getBench());
        playerTable.setItems(allPlayers);

        tableView.getChildren().addAll(targetMinutes, playerTable);

        // Highlight 5 players on the court
        playerTable.setRowFactory(pv -> new TableRow<Player>() {
            @Override
            public void updateItem(Player item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (getIndex() < 5 && !empty) {
                    setStyle("-fx-background-color: cyan;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    private void createButtons() {
        if (buttonView != null) {
            buttonView.getChildren().clear();
        }
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        
        Button endGame = new Button("End Game");
        Button makeSub = new Button("Make Substitution");

        endGame.setOnAction(e -> backToMain());
        makeSub.setOnAction(e -> toSubstitutionScreen());

        buttonBox.getChildren().addAll(endGame, makeSub);
        buttonView.getChildren().add(buttonBox);
    }

    private void backToMain() {
        try {
            StackPane newRoot = FXMLLoader.load(getClass().getResource("/substitution/manager/cis152/fxml/StartScreen.fxml"));

            Scene newScene = new Scene(newRoot, 600, 850);

            Stage currentStage = (Stage) buttonView.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toSubstitutionScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/substitution/manager/cis152/fxml/SubstitutionScreen.fxml"));
            StackPane newRoot = loader.load();

            SubstitutionScreenController gameController = loader.getController();
            gameController.initData(onCourt, onBench, currGame, roster);

            Scene newScene = new Scene(newRoot, 600, 850);

            Stage currentStage = (Stage) buttonView.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

}
