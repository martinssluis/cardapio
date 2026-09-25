package mx.florinda;

import java.math.BigDecimal;

public class Main {
    static void main() throws InterruptedException {
        Database database = new Database();

        // precisa alterar o preço de um item1 do cardapio

        ItemCardapio item = database.itemCardapioPorId(1L).orElseThrow();
        System.out.printf("\n%s (%d) R$ $%s", item.nome(), item.id(), item.preco());

       database.alterarPrecoItemCardapio(1L, new BigDecimal("3.99"));
        ItemCardapio item1 = database.itemCardapioPorId(1L).orElseThrow();
        System.out.printf("\n%s (%d) R$ $%s", item1.nome(), item1.id(), item1.preco());
        
        // precis auditar as mudanças de preco de itens do cardapio
        database.imprimirRastroAuditoriaPrecos();

    }
}
