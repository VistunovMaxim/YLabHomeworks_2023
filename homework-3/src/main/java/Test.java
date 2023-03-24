import java.io.File;
import java.io.IOException;


public class Test {
    public static void main(String[] args) throws IOException {
        int countOfNums = 7_244_433;
        File dataFile = new Generator().generate("data.txt", countOfNums);
        System.out.println(new Validator(dataFile).checkCountOfNums() + " - кол-во чисел исходного файла");
        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile, countOfNums);
        System.out.println(new Validator(sortedFile).isSorted()); // true
        System.out.println(new Validator(sortedFile).checkCountOfNums() + " - кол-во чисел отсортированного файла");
    }
}