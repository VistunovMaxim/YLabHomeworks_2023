package io.ylab.intensive.lesson04.filesort;

import java.beans.Statement;
import java.io.*;
import java.sql.*;
import java.util.Scanner;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
    private DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {

        long m = System.currentTimeMillis();
        String insertQuery = "insert into numbers (val) values (?)";
        String outputSortedQuery = "select * from numbers order by (val) asc";

        try (BufferedReader reader = new BufferedReader(new FileReader(data));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            connection.setAutoCommit(false);
            while (reader.ready()) {
                for (int i = 0; i < 10000; i++) {
                    if (reader.ready()) {
                        preparedStatement.setLong(1, Long.valueOf(reader.readLine()));
                        preparedStatement.addBatch();
                    } else {
                        i = 10000;
                    }
                }
                preparedStatement.executeBatch();
                connection.commit();
            }

//            while (reader.ready()) {
//                preparedStatement.setLong(1, Long.valueOf(reader.readLine()));  // NO BATCH-PROCESSING
//                preparedStatement.executeUpdate();
//            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        File sortedFile = new File("sortedData.txt");

        try (PrintWriter pw = new PrintWriter(sortedFile);
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatementSort = connection.prepareStatement(outputSortedQuery);
             ResultSet resultSet = preparedStatementSort.executeQuery()) {

            while (resultSet.next()) {
                pw.println(resultSet.getLong("val"));
            }

        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        long n = System.currentTimeMillis();
        double resTime = (n - m) / 1000.0;
        System.out.println(resTime + " sek.");

        return null;
    }
}
