package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Component
public class MessageProcessorImpl implements MessageProcessor {
    private final String QUEUE_NAME = "queue";
    @Autowired
    private DbApi dbApi;
    private Channel channel;
    private ObjectMapper objectMapper;

    @Autowired
    public MessageProcessorImpl(ConnectionFactory connectionFactory) {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            this.channel = channel;
            this.objectMapper = new ObjectMapper();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void processSingleMessage() {
        try {
            GetResponse message = channel.basicGet(QUEUE_NAME, true);
            if (message != null) {
                String receiving = new String(message.getBody(), StandardCharsets.UTF_8);

                switch (receiving.substring(0, 1)) {
                    case ("s"):
                        Person person = objectMapper.readValue(receiving.substring(2), Person.class);
                        dbApi.insertPerson(person);
                        break;
                    case ("d"):
                        dbApi.deletePerson(Long.parseLong(receiving.substring(2)));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
