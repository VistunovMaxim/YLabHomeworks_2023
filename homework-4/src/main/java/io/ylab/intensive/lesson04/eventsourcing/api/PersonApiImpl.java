package io.ylab.intensive.lesson04.eventsourcing.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.ylab.intensive.lesson04.eventsourcing.Person;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {

    private final String EXCHANGE_NAME = "exc";
    private final String QUEUE_NAME = "queue";
    private Channel channelMQ;
    private Connection connectionBD;

    public PersonApiImpl(Channel channel, Connection connection) throws IOException {
        this.channelMQ = channel;
        this.connectionBD = connection;
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*");
    }

    @Override
    public void deletePerson(Long personId) throws IOException {
        String deleteMessage = "d " + personId;
        channelMQ.basicPublish("", QUEUE_NAME, null, deleteMessage.getBytes());
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String saveMessage;
        saveMessage = "s " + objectMapper.writeValueAsString(new Person(personId, firstName, lastName, middleName));
        channelMQ.basicPublish("", QUEUE_NAME, null, saveMessage.getBytes());
    }

    @Override
    public Person findPerson(Long personId) {
        String getSQL = "select * from person where (person_id = " + personId + ")";
        try (PreparedStatement ps = connectionBD.prepareStatement(getSQL);
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
        try (PreparedStatement ps = connectionBD.prepareStatement(getSQL);
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
