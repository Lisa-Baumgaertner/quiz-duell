package com.example.bhtduell;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.bhtduell.LoginController.player1_name;
import static com.example.bhtduell.LoginController.player2_name;

// Initializable => to automatically load
public class BuzzerController implements Initializable {
    @FXML
    private Label press_player1;
    @FXML
    private Label press_player2;

    @FXML
    private Button keypress_a;

    @FXML
    private Button keypress_k;
    // click is handled with invisible buttons !!!

    // global variables to check if key A or K was pressed => further use in questionController
    public static Boolean A_clicked = false;
    public static Boolean K_clicked = false;


    @FXML
    private void init() {
        press_player1.setText(player1_name);
        press_player2.setText(player2_name);
    }


    @FXML
    private void whichKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.A) {
            System.out.println("A was pressed");
            A_clicked = true;
            // immediately change scene here
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("question.fxml"));

                //root.setFocusTraversable(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage window = (Stage) keypress_a.getScene().getWindow(); // typecast to Stage
            window.setScene((new Scene(root)));

            //window.setTitle("Let's see who's faster :)");

        }
        if (keyEvent.getCode() == KeyCode.K) {
            K_clicked = true;
            System.out.println("K was pressed");
            // immediately change scene here
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("question.fxml"));

                //root.setFocusTraversable(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage window = (Stage) keypress_k.getScene().getWindow(); // typecast to Stage
            window.setScene((new Scene(root)));

            window.setTitle("Let's see who's faster :)");
        }
        System.out.println("here");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();

    }
}

