package mx.florinda;

import java.math.BigDecimal;

import static mx.florinda.ItemCardapio.CategoriaCardapio.BEBIDAS;

public class Main {
    static void main() throws InterruptedException {
        Database database = new Database();

        // precisa alterar o preço de um item1 do cardapio


        database.alterarPrecoItemCardapio(1L, new BigDecimal("3.99")); // 2.99 - 3.99
        database.alterarPrecoItemCardapio(1L, new BigDecimal("2.99")); // 3.99 - 2.99
        database.alterarPrecoItemCardapio(1L, new BigDecimal("4.99")); // 2.99 - 4.99

        ItemCardapio item1 = new ItemCardapio(1L, "Refresco", "", BEBIDAS, new BigDecimal("2.99"), null);
        ItemCardapio item2 = new ItemCardapio(1L, "Refresco", "", BEBIDAS, new BigDecimal("3.99"), null);
        ItemCardapio item3 = new ItemCardapio(1L, "Refresco", "", BEBIDAS, new BigDecimal("2.99"), null);

        // Identity
        System.out.println("item1 == item2: " + (item1 == item2));
        System.out.println("item1 == item3: " + (item1 == item3));

        System.out.println("item1 == item2: (hashcode)" + (item1.hashCode() == item2.hashCode()));
        System.out.println("item1 == item3: (hashcode)" + (item1.hashCode() == item3.hashCode()));

        System.out.println("item1 equals item2: " + (item1.equals(item2)));
        System.out.println("item1 equals item3: " + (item1.equals(item3)));



        // precis auditar as mudanças de preco de itens do cardapio
        database.imprimirRastroAuditoriaPrecos();

    }
}
