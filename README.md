# Quizduell game project
Quiz for two players on one machine, written in Java, using JavaFX (GUI).

[Miro Board](https://miro.com/app/board/uXjVMQbmwPw=/)


# Details
## Database
To play the game, the PostgreSQL Database needs to be set up. This can be done using the provided database dump, which can be found 
in the file **pgdump_finalized.sql**. When importing the dump, it should be imported into an existing database. Then the import/restore can be performed using the following command: _psql -h hostname -d databasename -U username -f pgdump_finalized.sql_. If there are different databasename, username and hostname used, this would need to be edited in the Class **ConnectPostgresDB**.

## Running the game 
After importing the Code, the game can be started by running the class **DuellApplication**

