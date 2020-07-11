package com.aim.movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.aim.movie.domain.MovieInfo;
import com.aim.movie.util.MySQL;

public class Exercise1 {

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(MySQL.URL.value, MySQL.USER.value, MySQL.PASS.value)) {

            String movieTitle = getMovieTitleFromUser();

            ResultSet resultSet = getMovieResultSet(connection, movieTitle);
            if (resultSet == null) {
                System.out.println("Program darn messed up. Goodbye ...");
                System.exit(-1);
            }

            MovieInfo movieInfo = getMovieInfo(resultSet);

            System.out.format("%-30.30s  %-30.30s %-30.30s %-30.30s %n", "Movie Name", "Director", "Genre", "Rating");
            System.out.format("%-30.30s  %-30.30s %-30.30s %-30.30s %n", "----------", "--------", "-----", "------");

            while (resultSet.next()) {
                System.out.format("%-30.30s  %-30.30s%n", resultSet.getString("movie_name"),
                        resultSet.getString("full_name"));
            }

        } catch (Exception e) {
            System.out.println("Connection failure.");
        } finally {
            System.out.println("\nGoodbye ...");
        }
    }

    public static String getMovieTitleFromUser() {
        System.out.println("This program displays the Director, Genre and Rating of a movie.\n");
        System.out.print("Please enter a movie title: ");

        Scanner input = new Scanner(System.in);
        String movieTitle = input.nextLine();
        input.close();
        return movieTitle;
    }

    public static ResultSet getMovieResultSet(Connection connection, String movieTitle) {
        try {

            Statement statement = connection.createStatement();
            StringBuilder sql = new StringBuilder();
            sql.append("select m.movie_name, d.first_name, d.last_name, r.rating ");
            sql.append("from movies m ");
            sql.append("join directors d on d.director_id = m.director_id ");
            sql.append("join ratings r on r.rating_id = m.rating_id ");
            sql.append("where m.movie_name = '" + movieTitle + "';");

            ResultSet resultSet = statement.executeQuery(sql.toString());
            return resultSet;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MovieInfo getMovieInfo(ResultSet resultSet) {
        MovieInfo movieInfo = new MovieInfo();
        try {
            while (resultSet.next()) {
                movieInfo.setMovieTitle(resultSet.getString("movie_name"));
                movieInfo.setMovieLength(resultSet.getInt("movie_length"));
                movieInfo.setReleaseDate(resultSet.getDate("release_date"));
                movieInfo.setDirector(resultSet.getString("movie_name"));
                movieInfo.setMovieTitle(resultSet.getString("movie_name"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return movieInfo;
    }

}
