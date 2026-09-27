package mx.florinda;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorItensCardapioComSocket {
    static void main() throws Exception {

        try (ExecutorService executorService = Executors.newFixedThreadPool(50)) { // limitar número de threads com pool de threads

            try (ServerSocket serverSocket = new ServerSocket(8000)) {
                System.out.println("Subiu o servidor!");

                while (true) {
                    Socket clientScoket = serverSocket.accept();
                    executorService.execute(() -> trataRequisicao(clientScoket));
                }
            }
        }
    }

    private static void trataRequisicao(Socket clientScoket) {
        try (clientScoket) {
            InputStream clientIS = clientScoket.getInputStream();

            StringBuilder requestBuilder = new StringBuilder();

            int data;
            do {
                data = clientIS.read();
                requestBuilder.append((char) data);

            } while (clientIS.available() > 0);

            String request = requestBuilder.toString();
            System.out.println(request);

            Thread.sleep(250);

            Path path = Path.of("itensCardapio.json");
            String json = Files.readString(path);

            OutputStream clientOS = clientScoket.getOutputStream();
            PrintStream clientOUt = new PrintStream(clientOS);

            clientOUt.println("HTTP/1.1 200 OK");
            clientOUt.println("Content-type: application/json; charset=UTF-8");
            clientOUt.println();
            clientOUt.println(json);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
