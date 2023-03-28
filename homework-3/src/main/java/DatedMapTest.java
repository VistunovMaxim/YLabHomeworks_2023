import java.util.Scanner;

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();
        int operationNum;
        String key = "";
        String value = "";
        boolean isStop = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (isStop) {
                System.out.print("\nВыберите тип операции:\n1) Добавить элемент\n2) Получить элемент\n" +
                        "3) Получить множество элементов\n4) Проверить наличие элемента\n5) Получить время добавления элемента\n" +
                        "6) Удалить элемент\n7) Завершить работу\n");
                System.out.print("Выбор операции - ");
                operationNum = scanner.nextInt();

                if (operationNum == 2 || operationNum == 4 || operationNum == 5 || operationNum == 6) {
                    System.out.print("\nВведите ключ: ");
                    key = scanner.next();
                } else if (operationNum == 1) {
                    System.out.print("\nВведите ключ: ");
                    key = scanner.next();
                    System.out.print("Введите значение: ");
                    value = scanner.next();
                }

                switch (operationNum) {
                    case 1 -> datedMap.put(key, value);
                    case 2 -> System.out.println("Искомое значение: " + datedMap.get(key));
                    case 3 -> System.out.println("\n" + datedMap.keySet());
                    case 4 -> System.out.println(datedMap.containsKey(key) ? "Значение присутствует в списке" :
                        "Значения в списке нет");
                    case 5 -> System.out.println(datedMap.getKeyLastInsertionDate(key));
                    case 6 -> datedMap.remove(key);
                    case 7 -> isStop = false;
                }
            }
        }
    }
}