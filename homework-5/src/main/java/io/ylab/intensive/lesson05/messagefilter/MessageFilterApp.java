package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.sql.SQLException;

public class MessageFilterApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        DbInit dbInit = applicationContext.getBean(DbInit.class);
        MessageSender messageSender = applicationContext.getBean(MessageSender.class);

        File file = new File("CensoredWords.txt");
        try {
            dbInit.init(file);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while (!Thread.currentThread().isInterrupted()) {
            messageSender.receiveMessage();
        }

    }
}
