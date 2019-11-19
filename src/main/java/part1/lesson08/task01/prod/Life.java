package part1.lesson08.task01.prod;

import java.io.*;
import java.util.Scanner;

public class Life {
    private static int[][] field = new int[40][40];

    public void start(String nameFileForRead, String nameFileForWrite, int countIteration) throws InterruptedException {

        readFile(nameFileForRead);
        updateField(countIteration);
        writeInFile(nameFileForWrite);
    }

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

    private void writeInFile(String nameFileForWrite) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nameFileForWrite)))) {
            String result = copyArrayToString();
            pw.write(result);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void addRowCell(String[] cells, int count) {
        for (int i = 0; i < field.length; i++) {
            field[count][i] = Integer.parseInt(cells[i]);
        }
    }

    private int[][] createNewField() {
        int[][] arrayTemp = new int[field.length][field.length];

        for (int i = 0; i < arrayTemp.length; i++) {
            for (int j = 0; j < arrayTemp.length; j++) {
                int countNeighbours = countNeighbours(i, j);
                if (countNeighbours > 3 || countNeighbours < 2) {
                    arrayTemp[i][j] = 0;
                } else if (field[i][j] == 0 && countNeighbours == 3) {
                    arrayTemp[i][j] = 1;
                } else {
                    arrayTemp[i][j] = field[i][j];
                }
            }
        }
        return arrayTemp;
    }

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
}
