package substitution.manager.cis152.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author burns
 */
public class RosterOptionScreenController {
    
    @FXML
    private Button newRoster;
    @FXML
    private Button savedRoster;
    
    @FXML
    public void createRoster(ActionEvent event) {
        
        try {
            StackPane newRoot = FXMLLoader.load(getClass().getResource("/substitution/manager/cis152/fxml/CreateRosterScreen.fxml"));

            Scene newScene = new Scene(newRoot, 600, 850);

            Stage currentStage = (Stage) newRoster.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void selectRoster(ActionEvent event) {
        
        try {
            StackPane newRoot = FXMLLoader.load(getClass().getResource("/substitution/manager/cis152/fxml/SelectRosterScreen.fxml"));

            Scene newScene = new Scene(newRoot, 600, 850);

            Stage currentStage = (Stage) savedRoster.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
