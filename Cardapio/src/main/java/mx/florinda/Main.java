package mx.florinda;

import java.math.BigDecimal;
import java.util.List;

import static mx.florinda.ItemCardapio.CategoriaCardapio.BEBIDAS;

public class Main {
    static void main(){

        SQLDatabase database = new SQLDatabase();

        List<ItemCardapio> listaItensCardapio = database.listaDeItensCardapio();
        listaItensCardapio.forEach(System.out::println);

        int total = database.totalItensCardapio();
        System.out.println(total);
    }
}
