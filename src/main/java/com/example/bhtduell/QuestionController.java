package com.example.bhtduell;

// imports for timer
import java.util.*;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private int current_state = 0; // this is used to check at how many questions were already displayed to determine when round is over

    private int question_counter = 0; // variable to keep track of question count (so we do not always get the same one)

    private int[] answer_ids = new int[4]; // create array of size 4 to hold the 4 answer ids of the 4 answers for each question
    private Boolean clicked = false; // bool to indicate if a answer field was clicked already (i.e. that no other fields can be clicked anymore bc question already answered)
    private Boolean player1_active = false;
    private Boolean player2_active = false;

    //@FXML
    //private Button load;

    @FXML
    private Button go_final_btn; // button that is present on question answer mask but invisible, we use this to trigger scene change after game is over


    @FXML
    private Label turn_player1;
    @FXML
    private Label turn_player2;

    @FXML
    //private TextField question_field;
    private TextArea question_field;
    //private Label question_field;

    @FXML
    private TextField answer_A;
    @FXML
    private TextField answer_B;
    @FXML
    private TextField answer_C;
    @FXML
    private TextField answer_D;

    @FXML
    private ProgressBar progressBar;

    double progress;

    @FXML
    private Button timer_trigger;

    // this needs to be String otherwise we have problems later on with the vectors => fails with int
    public static String primaryKeyQuestionAnswer="0";
    //public static int primaryKeyQuestion;


    @FXML
    private void init() throws SQLException, IOException {
        // increase round column in table Game_control in Db
        JavaToDatabase.updateControlRound();

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

        for(int i = 0; i <= 10; i++){
            PauseTransition pt = new PauseTransition(Duration.seconds(1));
            pt.setOnFinished(e -> {
                timer_trigger.fire(); // trigger button
            });
            pt.play();


        }

        /*// implement progress bar
        progressBar.setStyle("-fx-accent: red");
        float current_progress = 0f;
        for(int i = 0; i < 10; i++)  {
            current_progress += 0.02;
            progressBar.setProgress(current_progress);
        }*/



//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 0)),
//                new KeyFrame(Duration.minutes(0.3), e-> {
//                    // do anything you need here on completion...
//                    PauseTransition pt = new PauseTransition(Duration.seconds(3));
//                    pt.setOnFinished(event -> {
//                        loadNextQuestion(); // trigger button
//                    });
//                    pt.play();
//                    System.out.println("Minute over");
//
//
//                }, new KeyValue(progressBar.progressProperty(), 1))
//        );
//        timeline.setCycleCount(15);
//        timeline.play();

    }

    @FXML
    public void myTimer(ActionEvent e){
        // set progress to different level of progressbar
        double ii = 0.0;
        ii += 0.1;
        progressBar.setProgress(ii);



    }
    // function to decrease progress in Progress bar -> time running out
    public void decreaseProgress(){

        progressBar.setStyle("-fx-accent: red;");
        progress -= 0.1; // decrease by 0.1 -> by 10%
        progressBar.setProgress(progress);

    }

    // question_counter is increased to get next question from db
    @FXML
    private void loadQuestionAnswer() throws IOException, SQLException {

        //progressBar.setProgress(0.0);
//        timeline.setOnFinished(event -> {
//            PauseTransition wait = new PauseTransition(Duration.seconds(1));
//            wait.setOnFinished((e) -> {
//                /*YOUR METHOD*/
//
//            });
//            });
        // implement progress bar
//        progressBar.setStyle("-fx-accent: red");
//        float current_progress = 0f;
//        for(int i = 0; i < 10; i++)  {
//            current_progress += 0.02;
//            progressBar.setProgress(current_progress);
//        }

        if(current_state <= 6) {
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
            JavaToDatabase.updateControlLastQuestion(question_counter);
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
                List answer_list = JavaToDatabase.getAnswers(question_counter);// result set from query
                Vector answer_id_vec = (Vector) answer_list.get(0); // get vector with answer ids from list
                Vector answer_vec = (Vector) answer_list.get(1); // cast vector with answers text



                if (!answer_vec.isEmpty()) {
                    // now add answer ids from answer_id_vec to array answer_ids
                    // so I can store the answer ids, where index 0 of array equals answer option A and so on
                    // can use this later to check after a click if answer was true ore false
                    System.out.println("answer_ids 1  " + answer_ids);
                    answer_ids[0] = (int) answer_id_vec.get(0);
                    answer_ids[1] = (int) answer_id_vec.get(1);
                    answer_ids[2] = (int) answer_id_vec.get(2);
                    answer_ids[3] = (int) answer_id_vec.get(3);
                    System.out.println("answer_ids 2  " + answer_ids);


                    setWhichPlayerName();
                    current_state += 1;
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

                    // land here if there are no more questions in the database to load -> head to final mask
                    // use PauseTransition to wait before triggering click button on our invisible go_final_button
                    // so we do not immediately skip to game results, but first see how last question was answered
/*
                    PauseTransition pt = new PauseTransition(Duration.seconds(3));
                    pt.setOnFinished(e -> {
                        go_final_btn.fire(); // trigger button
                    });
                    pt.play();
*/
                    //go_final_btn.fire();


                }
                // also set fields to clickable again
                // we do this here so field become clickable after loading new question, else we run into errors
                answer_A.setDisable(false);
                answer_B.setDisable(false);
                answer_C.setDisable(false);
                answer_D.setDisable(false);

            } else {
                // we only land here if no more questions in db
                // here I can start next mask then -> end game mask

                // set global var to 0, because if no more questions in db, and players want to start again we need to start at question 0
                // else error
                Integer temp_question_counter = 0;
                temp_question_counter = Integer.parseInt(primaryKeyQuestionAnswer);
                temp_question_counter= 0;
                primaryKeyQuestionAnswer = Integer.toString(temp_question_counter);

                disableAllFields();
                PauseTransition pt = new PauseTransition(Duration.seconds(3));
                pt.setOnFinished(e -> {
                    go_final_btn.fire(); // trigger button
                });
                pt.play();


            }

            JavaToDatabase.updateControlLastQuestion(question_counter);
        }
        else{
            System.out.println("SOS ");
            // load end mask here because end of round has been reached
            // current state == 6 -> 6 questions have been displayed in total

        }
//        Timeline timeline = new Timeline(
//                //I only use two keyframes instead of three
//                new KeyFrame(Duration.seconds(10), e-> {
//                }, new KeyValue(progressBar.progressProperty(), 1))
//        );
//        timeline.setCycleCount(1);
//        timeline.play();
//        loadNextQuestion();
//        timeline.setOnFinished(event -> {
//            //Main.setPane(questionNumber);
//            //questionNumber++;
//            loadNextQuestion();
//            //The setProgress is added
//            progressBar.setProgress(0.0F);
//            timeline.play();
//        });
        for(int i = 0; i <= 10; i++) {
            PauseTransition pt = new PauseTransition(Duration.seconds(1));
            pt.setOnFinished(e -> {
                timer_trigger.fire(); // trigger button
            });
            pt.play();
        }
//        progressBar.setProgress(0.0F);
    }

    // if answer A is clicked
    @FXML
    private void AClicked(MouseEvent event) throws SQLException, IOException, InterruptedException {
        if (clicked == false) {
            clicked = true; // set to true if one field was clicked, so it is only clickable once, and not possible to change answer
            System.out.println(("Clicked A"));
            // get text from field
            //String txt_a = answer_A.getText();

            // I use answer ID and question ID to identify answer and answer_Id -> to know if answer given is true or false
            // to get asw_ID need to get respective element from array answer_ids (index 0: A, etc...)
            Boolean answerStatus = JavaToDatabase.answerTrueFalse(answer_ids[0], Integer.parseInt(primaryKeyQuestionAnswer));
            //Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_a, primaryKeyQuestion);
            if (answerStatus == true) {
                answer_A.setStyle("-fx-background-color: #70db70"); // set green
                primaryKeyQuestionAnswer = Integer.toString(question_counter);
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), answer_ids[0] ,  answerStatus);

            } else {
                answer_A.setStyle("-fx-background-color: #ff5c33"); // set red
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), answer_ids[0] ,  answerStatus);

            }
            // disable all fields after answer was given
            disableAllFields();
            //progressBar.setProgress(0.0);
            System.out.println("Current State: " + current_state);
            // load the next question for other player
            if(current_state != 6) {
                loadNextQuestion();
            }
            if (current_state == 6){

                // use PauseTransition to wait before triggering click button on our invisible go_final_button
                // so we do not immediately skip to game results, but first see how last question was answered
                PauseTransition pt = new PauseTransition(Duration.seconds(3));
                pt.setOnFinished(e -> {
                    go_final_btn.fire(); // trigger button
                });
                pt.play();

            }
        }
    }

    @FXML
    private void Click_final(ActionEvent event){
        // if answer clicked and nothing comes after, show final mask
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("final.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage window = (Stage) go_final_btn.getScene().getWindow();// typecast to Stage
        window.setScene((new Scene(root)));


    }
    // if answer B is clicked
    @FXML
    private void BClicked(MouseEvent event) {
        if (clicked == false) {
            clicked = true;
            System.out.println(("Clicked B"));
            // get text from field
            //String txt_b = answer_B.getText();
            // access if answer is true or false with asw_ID and qu_ID
            Boolean answerStatus = JavaToDatabase.answerTrueFalse(answer_ids[1], Integer.parseInt(primaryKeyQuestionAnswer));
            //Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_b,primaryKeyQuestion);
            if (answerStatus == true) {
                answer_B.setStyle("-fx-background-color: #70db70");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), answer_ids[1] ,  answerStatus);
            } else {
                answer_B.setStyle("-fx-background-color: #ff5c33");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), answer_ids[1] ,  answerStatus);
            }
            // disable all fields after answer was given
            disableAllFields();
            //progressBar.setProgress(0.0);
            // load the next question for other player
            if(current_state != 6) {
                loadNextQuestion();
            }
            if (current_state == 6){

                //System.out.println("Current State = 6");

                // use PauseTransition to wait before triggering click button on our invisible go_final_button
                // so we do not immediately skip to game results, but first see how last question was answered
                PauseTransition pt = new PauseTransition(Duration.seconds(3));
                pt.setOnFinished(e -> {
                    go_final_btn.fire(); // trigger button
                });
                pt.play();


            }
        }
    }

    // if answer C is clicked
    @FXML
    private void CClicked(MouseEvent event) {
        if (clicked == false) {
            clicked = true;
            System.out.println(("Clicked C"));
            //String txt_c = answer_C.getText();
            // access if answer is true or false with asw_ID and qu_ID
            Boolean answerStatus = JavaToDatabase.answerTrueFalse(answer_ids[2], Integer.parseInt(primaryKeyQuestionAnswer));
            //Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_c, primaryKeyQuestion);
            if (answerStatus == true) {
                answer_C.setStyle("-fx-background-color: #70db70");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), answer_ids[2] ,  answerStatus);
            } else {
                answer_C.setStyle("-fx-background-color: #ff5c33");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), answer_ids[2] ,  answerStatus);
            }
            // disable all fields after answer was given
            disableAllFields();
            //progressBar.setProgress(0.0);
            // load the next question for other player
            if(current_state != 6) {
                loadNextQuestion();
            }
            if (current_state == 6){

                //System.out.println("Current State = 6");

                // use PauseTransition to wait before triggering click button on our invisible go_final_button
                // so we do not immediately skip to game results, but first see how last question was answered
                PauseTransition pt = new PauseTransition(Duration.seconds(3));
                pt.setOnFinished(e -> {
                    go_final_btn.fire(); // trigger button
                });
                pt.play();


            }
        }
    }

    // if answer D is clicked
    @FXML
    private void DClicked(MouseEvent event) {
        if (clicked == false) {
            clicked = true;
            System.out.println(("Clicked D"));
            //String txt_d = answer_D.getText();
            // access if answer is true or false with asw_ID and qu_ID
            Boolean answerStatus = JavaToDatabase.answerTrueFalse(answer_ids[3], Integer.parseInt(primaryKeyQuestionAnswer));
            //Boolean answerStatus = JavaToDatabase.answerTrueFalse(txt_d, primaryKeyQuestion);
            if (answerStatus == true) {
                answer_D.setStyle("-fx-background-color: #70db70");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), answer_ids[3] ,  answerStatus);
            } else {
                answer_D.setStyle("-fx-background-color: #ff5c33");
                String activeUser = getActiveUser();
                JavaToDatabase.trackScore(activeUser, Integer.valueOf(primaryKeyQuestionAnswer), answer_ids[3] ,  answerStatus);
            }
            // disable all fields after answer was given
            disableAllFields();
            //progressBar.setProgress(0.0);
            // load the next question for other player
            if(current_state != 6) {
                loadNextQuestion();
            }
            if (current_state == 6){

                //System.out.println("Current State = 6");

                // use PauseTransition to wait before triggering click button on our invisible go_final_button
                // so we do not immediately skip to game results, but first see how last question was answered
                PauseTransition pt = new PauseTransition(Duration.seconds(3));
                pt.setOnFinished(e -> {
                    go_final_btn.fire(); // trigger button
                });
                pt.play();


            }
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
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
                   throw new RuntimeException(e);
               }
               try {
                   // call next question

                   loadQuestionAnswer();
               } catch (IOException e) {
                   e.printStackTrace();
                   throw new RuntimeException(e);
               } catch (SQLException e) {
                   e.printStackTrace();
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
        System.out.println("IN INITIALIZE!!");
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
