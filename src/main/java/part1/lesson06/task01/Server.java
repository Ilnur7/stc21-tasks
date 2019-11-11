package part1.lesson06.task01;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Server {

    private static InputStream inputStream; // поток чтения из сокета
    private static OutputStream outputStream; // поток записи в сокет
    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    public static void main(String[] args) throws Throwable {

        int count = 0;

        serverSocket = new ServerSocket(8000); //создает серверное соединение для слушания порта
        System.out.println("Server started");

        while (true) { // после получения запроса accept завершается поэтому каждый раз вызываем заново в цикле

            clientSocket = serverSocket.accept();//начинает слушать порт и создает сокет для клиента
            System.out.println("Client accepted " + (++count));

            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();

            boolean isGetRequest = isGetRequestFromInputHeaders();
            writeResponse(outputStream, isGetRequest);

            clientSocket.close();
        }
    }

    private static void writeResponse(OutputStream os, boolean isGetRequest) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os); //сюда запишем стрингу для рендеринга страницы
        String nameError = (isGetRequest) ? "200 OK" : "404 Not Found";
        String response = "HTTP/1.1 " + nameError + "\r\n" +
                "Content-Type: text/html\r\n\r\n";
        String stringForRenderingPage = getFileNameFromRoot();
        outputStreamWriter.write(response + stringForRenderingPage); //клиент отправляет данные на сервер
        outputStreamWriter.flush();

        outputStreamWriter.close();
    }

    private static String getFileNameFromRoot() throws IOException {
        String string = System.getProperty("user.dir");
        /*List<String> listFileNameFromDir = Files.list(Paths.get(string))
                .map(Path::toString)
                .collect(Collectors.toList());*/

        File thisFolder = new File(string);
        File[] files = thisFolder.listFiles();
        List<File> lst = Arrays.asList(files);
        StringBuffer stringListFileNames = new StringBuffer("");
        for (File file : lst) {
            String s = "<li>" + file.getName() + "</li>";
            stringListFileNames.append(s);
        }
        String result = "<ol>" + stringListFileNames + "</ol>";
        return result;
    }

    private static boolean isGetRequestFromInputHeaders() throws Throwable {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String strHeader = bufferedReader.readLine();
            return (strHeader.contains("GET")) ? true : false;
        }
    }
}
