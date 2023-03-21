import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Sorter {

    public File sortFile(File dataFile, int countOfNums) throws IOException {
        int countOfBuckets = Sorter.findBestCountOfBuckets(dataFile);
        List<File> list = Sorter.makeListOfSortedBuckets(dataFile, countOfBuckets, countOfNums);
        if (countOfBuckets != 1) {
            Sorter.mergeBuckets(list.get(0), list.get(1));
            while (list.size() != 1) {
                Sorter.mergeBuckets(list.get(0), list.get(1));
                list.get(1).delete();
                list.remove(1);
            }
        }
        return list.get(0);
    }

    public static int findBestCountOfBuckets(File file) {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long sizeOfFile = file.length();
        int countOfBuckets = (int) (sizeOfFile / (freeMemory / 2));
        if (countOfBuckets == 0) countOfBuckets++;
        return countOfBuckets;
    }

    public static List makeListOfSortedBuckets(File initialFile, int countOfBuckets, int countOfNums) throws FileNotFoundException {
        int numsPerBucket = (int) Math.ceil(countOfNums / countOfBuckets);
        List<File> listOfBuckets = new ArrayList<>(countOfBuckets);
        List<Long> listForSort = new ArrayList<>(numsPerBucket);
        try (Scanner scanner = new Scanner(new FileInputStream(initialFile))) {
            for (int i = 0; i < countOfBuckets; i++) {
                File bucket = new File("bucket_" + (i + 1) + ".txt");
                try (PrintWriter pw = new PrintWriter(bucket)) {
                    for (int j = 0; j < numsPerBucket; j++) {
                        listForSort.add(scanner.nextLong());
                    }
                    if (i == countOfBuckets - 1 && scanner.hasNextLong()) {
                        while (scanner.hasNextLong()) {
                            listForSort.add(scanner.nextLong());
                        }
                    }
                    Collections.sort(listForSort);
                    for (int j = 0; j < listForSort.size(); j++) {
                        pw.println(listForSort.get(j));
                    }
                    listForSort.clear();
                    pw.flush();
                }
                listOfBuckets.add(bucket);
            }
        }
        return listOfBuckets;
    }

    public static void mergeBuckets(File file1, File file2) {
        File mergedFiles = new File("mergedFiles.txt");
        try (Scanner scanner1 = new Scanner(new FileInputStream(file1));
             Scanner scanner2 = new Scanner(new FileInputStream(file2));
             PrintWriter pw = new PrintWriter(mergedFiles)) {
            long x = scanner1.nextLong();
            long y = scanner2.nextLong();
            while (scanner1.hasNext() && scanner2.hasNext()) {
                if (x > y) {
                    pw.println(y);
                    y = scanner2.nextLong();
                } else {
                    pw.println(x);
                    x = scanner1.nextLong();
                }
            }
            if (!scanner1.hasNextLong()) {
                while (scanner2.hasNext()) {
                    pw.println(scanner2.nextLong());
                }
            }
            if (!scanner2.hasNextLong()) {
                while (scanner1.hasNext()) {
                    pw.println(scanner1.nextLong());
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Scanner scanner3 = new Scanner(new FileInputStream(mergedFiles));
             PrintWriter pw = new PrintWriter(file1)) {
            while (scanner3.hasNext()) {
                pw.println(scanner3.nextLong());
            }
            mergedFiles.delete();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}