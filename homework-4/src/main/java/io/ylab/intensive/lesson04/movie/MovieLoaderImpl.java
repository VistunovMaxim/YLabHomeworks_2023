package io.ylab.intensive.lesson04.movie;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {

        String line;
        String[] subString;
        List<Movie> movies = new ArrayList<>();

        String insertQuery = "insert into movie (year, length, title, subject, actors, actress, director, popularity," +
                " awards) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            while ((line = reader.readLine()) != null) {

                if (Character.isDigit(line.charAt(0))) {

                    subString = line.split(";");
                    Movie movie = new Movie();

                    movie.setYear(subString[0].equals("") ? null : Integer.parseInt(subString[0]));
                    movie.setLength(subString[1].equals("") ? null : Integer.parseInt(subString[1]));
                    movie.setTitle(subString[2]);
                    movie.setSubject(subString[3]);
                    movie.setActors(subString[4]);
                    movie.setActress(subString[5]);
                    movie.setDirector(subString[6]);
                    movie.setPopularity(subString[7].equals("") ? null : Integer.parseInt(subString[7]));
                    movie.setAwards(subString[8].equals("") ? null : Boolean.getBoolean(subString[8]));

                    movies.add(movie);
                }
            }

            for (Movie movie : movies) {
                if (movie.getYear() == null) {
                    preparedStatement.setNull(1, Types.NULL);
                } else {
                    preparedStatement.setInt(1, movie.getYear());
                }

                if (movie.getLength() == null) {
                    preparedStatement.setNull(2, Types.NULL);
                } else {
                    preparedStatement.setInt(2, movie.getLength());
                }

                preparedStatement.setString(3, movie.getTitle());
                preparedStatement.setString(4, movie.getSubject());
                preparedStatement.setString(5, movie.getActors());
                preparedStatement.setString(6, movie.getActress());
                preparedStatement.setString(7, movie.getDirector());

                if (movie.getPopularity() == null) {
                    preparedStatement.setNull(8, Types.NULL);
                } else {
                    preparedStatement.setInt(8, movie.getPopularity());
                }

                if (movie.getAwards() == null) {
                    preparedStatement.setNull(9, Types.NULL);
                } else {
                    preparedStatement.setBoolean(9, movie.getAwards());
                }

                preparedStatement.executeUpdate();
            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
