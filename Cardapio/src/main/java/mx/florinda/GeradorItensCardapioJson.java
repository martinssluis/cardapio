package mx.florinda;

import com.google.gson.Gson;

import java.util.List;

public class GeradorItensCardapioJson {
    static void main() {
        Database database = new Database();
        List<ItemCardapio> listaItensCardapio = database.listaDeItensCardapio();

        Gson gson = new Gson();
        String json = gson.toJson(listaItensCardapio);

        System.out.println(json);
    }

}
