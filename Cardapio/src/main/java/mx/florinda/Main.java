package mx.florinda;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static void main() {
       Database database = new Database();
       List<ItemCardapio> itens = database.listaDeItensCardapio();

       Comparator<ItemCardapio.CategoriaCardapio> comparadorPorNome = Comparator
               .comparing(ItemCardapio.CategoriaCardapio::name);

       Set<ItemCardapio.CategoriaCardapio> categorias = new TreeSet<>(comparadorPorNome);

        for(ItemCardapio item : itens){
            ItemCardapio.CategoriaCardapio categoria = item.categoria();
            categorias.add(categoria);
        }
        for(ItemCardapio.CategoriaCardapio categoria : categorias){
            System.out.println(categoria);
        }

        System.out.println("-----");
        itens.stream()
                .map(ItemCardapio::categoria)
                //.collect(Collectors.toCollection(TreeSet::new)) // ordem natural que foi listada no enum
                .collect(Collectors.toCollection(()-> new TreeSet<>(comparadorPorNome))) // ordena pelo nome
                .forEach(System.out::println);
    }
}
