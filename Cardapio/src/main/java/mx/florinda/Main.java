package mx.florinda;

import java.util.List;

public class Main {
    static void main() {
       Database database = new Database();

       List<ItemCardapio> itens = database.listaDeItensCardapio();

       for (ItemCardapio item : itens){
           System.out.println(item);
       }

       ItemCardapio itemCardapio = itens.get(4);
        System.out.println(itemCardapio.nome());

        System.out.println(itens.size());
        itens.remove(1);
        System.out.println(itens.size());

        itens.forEach(System.out::println);
    }
}
