package mx.florinda;

import java.util.*;

import static mx.florinda.ItemCardapio.CategoriaCardapio.*;

public class Main {
    static void main() {
        Database database = new Database();
        List<ItemCardapio> itens = database.listaDeItensCardapio();
        itens.forEach(System.out::println);

        System.out.println("--------");

        Optional<ItemCardapio> optionalItem = database.itemCardapioPorId(6L);
        String mensagem = optionalItem.
                map(ItemCardapio::toString)
                .orElse("Não encontrado");
        System.out.println(mensagem);

        System.out.println("--------");
        //Precisa manter as categorias que estão em promocao
        Set<ItemCardapio.CategoriaCardapio> categoriasPromocao = new TreeSet<>();
        categoriasPromocao.add(SOBREMESA);
        categoriasPromocao.add(ItemCardapio.CategoriaCardapio.ENTRADA);
        categoriasPromocao.forEach(System.out::println);

        System.out.println("--------");

        Set<ItemCardapio.CategoriaCardapio> categoriaCardapio2 = Set.of(SOBREMESA, ENTRADA);
        categoriaCardapio2.forEach(System.out::println);
    }
}
