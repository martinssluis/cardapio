package mx.florinda;

import java.util.*;

import static mx.florinda.ItemCardapio.CategoriaCardapio.*;

public class Main {
    static void main() {
        Database database = new Database();
        List<ItemCardapio> itens = database.listaDeItensCardapio();
        itens.forEach(System.out::println);

        Set<ItemCardapio.CategoriaCardapio> categoriasCardapio = EnumSet.of(SOBREMESA, ENTRADA);
        categoriasCardapio.forEach(System.out::println);

        // Preciso das descrições associadasas categorias em promocao
        Map<ItemCardapio.CategoriaCardapio, String> promocoes = new EnumMap<>(ItemCardapio.CategoriaCardapio.class);
        promocoes.put(SOBREMESA, "O doce perfeito para você");
        promocoes.put(ENTRADA, "come sua refeição com um toque de sabor");
        System.out.println(promocoes);
    }
}
