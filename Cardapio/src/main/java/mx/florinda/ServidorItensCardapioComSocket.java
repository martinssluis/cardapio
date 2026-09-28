package mx.florinda;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorItensCardapioComSocket {
    private static final Database database = new InMemoryDatabase();

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
            System.out.println("-----------------------------------");
            System.out.println(request);
            System.out.println("\n\nChegou um novo request");

            Thread.sleep(250);

            //Separando a request por pedaços - metodo e uri
            String[] requestChunks = request.split("\r\n\r\n");
            String requestLineAndHeaders = requestChunks[0];
            String[] requestLineAndHeadersChunks = requestLineAndHeaders.split("\r\n\r\n");
            String requestLine = requestLineAndHeadersChunks[0];
            String[] requestLineChunks = requestLine.split(" ");

            // metodo (GET/POST)
            String method = requestLineChunks[0];
            String requestURI = requestLineChunks[1];

            System.out.println(method);
            System.out.println(requestURI);
            // uri

            OutputStream clientOS = clientScoket.getOutputStream();
            PrintStream clientOUt = new PrintStream(clientOS);

            if(method.equals("GET") && requestURI.equals("/itens-cardapio.json")) {
                System.out.println("Chamou arquivo JSON");
                Path path = Path.of("itensCardapio.json");
                String json = Files.readString(path);

                clientOUt.println("HTTP/1.1 200 OK");
                clientOUt.println("Content-type: application/json; charset=UTF-8");
                clientOUt.println();
                clientOUt.println(json);
            } else if (method.equals("GET") && requestURI.equals("/itens-cardapio")) {
                System.out.println("Chamou arquivo listagem de itens de cardápio");

                List<ItemCardapio> listaItensCardapiotemCardapios = database.listaDeItensCardapio();

                Gson gson = new Gson();
                String json = gson.toJson(listaItensCardapiotemCardapios);

                clientOUt.println("HTTP/1.1 200 OK");
                clientOUt.println("Content-type: application/json; charset=UTF-8");
                clientOUt.println();
                clientOUt.println(json);
            } else if (method.equals("GET") && requestURI.equals("/itens-cardapio/total")) {
                System.out.println("Chamou total de itens de cardápio");

                List<ItemCardapio> listaItensCardapiotemCardapios = database.listaDeItensCardapio();

                Gson gson = new Gson();
                String json = gson.toJson(listaItensCardapiotemCardapios);

                clientOUt.println("HTTP/1.1 200 OK");
                clientOUt.println("Content-type: application/json; charset=UTF-8");
                clientOUt.println();
                int total = listaItensCardapiotemCardapios.size();
                clientOUt.println(total);
            }else if (method.equals("POST") && requestURI.equals("/itens-cardapio")) {
                System.out.println("Chamou adição de item de cardápio");

                if (requestChunks.length ==1){
                    clientOUt.println("HTTP/1.1 400 Bad Request");
                    return;
                }
                String body = requestChunks[1];

                Gson gson = new Gson();
                ItemCardapio novoItemCardapio = gson.fromJson(body, ItemCardapio.class);

                System.out.println(novoItemCardapio);

                database.adicionaItemCardapio(novoItemCardapio);

                clientOUt.println("HTTP/1.1 201 Created");
            }else{
                System.out.println("URI não encontrada: " + request);
                clientOUt.println("HTTP/1.1 404 Not Found");
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
