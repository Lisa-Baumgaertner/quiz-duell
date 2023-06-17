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

public class HighscoreController implements Initializable {

    @FXML
    private Button return_button;

    @FXML
    private TextArea highscore_table;

    @FXML
    private TextField highscore_header_player;

    @FXML
    private TextField highscore_header_name;

    @FXML
    private TextArea highscore_body;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String result_highscore = "";
        result_highscore = JavaToDatabase.getResultsHighscore();
        // fill text area with top 5 players with the highest scores across all games they played
        highscore_body.setText(result_highscore);


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
