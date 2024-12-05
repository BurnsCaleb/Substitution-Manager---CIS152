package substitution.manager.cis152.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Player;
import model.Roster;
import model.RosterIO;

/**
 * FXML Controller class
 *
 * @author burns
 */
public class SelectRosterScreenController implements Initializable {

    @FXML
    private List<Roster> rosters;
    @FXML
    private VBox rosterContainer;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        rosters = RosterIO.selectAllRosters();
        
        displayRosters();
        displayBackButton();
    }
    
    private void displayRosters() {
        if (rosterContainer != null) {
             rosterContainer.getChildren().clear();
        }
        
        rosterContainer.setAlignment(Pos.CENTER);
        
        for (Roster roster : rosters) {
            HBox rosterBox = new HBox(10);
            rosterBox.setAlignment(Pos.CENTER);
            
            ListView<String> playerListView = new ListView<>();
            
            ObservableList<String> playerNames = FXCollections.observableArrayList();
            for (Player player : roster.getRoster()) {
                playerNames.add(player.getPlayerName());
            }
            playerListView.setItems(playerNames);
            
            
            Button selectRosterButton = new Button("Select " + roster.getRosterTitle());
            selectRosterButton.setOnAction(e -> selectRoster(roster));
            
            rosterBox.getChildren().addAll(playerListView, selectRosterButton);
          
            rosterContainer.getChildren().add(rosterBox);  
        }
    }
    
    public void displayBackButton() {
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_LEFT);
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> backToMain());
        
        buttonBox.getChildren().add(backButton);
        rosterContainer.getChildren().add(buttonBox);
    }
    

    private void selectRoster(Roster roster) {
        Roster playingRoster = roster;    
        
        try {          
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/substitution/manager/cis152/fxml/GameSetupScreen.fxml"));
            StackPane newRoot = loader.load();
            
            GameSetupScreenController setupController = loader.getController();
            setupController.initData(playingRoster);
            
            Scene newScene = new Scene(newRoot, 600, 850);

            Stage currentStage = (Stage) rosterContainer.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private void backToMain() {
        try {
            StackPane newRoot = FXMLLoader.load(getClass().getResource("/substitution/manager/cis152/fxml/StartScreen.fxml"));

            Scene newScene = new Scene(newRoot, 600, 850);

            Stage currentStage = (Stage) rosterContainer.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
