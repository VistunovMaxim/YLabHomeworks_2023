package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        DataSource dataSource = DbUtil.buildDataSource();

        try (Connection connectionMQ = connectionFactory.newConnection();
             Channel channel = connectionMQ.createChannel();
             java.sql.Connection connectionBD = dataSource.getConnection();
             Scanner sc = new Scanner(System.in)) {

            PersonApiImpl personApi = new PersonApiImpl(channel, connectionBD);
            boolean isRun = true;
            Long personId;
            String personName;
            String personLastName;
            String personMidName;
            List<Person> list;

            while (isRun) {

                System.out.println("\n"
                        + "Введите номер команды-запроса: \n"
                        + "1) Добавить пользователя в БД\n"
                        + "2) Удалить пользователя из БД\n"
                        + "3) Найти пользователя в БД\n"
                        + "4) Отобразить всех пользователей в БД\n"
                        + "5) Выход");

                switch (sc.nextInt()) {
                    case (1):
                        System.out.println("Введите ID: ");
                        personId = sc.nextLong();
                        System.out.println("Введите имя: ");
                        personName = sc.next();
                        System.out.println("Введите фамилию: ");
                        personLastName = sc.next();
                        System.out.println("Введите отчество: ");
                        personMidName = sc.next();
                        personApi.savePerson(personId, personName, personLastName, personMidName);
                        break;
                    case (2):
                        System.out.println("Введите ID: ");
                        personId = sc.nextLong();
                        personApi.deletePerson(personId);
                        break;
                    case (3):
                        System.out.println("Введите ID: ");
                        personId = sc.nextLong();
                        System.out.println(personApi.findPerson(personId));
                        break;
                    case (4):
                        list = personApi.findAll();
                        for (Person person : list) {
                            System.out.println(person);
                        }
                        break;
                    case (5):
                        isRun = false;
                        break;
                    default:
                        System.err.println("Неверное значение");
                }

            }

        } catch (IOException e) {
            System.err.println("error");
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}