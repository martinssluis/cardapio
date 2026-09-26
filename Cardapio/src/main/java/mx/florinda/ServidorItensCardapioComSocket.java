package mx.florinda;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServidorItensCardapioComSocket {
    static void main() throws Exception {

        try(ServerSocket serverSocket = new ServerSocket(8000)){
            System.out.println("Subiu o servidor!");

            while(true){
                try(Socket clientScoket = serverSocket.accept()){
                    InputStream clientIS = clientScoket.getInputStream();

                    StringBuilder requestBuilder = new StringBuilder();

                    int data;
                    do{
                        data = clientIS.read();
                        requestBuilder.append((char) data);

                    }while (clientIS.available() >0);

                    String request = requestBuilder.toString();
                    System.out.println(request);

                    Path path = Path.of("itensCardapio.json");
                    String json = Files.readString(path);

                    OutputStream clientOS = clientScoket.getOutputStream();
                    PrintStream clientOUt = new PrintStream(clientOS);

                    clientOUt.println("HTTP/1.1 200 OK");
                    clientOUt.println("Content-type: application/json; charset=UTF-8");
                    clientOUt.println();
                    clientOUt.println(json);
                }
            }
            }


    }
}
