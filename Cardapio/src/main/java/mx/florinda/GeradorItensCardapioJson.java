package mx.florinda;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GeradorItensCardapioJson {
    static void main() throws IOException {
        Database database = new Database();
        List<ItemCardapio> listaItensCardapio = database.listaDeItensCardapio();

        Gson gson = new Gson();
        String json = gson.toJson(listaItensCardapio);

        Path path = Path.of("itensCardapio.json");
        Files.writeString(path, json);

    }

}
