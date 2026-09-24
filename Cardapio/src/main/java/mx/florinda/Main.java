package mx.florinda;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    static void main() {
       Database database = new Database();
       List<ItemCardapio> itens = database.listaDeItensCardapio();

        Set<ItemCardapio.CategoriaCardapio> categorias = new LinkedHashSet<>();

        //forma abreviada
        itens.stream()
                .map(ItemCardapio::categoria)
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .forEach(System.out::println);
    }
}
