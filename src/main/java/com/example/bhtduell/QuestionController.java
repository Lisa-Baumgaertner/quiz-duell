package com.example.bhtduell;

// imports for timer
import java.util.*;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

// global variables
import static com.example.bhtduell.BuzzerController.A_clicked;
import static com.example.bhtduell.BuzzerController.K_clicked;
import static com.example.bhtduell.Controller.player1_name;
import static com.example.bhtduell.Controller.player2_name;


public class QuestionController implements Initializable {
    private int question_counter = 0; // variable to keep track of question count (so we do not always get the same one)
    private Boolean clicked = false; // bool to indicate if a answer field was clicked already (i.e. that no other fields can be clicked anymore bc question already answered)
    private Boolean player1_active = false;
    private Boolean player2_active = false;

    //@FXML
    //private Button load;


    @FXML
    private Label turn_player1;
    @FXML
    private Label turn_player2;

    @FXML
    private TextField question_field;
    //private Label question_field;

    @FXML
    private TextField answer_A;
    @FXML
    private TextField answer_B;
    @FXML
    private TextField answer_C;
    @FXML
    private TextField answer_D;

    // this needs to be String otherwise we have problems later on with the vectors => fails with int
    public static String primaryKeyQuestionAnswer="0";
    //public static int primaryKeyQuestion;

    @FXML
    private void init() throws SQLException, IOException {
        System.out.println(("in init !"));

        loadQuestionAnswer(); // we begin with 0 here to get first question
        if (A_clicked == true){
            turn_player1.setText(player1_name);
            turn_player2.setText(player2_name);
            turn_player2.setVisible(false);
            turn_player1.setVisible(true);
            player1_active = true;
            player2_active = false;
        }
        if (K_clicked == true){
            turn_player2.setText(player2_name);
            turn_player1.setText(player1_name);
            turn_player1.setVisible(false);
            turn_player2.setVisible(true);
            player2_active = true;
            player1_active = false;
        }

    }

    // question_counter is increased to get next question from db
    @FXML
    private void loadQuestionAnswer() throws IOException, SQLException {
        Boolean noData; // bool to put result into from getQuestion function -> do we get data from db or not (if it is last question we don't)

        clicked = false; // reset this here to false, so after loading new question fields are clickable again

        // reset colours so they are 'normal' after reload of next question => else it stays green or red after click
        answer_A.setStyle("-fx-background-color: #ffefd6");
        answer_B.setStyle("-fx-background-color: #ffefd6");
        answer_C.setStyle("-fx-background-color: #ffefd6");
        answer_D.setStyle("-fx-background-color: #ffefd6");

         /*if((player1_active != false) || (player2_active != false)) {
            if (player1_active == true) {
                // now player2 active because previously player 1 active
                player2_active = true;
                turn_player2.setText(player2_name);
                turn_player2.setVisible(true);
                turn_player1.setVisible(false);
                player1_active = false;
                System.out.println("player2 name " + player2_name);

            }
            if (player2_active == true) {
                // now player1 active because previously player 2 active
                player1_active = true;
                turn_player1.setText(player1_name);
                turn_player1.setVisible(true);
                turn_player2.setVisible(false);
                player2_active = false;
                System.out.println("player1 name " + player1_name);

            }
        }*/

        // typecast from String to int
        question_counter = Integer.parseInt(primaryKeyQuestionAnswer);

        System.out.println(primaryKeyQuestionAnswer + "  primary k q a");

        // set up question text field
        //Vector question_vec = JavaToDatabase.getQuestion(question_counter); // result set from query
        List returnList = JavaToDatabase.getQuestion(question_counter); // result set from query
        // now get from List whether we get data from db -> boolean rsIsEmpty
        noData = (Boolean) returnList.get(0);
        if (noData == false) {
            // now we know that there is still a question to display
            // we get the Vector from returnList
            Vector question_vec = (Vector) returnList.get(1);
            // element at index 0 of question_vec is the qu_ID from DB
            primaryKeyQuestionAnswer = (String) question_vec.get(0);
            //primaryKeyQuestion = Integer.parseInt(question_vec.get(0));
            System.out.println(primaryKeyQuestionAnswer);

            // set TextField for question
            String qu = (String) question_vec.get(1);
            System.out.println("qu  " + qu);
            question_field.setText("");
            question_field.setText(qu); // typecast to string
            //question_field.setStyle("-fx-focus-color: transparent;");


            //System.out.println("answer_vec  " + answer_vec.toString());
            // check if vector is not empty

            // set up answer text fields
            Vector answer_vec = JavaToDatabase.getAnswers(question_counter);// result set from query
            if (!answer_vec.isEmpty()) {
                setWhichPlayerName();
                System.out.println("vector " + answer_vec.toString());
                // set TextField for answer A
                String asw_a = (String) answer_vec.get(0);
                answer_A.setText(asw_a);
                // set TextField for answer B
                String asw_b = (String) answer_vec.get(1);
                answer_B.setText(asw_b);
                // set TextField for answer C
                String asw_c = (String) answer_vec.get(2);
                answer_C.setText(asw_c);
                // set TextField for answer D
                String asw_d = (String) answer_vec.get(3);
                answer_D.setText(asw_d);

                System.out.println("Answer a disabled: " + answer_A.isDisabled());
            } else {
                System.out.println("vector " + answer_vec.toString());
                //throw new RuntimeException(e);
                System.out.println("no more answers to laod in database: " + question_counter);
            }
            // also set fields to clickable again
            // we do this here so field become clickable after loading new question, else we run into errors
            answer_A.setDisable(false);
            answer_B.setDisable(false);
            answer_C.setDisable(false);
            answer_D.setDisable(false);

        }


        else{
            // we only land here if no more questions in db
            // here I can start next mask then -> end game mask
            // for now we print
            System.out.println("No more questions to display: " + question_counter);
            disableAllFields();
        }


    }

    // if answer A is clicked
    @FXML
    private void AClicked(MouseEvent event) throws SQLException, IOException, InterruptedException {
        if (clicked == false) {
            clicked = true; // set to true if one field was clicked, so it is only clickable once, and not possible to change answer
            System.out.println(("Clicked A"));
            // get text from field
            String txt_a = answer_A.getText();
            //System.out.println(txt_a);
            // then get primaryKeyQuestion variable bc it contains primary key of question in DB
            //primaryKeyQuestion = String.valueOf(Integer.getInteger(primaryKeyQuestion));
            System.out.println("primary key question answer  " + primaryKeyQuestionAnswer);
            // we use answer text and question ID to identify answer and answer_Id -> to know if answer given is true or false
            // we need to trace ack to question with just question_Id and text of clicked answer
            Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_a, Integer.parseInt(primaryKeyQuestionAnswer));
            //Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_a, primaryKeyQuestion);
            if (answerStatus == true) {
                answer_A.setStyle("-fx-background-color: #70db70"); // set green
                primaryKeyQuestionAnswer = Integer.toString(question_counter);
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), txt_a ,  answerStatus);

            } else {
                answer_A.setStyle("-fx-background-color: #ff5c33"); // set red
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), txt_a ,  answerStatus);

            }
            // disable all fields after answer was given
            disableAllFields();
            // load the next question for other player
            loadNextQuestion();
        }
    }

    // if answer B is clicked
    @FXML
    private void BClicked(MouseEvent event) {
        if (clicked == false) {
            clicked = true;
            System.out.println(("Clicked B"));
            // get text from field
            String txt_b = answer_B.getText();
            // then get primaryKeyQuestion variable bc it contains primary key of question in DB
            Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_b, Integer.parseInt(primaryKeyQuestionAnswer));
            //Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_b,primaryKeyQuestion);
            if (answerStatus == true) {
                answer_B.setStyle("-fx-background-color: #70db70");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), txt_b ,  answerStatus);
            } else {
                answer_B.setStyle("-fx-background-color: #ff5c33");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), txt_b ,  answerStatus);
            }
            // disable all fields after answer was given
            disableAllFields();
            // load the next question for other player
            loadNextQuestion();
        }
    }

    // if answer C is clicked
    @FXML
    private void CClicked(MouseEvent event) {
        if (clicked == false) {
            clicked = true;
            System.out.println(("Clicked C"));
            String txt_c = answer_C.getText();
            // then get primaryKeyQuestion variable bc it contains primary key of question in DB
            Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_c, Integer.parseInt(primaryKeyQuestionAnswer));
            //Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_c, primaryKeyQuestion);
            if (answerStatus == true) {
                answer_C.setStyle("-fx-background-color: #70db70");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), txt_c ,  answerStatus);
            } else {
                answer_C.setStyle("-fx-background-color: #ff5c33");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), txt_c ,  answerStatus);
            }
            // disable all fields after answer was given
            disableAllFields();
            // load the next question for other player
            loadNextQuestion();
        }
    }

    // if answer D is clicked
    @FXML
    private void DClicked(MouseEvent event) {
        if (clicked == false) {
            clicked = true;
            System.out.println(("Clicked D"));
            String txt_d = answer_D.getText();
            // then get primaryKeyQuestion variable bc it contains primary key of question in DB
            Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_d, Integer.parseInt(primaryKeyQuestionAnswer));
            //Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_d, primaryKeyQuestion);
            if (answerStatus == true) {
                answer_D.setStyle("-fx-background-color: #70db70");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), txt_d ,  answerStatus);
            } else {
                answer_D.setStyle("-fx-background-color: #ff5c33");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), txt_d ,  answerStatus);
            }
            // disable all fields after answer was given
            disableAllFields();
            // load the next question for other player
            loadNextQuestion();
        }
    }


    // function to disable all answer fields
    private void disableAllFields(){
        answer_A.setDisable(true);
        answer_B.setDisable(true);
        answer_C.setDisable(true);
        answer_D.setDisable(true);

    }


    // function to get next question + answers loaded after previous answer was given
   private void loadNextQuestion(){
        // thread to schedule task in background
       // we need this to display if answer is correct/false and then load next question,
       // without this correct/false would not be displayed correctly (would always stay on first clicked)
       Timer t = new Timer(); // create new timer
       t.schedule(new TimerTask() {
           @Override
           public void run() {
               // update question counter
               question_counter +=1;
               // convert to string (our loadQuestionAnswer function needs it as string)
               // we get next question with this id
               primaryKeyQuestionAnswer = Integer.toString(question_counter);

               try {
                   Thread.sleep(2500);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               try {
                   // call next question

                   loadQuestionAnswer();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               } catch (SQLException e) {
                   throw new RuntimeException(e);
               }
               t.cancel();
           }
       }, 1000);




    }

    // function to set which player's turn it is currently -> this is displayed on mask
    public void setWhichPlayerName(){
        // in init(), we set which player is the first player based on the click competition -> this player's field is set visible
        // we can now ask which field is visible
        if(turn_player1.isVisible() == true){
            turn_player2.setVisible(true);
            turn_player2.setText(player2_name);
            turn_player1.setVisible(false);


        } else{
            // in this case player2 did begin the game with first question
            turn_player1.setVisible(true);
            turn_player1.setText(player1_name);
            turn_player2.setVisible(false);

        }

    }

    // function that returns which player is currently visible, i.e. which player's turn it currently is
    public String getActiveUser(){
        // we can now ask which field is visible
        if(turn_player1.isVisible() == true){
            return player1_name;

        }
        else{
            return player2_name;
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // create timer to switch to next question => so we get green/red color after click BEFORE loading new question
/*public class MyTimerTask extends TimerTask {


    //@Override
//    public void run() {
//        System.out.println("Timer task started at:" + new Date());
//        completeTask();
//        System.out.println("Timer task finished at:" + new Date());
//    }

    @Override
    public void run() {
        System.out.println("Timer task started at:" + new Date());
        completeTask();
        System.out.println("Timer task finished at:" + new Date());
        //t.cancel();
    }
    }, 10000);

    private void completeTask() {
        try {

            question_counter +=1;
            primaryKeyQuestionAnswer = Integer.toString(question_counter);
            //TimeUnit.SECONDS.sleep(5);
            Thread.sleep(3000);
            loadQuestionAnswer();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}*/
}
