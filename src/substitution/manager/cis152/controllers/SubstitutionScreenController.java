package substitution.manager.cis152.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
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
public class SubstitutionScreenController implements Initializable {

    private Court onCourt;
    private Bench onBench;
    private Roster roster;
    private GameSetup gameSetup;

    private int prevSubTime;
    private int currSubTime;

    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    VBox onCourtContainer;
    @FXML
    VBox onBenchContainer;
    @FXML
    ScrollPane scrollPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initData(Court onCourt, Bench onBench, GameSetup gameSetup, Roster roster) {
        this.onCourt = onCourt;
        this.onBench = onBench;
        this.gameSetup = gameSetup;
        this.roster = roster;

        getGameTime();
    }

    /*
    Get current time on the game clock
    Do math to get the amount of time the players were in
     */
    private void getGameTime() {
        if (onCourtContainer != null) {
            onCourtContainer.getChildren().clear();
        }

        scrollPane.setVisible(false);

        Label qLabel = new Label("Current Quarter:");
        Spinner qtrSpinner = new Spinner(gameSetup.getCurrQuarter(), gameSetup.getNumQuarters(), 1);
        qtrSpinner.setPrefSize(60, 25);

        VBox gameTimeVBOX = new VBox(20);
        gameTimeVBOX.setAlignment(Pos.CENTER);

        HBox minsHBOX = new HBox(10);
        minsHBOX.setAlignment(Pos.CENTER);

        TextField mins = new TextField();
        mins.setPromptText("Minutes on the Game Clock");
        mins.setPrefWidth(165);
        Label mLabel = new Label("Minutes");
        minsHBOX.getChildren().addAll(mins, mLabel);

        HBox secsHBOX = new HBox(10);
        secsHBOX.setAlignment(Pos.CENTER);

        TextField secs = new TextField();
        secs.setPromptText("Seconds on the Game Clock");
        secs.setPrefWidth(165);
        Label sLabel = new Label("Seconds");
        secsHBOX.getChildren().addAll(secs, sLabel);

        gameTimeVBOX.getChildren().addAll(minsHBOX, secsHBOX);
        
        Label clock = new Label("Game Clock Shows: ");
        mins.setOnKeyReleased(e -> updateClock(clock, mins, secs));
        secs.setOnKeyReleased(e -> updateClock(clock, mins, secs));

        HBox buttonHBOX = new HBox(50);
        Button cancel = new Button("Cancel");
        Button submit = new Button("Submit");
        buttonHBOX.setAlignment(Pos.BASELINE_CENTER);
        buttonHBOX.getChildren().addAll(cancel, submit);

        submit.setOnAction(e -> setGameTime(mins.getText(), secs.getText(), qtrSpinner.getValue().toString()));
        cancel.setOnAction(e -> backToGameScreen());

        onCourtContainer.getChildren().addAll(qLabel, qtrSpinner, gameTimeVBOX, clock, buttonHBOX);
    }
    
    private void updateClock(Label clock, TextField mins, TextField secs) {
        int seconds = formatSecs(secs);
        if (seconds < 10) {
            clock.setText("Game Clock Shows: " + formatMins(mins) + ":0" + formatSecs(secs));
        } else {
            clock.setText("Game Clock Shows: " + formatMins(mins) + ":" + formatSecs(secs));
        }
        
    }
    
    private int formatMins(TextField mins) {
        int minutes;
        try{
            if (mins.getText().isEmpty()) {
                minutes = 0;
                
                return minutes;
            } else {
                try{
                    minutes = Integer.parseInt(mins.getText());
                    
                    if (minutes > gameSetup.getQuarterLength()) {
                        errorAlert.setContentText("Minutes Cannot Be Larger Than The Quarter Length You Entered.");
                        errorAlert.show();
                    } else {
                        return minutes;
                    }
                } catch(NumberFormatException nf) {
                    errorAlert.setContentText("Only Enter Numeric Values.");
                    errorAlert.show();                   
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    
    private int formatSecs(TextField secs) {
     int seconds;
     try{
         if (secs.getText().isEmpty()) {
             seconds = 0;
             
             return seconds;
         } else {
             try {
                 seconds = Integer.parseInt(secs.getText());
                 
                 if (seconds > 59) {
                     errorAlert.setContentText("Seconds Cannot Be Greater Than 59.");
                     errorAlert.show();
                 } else {
                     return seconds;
                 }
             } catch(NumberFormatException nf) {
                 errorAlert.setContentText("Only Enter Numeric Values.");
                 errorAlert.show();    
             }
         }
     }catch(Exception e){
            e.printStackTrace();
     }
     return 0;
    }

    private void setGameTime(String minutes, String seconds, String quarter) {
        boolean goodValue = false;
        try {
            // Validate the input is numbers
            int mins = Integer.parseInt(minutes);
            int secs = Integer.parseInt(seconds);
            int qtr = Integer.parseInt(quarter);

            // Ensure seconds are not greater than 59
            if (secs > 59) {
                errorAlert.setContentText("Seconds cannot be more than 59.");
                errorAlert.show();

                // Ensure minutes are not greater than the quarter length
            } else if (mins > gameSetup.getQuarterLength()) {
                errorAlert.setContentText("Minutes cannot be greater than the quarter length you entered.");
                errorAlert.show();
            } else if (qtr < gameSetup.getCurrQuarter()) {
                errorAlert.setContentText("It is no longer Quarter " + qtr + ".");
                errorAlert.show();
            } else {
                goodValue = true;
                // Multiply seconds by quarters left
                // Convert to all seconds
                int subTime = secsLeftInGame(mins, secs, qtr);
                prevSubTime = gameSetup.getLastSubTime();

                // Add to the subTimes list
                gameSetup.addSubTime(subTime);
                currSubTime = gameSetup.getLastSubTime();

                // Update players on the court for mins in
                for (Player player : onCourt.getOnCourt()) {
                    player.updateMinsPlayed((prevSubTime - currSubTime) / 60.0);
                }

            }
            if (goodValue) {
                populateView();
                gameSetup.setCurrQuarter(qtr);
            }
        } catch (NumberFormatException nf) {
            errorAlert.setContentText("Must only enter whole numbers.");
            errorAlert.show();
        }

    }

    private void backToGameScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/substitution/manager/cis152/fxml/GameScreen.fxml"));
            StackPane newRoot = loader.load();

            GameScreenController gameController = loader.getController();
            gameController.initData(roster, gameSetup, onCourt, onBench);

            Scene newScene = new Scene(newRoot, 600, 850);

            Stage currentStage = (Stage) onCourtContainer.getScene().getWindow();

            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int secsLeftInGame(int mins, int secs, int qtr) {
        return (gameSetup.getGameSecs() - (gameSetup.getQuarterLengthInSecs() - (mins * 60 + secs))) - ((qtr - 1) * gameSetup.getQuarterLengthInSecs());

    }

    /*
    Show list of players on the court with buttons next to their name to sub out
    Once button is pressed, show new screen that shows the list of bench players
    Then make the sub
     */
    private void populateView() {
        if (onCourtContainer != null) {
            onCourtContainer.getChildren().clear();
        }
        if (onBenchContainer != null) {
            onBenchContainer.getChildren().clear();
        }

        scrollPane.setVisible(false);
        onCourtContainer.setVisible(true);

        for (Player player : onCourt.getOnCourt()) {
            HBox playerHBOX = new HBox(10);
            playerHBOX.setAlignment(Pos.CENTER);
            
            Label nameLabel = new Label(player.getPlayerName());
            Label minutesLabel = new Label("Minutes: " + player.getMinsPlayed());
            Button subButton = new Button("Sub Out");
            
            subButton.setOnAction(e -> initSub(player));

            playerHBOX.getChildren().addAll(nameLabel, minutesLabel, subButton);
            onCourtContainer.getChildren().add(playerHBOX);
        }
        
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> backToGameScreen());
        onCourtContainer.getChildren().add(cancel);
    }

    private void initSub(Player subOut) {
        if (onCourtContainer != null) {
            onCourtContainer.getChildren().clear();
        }
        if (onBenchContainer != null) {
            onBenchContainer.getChildren().clear();
        }

        onCourtContainer.setVisible(false);
        scrollPane.setVisible(true);
        

        Iterator<Player> queueIterator = onBench.getBench().iterator();
        while (queueIterator.hasNext()) {
            Player player = queueIterator.next();

            HBox playerHBOX = new HBox(10);
            playerHBOX.setAlignment(Pos.CENTER);

            Label nameLabel = new Label(player.getPlayerName());
            Label minutesLabel = new Label("Minutes: " + player.getMinsPlayed());

            Button subButton = new Button("Sub In");
            subButton.setOnAction(e -> substitutePlayers(player, subOut));

            playerHBOX.getChildren().addAll(nameLabel, minutesLabel, subButton);
            onBenchContainer.getChildren().add(playerHBOX);
        }
    }

    /*
    Pass in a player on the court to be subbed out
    Remove that player from the LinkedList
    
    Pass in the bench player to be subbed in
    Add them to the onCourt LinkedList
    
    Add the bench player to be subbed in onto the stack
    Add the player being subbed out into the queue
     */
    private void substitutePlayers(Player benchIn, Player courtOff) {

        // Confirm the players are in the correct spots
        // Remove and replace players
        if (onCourt.getOnCourt().contains(courtOff) && onBench.getBench().contains(benchIn)) {  // If Exists
            onCourt.getOnCourt().remove(courtOff);
            onCourt.getOnCourt().add(benchIn);

            // Iterate through queue and remove player to sub in
            // Add subbed out player to queue
            Iterator<Player> queueIterator = onBench.getBench().iterator();
            while (queueIterator.hasNext()) {
                Player player = queueIterator.next();
                if (player.equals(benchIn)) {
                    onBench.getBench().remove(player);
                    onBench.add(courtOff);
                    break;
                }
            }
            populateView();
        }
    }
}
