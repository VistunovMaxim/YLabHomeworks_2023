package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        boolean isRun = true;
        String name;
        String key;
        String value;
        List<String> list;
        try (Scanner sc = new Scanner(System.in)) {

            while (isRun) {

                System.out.println("\n"
                        + "Введите номер команды-запроса: \n"
                        + "1) Создать новый / переключиться на существующий экземпляр Map\n"
                        + "2) Добавить / заменить значение в текущей Map\n"
                        + "3) Получить значение по ключу в текущей Map\n"
                        + "4) Проверить наличие значения по ключу в текущей Map\n"
                        + "5) Удалить значение по ключу из текущей Map\n"
                        + "6) Получить список ключей из текущей Map\n"
                        + "7) Отчистить текущую Map\n"
                        + "8) Выход");

                switch (sc.nextInt()) {
                    case (1):
                        System.out.println("Введите имя Map: ");
                        name = sc.next();
                        persistentMap.init(name);
                        break;
                    case (2):
                        System.out.println("Введите ключ: ");
                        key = sc.next();
                        System.out.println("Введите значение: ");
                        value = sc.next();
                        persistentMap.put(key, value);
                        break;
                    case (3):
                        System.out.println("Введите ключ: ");
                        key = sc.next();
                        System.out.println("Значение - " + persistentMap.get(key));
                        break;
                    case (4):
                        System.out.println("Введите ключ: ");
                        key = sc.next();
                        System.out.println(persistentMap.containsKey(key) ? "Значение присутствует в Map" :
                                "Значения по такому ключу отсутствует");
                        break;
                    case (5):
                        System.out.println("Введите ключ: ");
                        key = sc.next();
                        persistentMap.remove(key);
                        break;
                    case (6):
                        list = persistentMap.getKeys();
                        for (String keys : list) {
                            System.out.print("|" + keys + "| ");
                        }
                        break;
                    case (7):
                        persistentMap.clear();
                        break;
                    case (8):
                        isRun = false;
                        break;
                    default:
                        System.err.println("Неверное значение");
                        break;
                }

            }
        }
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
