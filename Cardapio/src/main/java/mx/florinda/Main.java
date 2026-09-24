package mx.florinda;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static void main() {
       Database database = new Database();
       List<ItemCardapio> itens = database.listaDeItensCardapio();

       Map<ItemCardapio.CategoriaCardapio, Integer> itensPorCategoria = new LinkedHashMap<>();
       for(ItemCardapio item : itens){
          ItemCardapio.CategoriaCardapio categoria = item.categoria();
          if(!itensPorCategoria.containsKey(categoria)){
              itensPorCategoria.put(categoria,1);
          } else{
             Integer quantidadeAnterior = itensPorCategoria.get(categoria);
             itensPorCategoria.put(categoria, quantidadeAnterior +1);
          }
       }

        System.out.println(itensPorCategoria);

        System.out.println("-------");

        itens.stream()
                .collect(Collectors.groupingBy(
                        ItemCardapio::categoria,
                        LinkedHashMap::new,
                        Collectors.counting()
                ))
                .forEach((chave, valor)-> System.out.println(chave + "->" + valor));
    }
}
