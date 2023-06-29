package com.example.bhtduell;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.bhtduell.QuestionController.primaryKeyQuestionAnswer;
public class LoginController implements Initializable {

    // two global variables for the player names => later use in masks (e.g. "buzzer" & questions)
    public static String player1_name = "";
    public static String player2_name = "";
    @FXML
    private Label welcomeText;

    @FXML
    private Label player1_error;
    @FXML
    private Label player2_error;
    @FXML
    private Label successful_login;
    @FXML
    private ImageView login_image_1;
    @FXML
    private ImageView login_image_2;

    @FXML
    private TextField username_1;
    @FXML
    private TextField username_2;
    @FXML
    private Button continue_button;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if ((!player1_name.isEmpty()) && (!player2_name.isEmpty())){
            username_1.setText(player1_name);
            username_2.setText(player2_name);
            // now set question counter higher
            // because we do not want the same questions as in the previous round of the game
            // use global var primaryKeyQuestionAnswer, bc with this we later get questions from db, increase by 1
            // var is a string, needs typecasting first
            Integer temp_question_counter = 0;
            temp_question_counter = Integer.parseInt(primaryKeyQuestionAnswer);
            temp_question_counter+= 1;
            primaryKeyQuestionAnswer = Integer.toString(temp_question_counter);

        }

    }

    @FXML
    // function called when login button is pressed
    public void handle(ActionEvent event) throws IOException, SQLException {
        player1_error.setText("");
        player2_error.setText("");
        String player1 = username_1.getText();
        String player2 = username_2.getText();
        // we want to check if fields are empty => not allowed
        if ((player1.isEmpty()) && (!player2.isEmpty())) {
            System.out.println("Empty player1");
            player1_error.setText("Empty player1");
        }
        if ((!player1.isEmpty()) && (player2.isEmpty())) {
            System.out.println("Empty player2");
            player2_error.setText("Empty player2");
        }
        if ((player1.isEmpty()) && (player2.isEmpty())) {
            System.out.println("Both are empty");
            player1_error.setText("Empty");
            player2_error.setText("Empty");
        }

        if ((!player1.isEmpty()) && (!player2.isEmpty())) {
            player1_name = player1;
            player2_name = player2;
            Boolean player_exists = false;
            // check if player1_name is already in DB or not
            // if not then write him into DB, else don't write to DB
            // get boolean
            player_exists = JavaToDatabase.verifyPlayerNameExists(player1_name);
            System.out.println("player 1 exists  " + player_exists);
            if (player_exists == false){
                JavaToDatabase.writePlayerToDB(player1_name);

            }

            player_exists = JavaToDatabase.verifyPlayerNameExists(player2_name);
            System.out.println("player 2 exists  " + player_exists);
            if (player_exists == false){
                JavaToDatabase.writePlayerToDB(player2_name);
            }
            successful_login.setText("Successfully logged in as " + player1 + " and " + player2);

            // change scene here
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("begin_buzzer.fxml"));
                root.setFocusTraversable(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage window = (Stage) continue_button.getScene().getWindow(); // typecast to Stage
            window.setScene((new Scene(root)));

            window.setTitle("Quizz duell");
        }

    }


}