package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class ApiApp {
    public static void main(String[] args) throws Exception {

        boolean isRun = true;
        Long personId;
        String personName;
        String personLastName;
        String personMidName;
        List<Person> list;

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        applicationContext.start();

        try (Scanner sc = new Scanner(System.in)) {

            while (isRun) {

                System.out.println("\n"
                        + "Введите номер команды-запроса: \n"
                        + "1) Добавить пользователя в БД\n"
                        + "2) Удалить пользователя из БД\n"
                        + "3) Найти пользователя в БД\n"
                        + "4) Отобразить всех пользователей в БД\n"
                        + "5) Выход\n"
                );

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
                        applicationContext.close();
                        break;
                    default:
                        System.err.println("Неверное значение");
                }
            }
        }
    }
}
