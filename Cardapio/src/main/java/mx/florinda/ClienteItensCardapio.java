package mx.florinda;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteItensCardapio {
    static void main() throws Exception {

        URI uri = URI.create("http://localhost:8000/itensCardapio.json");

        try(HttpClient httpClient = HttpClient.newHttpClient()){
            HttpRequest httpRequest = HttpRequest.newBuilder(uri).build();
           HttpResponse<String> httpResponse = httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());

            int satatusCode = httpResponse.statusCode();
            String body = httpResponse.body();
            System.out.println(satatusCode);
            System.out.println(body);
        }

    }
}
