package io.ylab.intensive.lesson05.eventsourcing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class PersonApiImpl implements PersonApi{

    private final String EXCHANGE_NAME = "exc";
    private final String QUEUE_NAME = "queue";
    private Channel channelMQ;
    private java.sql.Connection connectionDB;

    @Autowired
    public PersonApiImpl(ConnectionFactory connectionFactory, DataSource dataSource) {
        try {
            Connection connectionMQ = connectionFactory.newConnection();
            Channel channel = connectionMQ.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*");

            java.sql.Connection connectionDB = dataSource.getConnection();

            this.channelMQ = channel;
            this.connectionDB = connectionDB;

        } catch (IOException | TimeoutException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePerson(Long personId) {
        String deleteMessage = "d " + personId;
        try {
            channelMQ.basicPublish("", QUEUE_NAME, null, deleteMessage.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        ObjectMapper objectMapper = new ObjectMapper();
        String saveMessage;
        try {
            saveMessage = "s " + objectMapper.writeValueAsString(new Person(personId, firstName, lastName, middleName));
            channelMQ.basicPublish("", QUEUE_NAME, null, saveMessage.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Person findPerson(Long personId) {
        String getSQL = "select * from person where (person_id = " + personId + ")";
        try (PreparedStatement ps = connectionDB.prepareStatement(getSQL);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Person person = new Person(personId, rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("middle_name"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> findAll() {
        List<Person> list = new ArrayList<>();
        String getSQL = "select * from person order by person_id";
        try (PreparedStatement ps = connectionDB.prepareStatement(getSQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Person person = new Person(rs.getLong("person_id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("middle_name"));
                list.add(person);
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
