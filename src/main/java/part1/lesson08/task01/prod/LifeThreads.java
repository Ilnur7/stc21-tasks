package part1.lesson08.task01.prod;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Игра - жизнь
 */
public class LifeThreads {

    private static int[][] field = new int[40][40];

    /**
     * Метод запуска игры жизнь
     * @param nameFileForRead
     * @param nameFileForWrite
     * @param countIteration
     * @throws InterruptedException
     */
    public void start(String nameFileForRead, String nameFileForWrite, int countIteration) throws InterruptedException {
        readFile(nameFileForRead);
        updateField(countIteration);
        writeInFile(nameFileForWrite);
    }

    /**
     * Метод обновления поля при каждой итерации
     * @param countIteration
     * @throws InterruptedException
     */
    private void updateField(int countIteration) throws InterruptedException {
        int[][] newField;
        for (int i = 0; i < countIteration; i++) {
            //printArray(field);
            newField = createNewField();
            field = newField;
            //Thread.sleep(800);
            //clearScreen();
        }
    }

    /**
     * Метод записи полученного поля в файл
     * @param nameFileForWrite
     */
    private void writeInFile(String nameFileForWrite) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nameFileForWrite)))) {
            String result = copyArrayToString();
            pw.write(result);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод чтения данных из файла
     * @param nameFileForRead
     */
    private void readFile(String nameFileForRead) {
        File file = new File(nameFileForRead);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {
            String str;
            int countRow = 0;
            while (((str = reader.readLine()) != null)) {
                String[] cells = str.split(" ");
                addRowCell(cells, countRow);
                countRow++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод записи массива в переменную типа String
     * @return
     */
    private String copyArrayToString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                stringBuffer.append(field[i][j] + " ");
            }
            stringBuffer.append("\n");
        }
        return String.valueOf(stringBuffer);
    }

    /**
     * Метод добавления строки после чтения из файла в массив field
     * @param cells
     * @param count
     */
    private void addRowCell(String[] cells, int count) {
        for (int i = 0; i < field.length; i++) {
            field[count][i] = Integer.parseInt(cells[i]);
        }
    }

    /**
     * Метод создания обновленного поля на каждой итерации
     * @return
     * @throws InterruptedException
     */
    private int[][] createNewField() throws InterruptedException {
        int[][] arrayTemp = new int[field.length][field.length];
        int mid = arrayTemp.length/2;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new MyRunnable(arrayTemp, 0, mid));
        executorService.submit(new MyRunnable(arrayTemp, mid, arrayTemp.length));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        /*Thread thread1 = new Thread();
        Thread thread2 = new Thread(new MyRunnable(arrayTemp, mid, arrayTemp.length));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return arrayTemp;
    }

    /**
     * Метод расчета количества соседей у каждой ячейки
     * @param rowY
     * @param colX
     * @return
     */
    private int countNeighbours(int rowY, int colX) {
        int countNeighbours = 0;
        int size = field.length;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int x = (i + rowY + size) % (size);
                int y = (j + colX + size) % (size);
                if (field[x][y] == 1 && (i != 0 || j != 0)) {
                    countNeighbours++;
                }
            }
        }
        return countNeighbours;
    }

    private void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    private void printArray(int[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }

    }

    /**
     * Класс Runnable, который выполняет в потоках алгоритм игры жизнь
     */
    public class MyRunnable implements Runnable {
        int[][] arrayTemp;
        int begin;
        int end;

        public MyRunnable(int[][] arrayTemp, int begin, int end){
            this.arrayTemp = arrayTemp;
            this.begin = begin;
            this.end = end;
        }

        int i = Integer.valueOf(String.valueOf(true));

        @Override
        public void run() {
            int countNeighbours = 0;
            for (int i = begin; i < end; i++) {
                for (int j = 0; j < arrayTemp.length; j++) {
                    countNeighbours = countNeighbours(i, j);
                    if (countNeighbours > 3 || countNeighbours < 2) {
                        arrayTemp[i][j] = 0;
                    } else if (field[i][j] == 0 && countNeighbours == 3) {
                        arrayTemp[i][j] = 1;
                    } else {
                        arrayTemp[i][j] = field[i][j];
                    }
                }
            }
        }
    }
}
