package com.example.bhtduell;

import java.sql.*; //Any source file that uses JDBC needs to import the java.sql package
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JavaToDatabase {

    // function that returns data to establish connection to postgres DB
    public static Vector connectData() {
        // database connection
        // with postgres user and password
        // return Vector with 0:url, 1:user, 2:password
        Vector data = new Vector<>();
        String url = "jdbc:postgresql://localhost:5432/postgres"; // follow schema: jdbc:postgresql://host:port/database
        String user = "postgres";
        String pwd = "BHT2023";

        data.add(url);
        data.add(user);
        data.add(pwd);
        return data;

    }

    public static void writePlayerToDB(String username_1, String username_2) throws SQLException {

        // set up for db
        /*String url = "jdbc:postgresql://localhost:5432/postgres"; // follow schema: jdbc:postgresql://host:port/database
        String user = "postgres";
        String pwd = "BHT2023";*/

        /*Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);*/

        String playername_1 = username_1;
        String playername_2 = username_2;

        // the query
        String query = "INSERT INTO game.\"Player\" (player_username) VALUES (?)";

        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()) {

            PreparedStatement prepared = connection.prepareStatement(query);
            {
                // for player1
                prepared.setString(1, playername_1);
                prepared.executeUpdate();

                // for player2
                prepared.setString(1, playername_2);
                prepared.executeUpdate();
                System.out.println("Action was successful!");

            }
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


        // db connection
        /*String url = "jdbc:postgresql://localhost:5432/postgres"; // follow schema: jdbc:postgresql://host:port/database
        String user = "postgres";
        String pwd = "BHT2023";*/
        /*Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);*/

        String query_question = "SELECT \"qu_ID\", \"qu_text\" FROM game.\"Questions\"\n" +
                "WHERE \"qu_ID\"=?;";
        //System.out.println(query_question);
        Vector questionVector;
        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()) {
            //try (Connection connection = DriverManager.getConnection(url, user, pwd);
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
                    //System.out.print("Column 1 returned ");
                    //System.out.println(rs.getString("qu_text"));
                    // add column w question text to vector
                    questionVector.addElement(new String(rs.getString(1)));
                    questionVector.addElement(new String(rs.getString(2)));

                }
                resultList.add(0, (Obj) rsIsEmpty);
                resultList.add(1, (Obj) questionVector);
                rs.close();
                prepared.close();
                return resultList; //questionVector;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    } /*catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
            return null;
        }*/ /*catch(
    SQLException e)

    {
        throw new RuntimeException(e);}*/



        //}


    // copy of function for safety
    /*public static Vector getQuestion(int question_counter) {

        // db connection
        *//*String url = "jdbc:postgresql://localhost:5432/postgres"; // follow schema: jdbc:postgresql://host:port/database
        String user = "postgres";
        String pwd = "BHT2023";*//*
        Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);

        String query_question = "SELECT \"qu_ID\", \"qu_text\" FROM game.\"Questions\"\n" +
                "WHERE \"qu_ID\"=?;";
        System.out.println(query_question);
        Vector questionVector;
        try (Connection connection = DriverManager.getConnection(url, user, pwd);
             // Get question from DB
             PreparedStatement prepared = connection.prepareStatement(query_question)) {
            // get question and answer text from db
            // question_counter is in this case also id of question in database
            prepared.setInt(1, question_counter); // one is the question mark in query
            ResultSet rs = prepared.executeQuery();

            // Vector class implements a growable array of objects
            // we use it to add results from ResultSet to it => the results from db query
            // because ResultSet is difficult to access after
            // Vector for question
            questionVector = new Vector();


            while(rs.next()){
                System.out.print("Column 1 returned ");
                System.out.println(rs.getString("qu_text"));
                // add column w question text to vector
                questionVector.addElement(new String(rs.getString(1)));
                questionVector.addElement(new String(rs.getString(2)));

            }


            rs.close();
            prepared.close();
            return questionVector;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }*/
    public static Vector getAnswers ( int qu_counter){
        /*Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);*/
        // get answers from DB
        String query_answer = "SELECT \"asw_text\" FROM game.\"Answers\"\n" +
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

                while (rs.next()) {
                    System.out.print("Column 1 returned ");
                    System.out.println(rs.getString("asw_text"));
                    // add column w question text to vector
                    answerVector.addElement(new String(rs.getString(1)));

                }


                rs.close();
                prepared.close();


                return answerVector;

            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
            return null;

        } //catch (SQLException e)

//    {
//        throw new RuntimeException(e);
//
////        Vector emptyVector = new Vector();
////        return emptyVector;
//        }
    }

    public static Boolean answerTrueFalse(String answer_text, int primaryKey){
        /*System.out.println("answerTrueFalse");
        Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);*/
        // get answers from DB
        String query_bool = "SELECT \"asw_is_correct\" FROM game.\"Answers\"\n" +
                "WHERE \"asw_text\"=? AND \"asw_qu_ID\"=?;";
        //System.out.println(query_bool);
        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             // Get answers from DB
             PreparedStatement prepared = connection.prepareStatement(query_bool);{
                // get answer text from db
                // question_counter is in this case also id of question in database
                prepared.setString(1, answer_text); // one is the question mark in query
                prepared.setInt(2, primaryKey); // one is the question mark in query
                ResultSet rs = prepared.executeQuery();

                // Vector class implements a growable array of objects
                // we use it to add results from ResultSet to it => the results from db query
                // because ResultSet is difficult to access after
                // Vector for question
                while (rs.next()) {
                    System.out.print("Column 1 returned ");
                    System.out.print(rs.getBoolean("asw_is_correct"));
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
    public static void trackScore(String username, Integer game_qu_ID, String asw_txt, Boolean answerStatus){
        // collects necessary data and inserts into db

        Integer points;
        // convert boolean answerStatus to 0 or 1 as points
        if (answerStatus == true){
            points = 1;
        } else {
            points = 0;
        }

        /*Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);*/

        // the query
        //String query = "INSERT INTO game.\"Player\" (player_username) VALUES (?)";
        String query ="INSERT INTO game.\"Games\" (game_round, \"game_pl_ID\", \"game_qu_ID\", \"game_asw_ID\", game_points)" +
                      " VALUES ((SELECT round FROM game.\"Game_control\" WHERE \"control_ID\" = 0)," +
                      "(SELECT \"player_ID\" FROM game.\"Player\" WHERE player_username = ?)," +
                      "?," +
                      "(SELECT \"asw_ID\" FROM game.\"Answers\"" +
                      " WHERE \"asw_text\"=? AND \"asw_qu_ID\"=?), ?);";

        //System.out.println(query);

        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             PreparedStatement prepared = connection.prepareStatement(query);{
                // round, currently fixed but needs to be changed
                //prepared.setInt(1, 2);
                // username to get player_ID
                prepared.setString(1, username);
                // game_qu_ID
                prepared.setInt(2, game_qu_ID);
                //asw_txt
                prepared.setString(3, asw_txt);
                //game_asw_ID
                prepared.setInt(4, game_qu_ID);
                // game_points
                prepared.setInt(5, points);
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
        /*Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);*/

        // the query
        String query ="UPDATE game.\"Game_control\" SET round = round + 1\n" +
                "WHERE \"control_ID\" = 0;";

        //System.out.println(query);

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
        /*Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);*/

        // the query
        //String query = "INSERT INTO game.\"Player\" (player_username) VALUES (?)";
        String query ="UPDATE game.\"Game_control\" SET \"last_qu_ID\" = ?\n" +
                "WHERE \"control_ID\" = 0;";

        //System.out.println(query);

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
        /*Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);*/

        // get scores
        String query ="SELECT player_username, SUM(game_points) FROM game.\"Games\"\n" +
                      "JOIN game.\"Player\" ON \"player_ID\" = \"game_pl_ID\"\n" +
                      "WHERE game_round = (SELECT round FROM game.\"Game_control\" WHERE \"control_ID\" = 0)\n" +
                      "GROUP BY player_username\n" +
                      "ORDER BY SUM(game_points) DESC";

        //System.out.println(query);

        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             PreparedStatement prepared = connection.prepareStatement(query);
            {
                // set round
                //prepared.setInt(1, 3);
                prepared.executeQuery();

                Vector winnerVector = new Vector();
                ResultSet rs = prepared.executeQuery();
                System.out.println("Action was successful!");
                while (rs.next()) {
                    System.out.print("Column 1 returned ");
                    //System.out.print(rs.getBoolean("asw_is_correct"));
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
    public static String getResultsHighscore(){
        /*Vector DBConnectData = connectData();
        String url = (String) DBConnectData.get(0);
        String user = (String) DBConnectData.get(1);
        String pwd = (String) DBConnectData.get(2);*/

        // get scores
        String query ="SELECT player_username, SUM(game_points) as score FROM game.\"Games\"\n" +
                "JOIN game.\"Player\" ON \"game_pl_ID\"=\"player_ID\"\n" +
                "GROUP BY player_username\n" +
                "ORDER BY score desc\n" +
                "LIMIT 5";

        try (Connection connection = ConnectPostgresDB.getInstance().getConnection()){
             PreparedStatement prepared = connection.prepareStatement(query);
            {
                // set round
                //prepared.setInt(1, 3);
                prepared.executeQuery();

                String result_str_highscore = "";
                ResultSet rs = prepared.executeQuery();
                while (rs.next()) {
                    // create string from results
                    result_str_highscore += String.format("    %-40s %s\n", (new String(rs.getString(1))), new String(String.valueOf(rs.getInt(2))));

                }

                rs.close();
                prepared.close();
                System.out.println(result_str_highscore);
                return result_str_highscore;
            }
        } catch (SQLException except) {
            Logger logger = Logger.getLogger(JavaToDatabase.class.getName());
            logger.log(Level.SEVERE, except.getMessage(), except);
            return null;
        }

    }

}

