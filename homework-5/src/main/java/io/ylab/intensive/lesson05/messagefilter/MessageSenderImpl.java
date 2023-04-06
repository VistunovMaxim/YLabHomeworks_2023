package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageSenderImpl implements MessageSender {

    private final String INPUT_QUEUE_NAME = "input";
    private final String OUTPUT_QUEUE_NAME = "output";
    private Channel inputChannel;
    private Channel outputChannel;
    @Autowired
    private MessageProcessor messageProcessor;

    @Autowired
    public MessageSenderImpl(ConnectionFactory connectionFactory) {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel inputChannel = connection.createChannel();
            inputChannel.queueDeclare(INPUT_QUEUE_NAME, true, false, false, null);
            this.inputChannel = inputChannel;

            connection = connectionFactory.newConnection();
            Channel outputChannel = connection.createChannel();
            outputChannel.queueDeclare(OUTPUT_QUEUE_NAME, true, false, false, null);
            this.outputChannel = outputChannel;
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendMessage(String message) {
        try {
            outputChannel.basicPublish("", OUTPUT_QUEUE_NAME, null, message.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void receiveMessage() {
        try {
            GetResponse message = inputChannel.basicGet(INPUT_QUEUE_NAME, true);
            if (message != null) {
                String receiving = new String(message.getBody(), StandardCharsets.UTF_8);
                receiving = messageProcessor.messageProcessing(receiving);
                sendMessage(receiving);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
