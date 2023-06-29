package com.example.bhtduell;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectPostgresDB {

    private static ConnectPostgresDB instance;
    private Connection connection;
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String user = "postgres";
    private String pwd = "BHT2023";

    // constructor for ConnectPostgresDB class
    // constructor is called when new ConnectPostgresDB is created (in getInstance() function)
    // constructor sets the connection
    private ConnectPostgresDB() throws SQLException {
            this.connection = DriverManager.getConnection(url, user, pwd);
    }

    // returns Connection object
    // connection is set in constructor
    public Connection getConnection() {
        return connection;
    }

    // function that creates new instance of class ConnectPostgresDB() -> calls constructor, which then establishes connection
    // if the instance object is already filled, just return it, so there is not a new connection established
    public static ConnectPostgresDB getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectPostgresDB();
        } else if (instance.getConnection().isClosed()) {
            instance = new ConnectPostgresDB();
        }

        return instance;
    }
}


