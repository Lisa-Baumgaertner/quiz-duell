package com.example.bhtduell;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class finalController implements Initializable {

    @FXML
    private Label final_score;

    @FXML
    private Label winner_text;

    @FXML
    private Label winner_label;

    @FXML
    private Label loser_label;

    @FXML
    private Button highscore_button;

    @FXML
    private Button newgame_button;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Vector winnerVec = new Vector();
        winnerVec = JavaToDatabase.getGameResult();
        String winnerName = (String) winnerVec.get(0);
        String winnerScore = (String) winnerVec.get(1);
        String loserName = (String) winnerVec.get(2);
        String loserScore = (String) winnerVec.get(3);

        if (Integer.parseInt(winnerScore) > Integer.parseInt(loserScore)){
            final_score.setText(winnerScore + " : " +  loserScore);
            winner_text.setText("Congratulations, " + winnerName + " you won!");
            winner_label.setText(winnerName);
            loser_label.setText(loserName);

        }
        else{
            final_score.setText(winnerScore + " : " +  loserScore);
            winner_text.setText("You both tied!");
            winner_label.setText(winnerName);
            loser_label.setText(loserName);
        }


    }

    // function to change stage to highscore display if button highscore is clicked
    @FXML
    private void highscoreClicked(ActionEvent event) {
        Parent root = null;
        try {
            //System.out.println("Click final 2");
            root = FXMLLoader.load(getClass().getResource("highscore.fxml"));
            //System.out.println("Click final 3");
            //root.setFocusTraversable(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Click final 4");
        Stage window = (Stage) highscore_button.getScene().getWindow();// typecast to Stage
        window.setScene((new Scene(root)));

    }

    // function to begin new game if button new game is clicked
    @FXML
    public void newGameClicked(ActionEvent event){
        // on click new game button -> begin new game
        Parent root = null;
        try {
            //System.out.println("Click final 2");
            root = FXMLLoader.load(getClass().getResource("login.fxml"));
            //System.out.println("Click final 3");
            //root.setFocusTraversable(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("Click final 4");
        Stage window = (Stage) highscore_button.getScene().getWindow();// typecast to Stage
        window.setScene((new Scene(root)));

        // try and set player names




    }





}
