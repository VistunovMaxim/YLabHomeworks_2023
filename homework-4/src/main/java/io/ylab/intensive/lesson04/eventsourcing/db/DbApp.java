package io.ylab.intensive.lesson04.eventsourcing.db;

import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

public class DbApp {

    public static void main(String[] args) throws Exception {

        final String QUEUE_NAME = "queue";
        String deleteSQL = "delete from person where (person_id = ?)";
        String insertSQL = "insert into person (person_id, first_name, last_name, middle_name) values (?, ?, ?, ?);";
        String updateSQL = "update person set first_name = ?, last_name = ?, middle_name = ? where person_id = ?";

        ObjectMapper objectMapper = new ObjectMapper();
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();

        try (java.sql.Connection connectionBd = dataSource.getConnection();
             PreparedStatement psDelete = connectionBd.prepareStatement(deleteSQL);
             PreparedStatement psSave = connectionBd.prepareStatement(insertSQL);
             PreparedStatement psUpdate = connectionBd.prepareStatement(updateSQL);
             Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(QUEUE_NAME, true);
                if (message != null) {
                    String receiving = new String(message.getBody(), StandardCharsets.UTF_8);

                    switch (receiving.substring(0, 1)) {
                        case ("s"):
                            Person person = objectMapper.readValue(receiving.substring(2), Person.class);
                            if (containsSQL(dataSource, person.getId())) {
                                psUpdate.setString(1, person.getName());
                                psUpdate.setString(2, person.getLastName());
                                psUpdate.setString(3, person.getMiddleName());
                                psUpdate.setLong(4, person.getId());
                                psUpdate.executeUpdate();
                            } else {
                                psSave.setLong(1, person.getId());
                                psSave.setString(2, person.getName());
                                psSave.setString(3, person.getLastName());
                                psSave.setString(4, person.getMiddleName());
                                psSave.executeUpdate();
                            }
                            break;
                        case ("d"):
                            if (containsSQL(dataSource, Long.parseLong(receiving.substring(2)))) {
                                psDelete.setLong(1, Long.parseLong(receiving.substring(2)));
                                psDelete.executeUpdate();
                            } else {
                                System.err.println("Была попытка удаления, но данные не найдены");
                            }
                    }
                }
            }
        }
    }

    private static boolean containsSQL(DataSource dataSource, Long personId) throws SQLException {
        String containsSQL = "select * from person where (person_id = " + personId + ")";
        try (java.sql.Connection connectionBd = dataSource.getConnection();
             PreparedStatement psContains = connectionBd.prepareStatement(containsSQL);
             ResultSet rs = psContains.executeQuery()) {
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
