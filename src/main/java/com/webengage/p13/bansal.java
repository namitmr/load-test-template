package com.webengage.p13;

import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.Instant;
import java.util.Random;

/**
 * Created by namitmahuvakar on 18/04/17.
 */
    /*
    You can compile and run this example with a command like:
      javac BasicSample.java && java -cp .:~/path/to/postgresql-9.4.1208.jar BasicSample
    You can download the postgres JDBC driver jar from https://jdbc.postgresql.org.
    */
    public class bansal {
//        public static void main(String[] args) throws ClassNotFoundException, SQLException {
//            // Load the postgres JDBC driver.
//            Class.forName("org.postgresql.Driver");
//            Random rand;
//
//            // nextInt is normally exclusive of the top value,
//            // so add 1 to make it inclusive
//
//            int randomNum = 0;
//            String str ="";
//            // Connect to the "bank" database.
//            Connection db = DriverManager.getConnection("jdbc:postgresql://192.168.2.88:26257/users_list?sslmode=disable", "root", "");
//
//            try {
//                // Create the "accounts" table.
////                db.createStatement().execute("CREATE TABLE IF NOT EXISTS accounts (id INT PRIMARY KEY, balance INT)");
//                long start = Instant.now().toEpochMilli();
//                System.out.println("Start: " + start);
//
//                for(int i = 0 ; i< 1000000000; i++) {
//                    if(i % 1000 == 0) {
//                        System.out.println("done: " + i + " in " + (Instant.now().toEpochMilli() - start) / 1000 + " seconds");
//                    }
//
//                    if(i % 1000 == 0) {
//                        System.out.println("done: " + i + " in " + (Instant.now().toEpochMilli() - start) / 1000 + " seconds");
//                    }
//
//                    randomNum = 1 + (int)(Math.random() * ((10000 - 1) + 1));
//                    // Insert two rows into the "accounts" table.
//                    str = i+":"+randomNum;
//                    try {
//                        db.createStatement().execute("INSERT INTO users_list.kv (k, v) VALUES ('" + str + "', " + i + ")");
//                    } catch (PSQLException e) {
//
//                        continue;
//                    }
//
//                    // Print out the balances.
//                }
//                System.out.println("stop. total time taken: " + (Instant.now().toEpochMilli() - start) / 1000  + " seconds");
//                System.out.println("Initial balances:");
//                ResultSet res = db.createStatement().executeQuery("SELECT count(1) FROM kv");
//                while (res.next()) {
//                    System.out.printf("\taccount %s: %s\n", res.toString());
//                }
//            } finally {
//                // Close the database connection.
//                db.close();
//            }
//        }
    }