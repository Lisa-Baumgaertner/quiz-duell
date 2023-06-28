package com.example.bhtduell;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class HighscoreController implements Initializable {

    @FXML
    private Button return_button;

    @FXML
    private TextArea highscore_table;

    @FXML
    private TextField highscore_header_player;

    @FXML
    private TextArea player_body;

    @FXML
    private TextArea highscore_body;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //String result_highscore = "";
        List result_highscore = new ArrayList();
        //String [] temp_array = new String[2];
        result_highscore = JavaToDatabase.getResultsHighscore();
        Vector player_vec = (Vector) result_highscore.get(0);
        Vector score_vec = (Vector) result_highscore.get(1);
        // element at index 0 of question_vec is the qu_ID from DB
        for(int i = 0; i < player_vec.size(); i++) {
            String fill_player = (String) player_vec.get(i);
            String fill_score = (String) score_vec.get(i);
            player_body.appendText(fill_player + "\n");
            highscore_body.appendText(fill_score + "\n");
        }

        //System.out.println("result highscore list" + result_highscore);

//        for (int i = 0; i < result_highscore.size()-1; i++){
//            temp_array = (String[]) result_highscore.get(i);
//            System.out.println(temp_array[0]);
//            System.out.println(temp_array[1]);
//            player_body.appendText(temp_array[0]);
//            highscore_body.appendText(temp_array[1]);
//
//
//        }




        // need to split result_highscore string on |
        //String[] tokens=result_highscore.split("|");
        //System.out.println("tokens " + tokens);
        // fill text area with top 5 players with the highest scores across all games they played



    }


    @FXML
    public void returnToFinal(ActionEvent event){
        // on click return button -> return to previous stage
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("final.fxml"));
            //root.setFocusTraversable(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage window = (Stage) return_button.getScene().getWindow();// typecast to Stage
        window.setScene((new Scene(root)));

    }


}
