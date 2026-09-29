package mx.florinda;

import java.math.BigDecimal;
import java.util.List;

import static mx.florinda.ItemCardapio.CategoriaCardapio.BEBIDAS;
import static mx.florinda.ItemCardapio.CategoriaCardapio.PRATOS_PRINCIPAIS;

public class Main {
    static void main(){

        SQLDatabase database = new SQLDatabase();

        List<ItemCardapio> listaItensCardapio = database.listaDeItensCardapio();
        listaItensCardapio.forEach(System.out::println);

        int total = database.totalItensCardapio();
        System.out.println(total);

//        var novoItemCardapio = new ItemCardapio(10L, "Tacos de Carnitas", "Tacos recheados com carne tenra", PRATOS_PRINCIPAIS, new BigDecimal("25.9"), null);
//
//        database.adicionaItemCardapio(novoItemCardapio);

        database.itemCardapioPorId(10L);
        database.alterarPrecoItemCardapio(10L, new BigDecimal("1.99"));
    }
}
