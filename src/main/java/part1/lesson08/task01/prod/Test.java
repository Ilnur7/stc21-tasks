package part1.lesson08.task01.prod;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите название файла для чтения:");
        //String nameFileForRead = scanner.nextLine().trim() + ".txt";
        String nameFileForRead = "data.txt";

        System.out.println("Введите название файла для записи:");
        //String nameFileForWrite = scanner.nextLine().trim() + ".txt";
        String nameFileForWrite = "write.txt";

        System.out.println("Введите количество итераций:");
        //int countIteration = Integer.parseInt(scanner.nextLine().trim());
        int countIteration = 500000;
        scanner.close();

        Life life = new Life();
        LifeThreads lifeThreads = new LifeThreads();

        long start = System.currentTimeMillis();
        //life.start(nameFileForRead, nameFileForWrite, countIteration);
        System.out.println(System.currentTimeMillis() - start);

        long startThreads = System.currentTimeMillis();
        lifeThreads.start(nameFileForRead, nameFileForWrite, countIteration);
        System.out.println(System.currentTimeMillis() - startThreads);
    }
}
