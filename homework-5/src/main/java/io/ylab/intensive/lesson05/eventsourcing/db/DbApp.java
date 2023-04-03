package io.ylab.intensive.lesson05.eventsourcing.db;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        MessageProcessor messageProcessor = applicationContext.getBean(MessageProcessor.class);
        applicationContext.start();
        while (!Thread.currentThread().isInterrupted()) {
            messageProcessor.processSingleMessage();
        }
    }
}
