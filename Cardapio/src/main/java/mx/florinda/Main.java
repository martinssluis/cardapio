package mx.florinda;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static void main() {
        Database database = new Database();
        List<ItemCardapio> itens = database.listaDeItensCardapio();
        itens.forEach(System.out::println);

        System.out.println("--------");

        Optional<ItemCardapio> optionalItem = database.itemCardapioPorId(6L);
        String mensagem = optionalItem.map(ItemCardapio::toString).orElse("Não encontrado");
        System.out.println(mensagem);

        if (optionalItem.isPresent()) {
            ItemCardapio item = optionalItem.get();
            System.out.println(item);
        } else{
            System.out.println("Não encontrado");
        }
    }
}
