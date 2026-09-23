package mx.florinda;

import com.google.gson.Gson;

import java.math.BigDecimal;
import static mx.florinda.ItemCardapio.CategoriaCardapio.BEBIDAS;

public class Main {
    static void main() {
        ItemCardapio refrescoDoChaves = new ItemCardapio(1l, "Refresco do Chaves", """
                Suco de limão, que parece tamarindo ma tem gosto de groselha
                """, BEBIDAS, new BigDecimal("2.99"), null);

        Gson gson = new Gson();
        String json = gson.toJson(refrescoDoChaves);

        System.out.println(json);
    }
}
