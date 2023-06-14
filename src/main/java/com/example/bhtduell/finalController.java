package com.example.bhtduell;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
}
