package com.example.bhtduell;

import java.sql.*; //Any source file that uses JDBC needs to import the java.sql package
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JavaToDatabase {


    // Heike's function
    // to check if username already exists in database
    public static Boolean verifyPlayerNameExists(String username) {

        String playername = username;
        Boolean status = true;

        // the query
        String query_verifyName = "SELECT EXISTS ( SELECT * FROM game.\"Player\" WHERE \"player_username\"=\'" +
                playername + "\') AS exists;";
        System.out.println("QUERY" + query_verifyName);

        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()) {
             // Get answers from DB
             PreparedStatement prepared = connection.prepareStatement(query_verifyName);{
                // if exists return true else false as db vector
                ResultSet rs = prepared.executeQuery();
                while (rs.next()) {
                    status = rs.getBoolean(1);

                }
                rs.close();
                prepared.close();
            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
            return null;
        }

        return status;
    }

    public static void writePlayerToDB(String username) throws SQLException {

        String playername = username;

        // the query
        String query = "INSERT INTO game.\"Player\" (player_username) VALUES (?)";

        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()) {

            PreparedStatement prepared = connection.prepareStatement(query);
            {
                // for player1
                prepared.setString(1, playername);
                prepared.executeUpdate();

                System.out.println("Action was successful!");

            }
            prepared.close();
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
        }
    }

    public static <Obj> List<Obj> getQuestion(int question_counter) {
        // create List to return
        // this will then contain Vector and boolean
        List<Obj> resultList = new ArrayList<Obj>();

        // boolean to check if ResultSet is empty or not, i.e. if data comes back from db
        Boolean rsIsEmpty;

        String query_question = "SELECT \"qu_ID\", \"qu_text\" FROM game.\"Questions\"\n" +
                "WHERE \"qu_ID\"=?;";
        //System.out.println(query_question);
        Vector questionVector;
        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()) {
            // Get question from DB
            PreparedStatement prepared = connection.prepareStatement(query_question);
            {
                // get question and answer text from db
                // question_counter is in this case also id of question in database
                prepared.setInt(1, question_counter); // one is the question mark in query
                ResultSet rs = prepared.executeQuery();

                // Vector class implements a growable array of objects
                // we use it to add results from ResultSet to it => the results from db query
                // because ResultSet is difficult to access after
                // Vector for question
                questionVector = new Vector();

                rsIsEmpty = true;
                while (rs.next()) {
                    rsIsEmpty = false; // while in here ResultSet is not empty
                    // add column w question text to vector
                    questionVector.addElement(new String(rs.getString(1)));
                    questionVector.addElement(new String(rs.getString(2)));

                }
                resultList.add(0, (Obj) rsIsEmpty);
                resultList.add(1, (Obj) questionVector);
                rs.close();
                prepared.close();
                return resultList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public static <Obj> List<Obj> getAnswers ( int qu_counter){

        // get answers from DB
        List<Obj> aswResultList = new ArrayList<Obj>(); // list to store the two vectors in

        String query_answer = "SELECT \"asw_ID\", \"asw_text\" FROM game.\"Answers\"\n" +
                "WHERE \"asw_qu_ID\"=?;";
        System.out.println(query_answer);
        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             // Get answers from DB
             PreparedStatement prepared = connection.prepareStatement(query_answer);{
                // get answer text from db
                // question_counter is in this case also id of question in database
                prepared.setInt(1, qu_counter); // one is the question mark in query
                ResultSet rs = prepared.executeQuery();

                // Vector class implements a growable array of objects
                // we use it to add results from ResultSet to it => the results from db query
                // because ResultSet is difficult to access after
                // Vector for question
                Vector answerVector = new Vector();
                Vector answerIDVector = new Vector();
                while (rs.next()) {
                    // add column w answer ids to vector
                    answerIDVector.addElement(Integer.parseInt(rs.getString(1)));
                    // add column w question text to vector
                    answerVector.addElement(new String(rs.getString(2)));

                }

                aswResultList.add(0, (Obj) answerIDVector);
                aswResultList.add(1, (Obj) answerVector);

                rs.close();
                prepared.close();


                return aswResultList;

            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
            return null;

        }
    }

    public static Boolean answerTrueFalse(int asw_id, int asw_qu_id){
        // get answers from DB
        String query_bool = "SELECT \"asw_is_correct\" FROM game.\"Answers\"\n" +
                "WHERE \"asw_ID\"=? AND \"asw_qu_ID\"=?;";
        //System.out.println(query_bool);
        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             // Get answers from DB
             PreparedStatement prepared = connection.prepareStatement(query_bool);{
                // get answer text from db via asw_id (primary key)
                // question_counter is in this case also id of question in database
                prepared.setInt(1, asw_id); // one is the question mark in query
                prepared.setInt(2, asw_qu_id); // one is the question mark in query
                ResultSet rs = prepared.executeQuery();

                // Vector class implements a growable array of objects
                // we use it to add results from ResultSet to it => the results from db query
                // because ResultSet is difficult to access after
                // Vector for question
                while (rs.next()) {
                    return rs.getBoolean("asw_is_correct");

                }

                rs.close();
                prepared.close();
            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
            return null;
        }

        return null;
    }

    // function to keep track of scores
    public static void trackScore(String username, Integer game_qu_ID, Integer asw_id, Boolean answerStatus){
        // collects necessary data and inserts into db

        Integer points;
        // convert boolean answerStatus to 0 or 1 as points
        if (answerStatus == true){
            points = 1;
        } else {
            points = 0;
        }

        // the query
        String query ="INSERT INTO game.\"Games\" (game_round, \"game_pl_ID\", \"game_qu_ID\", \"game_asw_ID\", game_points)" +
                      " VALUES ((SELECT round FROM game.\"Game_control\" WHERE \"control_ID\" = 0)," +
                      " (SELECT \"player_ID\" FROM game.\"Player\" WHERE player_username = ?)," +
                      "?," + "?," + "?);";


        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             PreparedStatement prepared = connection.prepareStatement(query);{
                // round, currently fixed but needs to be changed
                // username to get player_ID
                prepared.setString(1, username);
                // game_qu_ID
                prepared.setInt(2, game_qu_ID);
                //asw_txt
                prepared.setInt(3, asw_id );
                //game_asw_ID
                // game_points
                prepared.setInt(4, points);
                prepared.executeUpdate();

                System.out.println("Action was successful!");

            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
        }

    }

    // function to update round in Game_control table in Db
    // round automatically increased by 1
    public static void updateControlRound(){

        // the query
        String query ="UPDATE game.\"Game_control\" SET round = round + 1\n" +
                "WHERE \"control_ID\" = 0;";

        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             PreparedStatement prepared = connection.prepareStatement(query);
            {
                prepared.executeUpdate();
                System.out.println("Action was successful!");

            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
        }

    }

    // function to update last_qu_ID in Game_control table in Db
    public static void updateControlLastQuestion(int lastquestion_ID){
        // the query
        String query ="UPDATE game.\"Game_control\" SET \"last_qu_ID\" = ?\n" +
                "WHERE \"control_ID\" = 0;";

        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             PreparedStatement prepared = connection.prepareStatement(query);{
                // set round
                prepared.setInt(1, lastquestion_ID);
                prepared.executeUpdate();

                System.out.println("Action was successful!");

            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
        }

    }

    // function to get the final score of game (which player won)
    public static Vector getGameResult(){
        // get scores
        String query ="SELECT player_username, SUM(game_points) FROM game.\"Games\"\n" +
                      "JOIN game.\"Player\" ON \"player_ID\" = \"game_pl_ID\"\n" +
                      "WHERE game_round = (SELECT round FROM game.\"Game_control\" WHERE \"control_ID\" = 0)\n" +
                      "GROUP BY player_username\n" +
                      "ORDER BY SUM(game_points) DESC";

        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             PreparedStatement prepared = connection.prepareStatement(query);
            {
                prepared.executeQuery();

                Vector winnerVector = new Vector();
                ResultSet rs = prepared.executeQuery();
                System.out.println("Action was successful!");
                while (rs.next()) {
                    System.out.print("Column 1 returned ");
                    winnerVector.addElement(new String(rs.getString(1)));
                    winnerVector.addElement(new String(String.valueOf(rs.getInt(2))));

                    System.out.print(rs.getString("player_username"));
                    System.out.print(rs.getInt("sum"));

                }

                rs.close();
                prepared.close();
                return winnerVector;
            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
            return null;
        }

    }


    // function to get highscores across all rounds -> who has the highest amount of points across all games played
    public static <Obj>List<Obj> getResultsHighscore(){

        List<Obj> highscore_summary = new ArrayList<Obj>();
        // get scores
        String query ="SELECT player_username, SUM(game_points) as score FROM game.\"Games\"\n" +
                "JOIN game.\"Player\" ON \"game_pl_ID\"=\"player_ID\"\n" +
                "GROUP BY player_username\n" +
                "ORDER BY score desc\n" +
                "LIMIT 5";

        Vector playerVector = new Vector();
        Vector scoreVector = new Vector();
        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             PreparedStatement prepared = connection.prepareStatement(query);
            {
                prepared.executeQuery();
                ResultSet rs = prepared.executeQuery();
                while (rs.next()) {
                    // create string from results
                    playerVector.addElement(new String(rs.getString(1)));
                    scoreVector.addElement(new String(String.valueOf(rs.getInt(2))));

                }

                highscore_summary.add(0, (Obj) playerVector);
                highscore_summary.add(1, (Obj) scoreVector);

                rs.close();
                prepared.close();
                return highscore_summary;
            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
            return null;
        }

    }

}

