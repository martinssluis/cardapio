package mx.florinda;

import java.math.BigDecimal;
import java.util.*;

import static mx.florinda.ItemCardapio.CategoriaCardapio.*;

public class Database {

    private final Map<Long, ItemCardapio> itensPorId = new HashMap<>();
    private final Map<ItemCardapio, BigDecimal> auditoriaPrecos = new IdentityHashMap<>();

    public Database() {
        var refrescoDoChaves = new ItemCardapio(1L, "Refresco do Chaves",
                "Suco de limão que parece de tamarindo e tem gosto de groselha.",
                BEBIDAS, new BigDecimal("2.99"), null);
        itensPorId.put(1L, refrescoDoChaves);

        var sanduicheDoChaves = new ItemCardapio(2L, "Sanduíche de Presunto do Chaves",
                "Sanduíche de presunto simples, mas feito com muito amor.",
                PRATOS_PRINCIPAIS, new BigDecimal("3.50"), new BigDecimal("2.99"));
        itensPorId.put(2L, sanduicheDoChaves);

        var tortaDaDonaFlorinda = new ItemCardapio(5L, "Torta de Frango da Dona Florinda",
                "Torta de frango com recheio cremoso e massa crocante.",
                PRATOS_PRINCIPAIS, new BigDecimal("12.99"), new BigDecimal("10.99"));
        itensPorId.put(5L, tortaDaDonaFlorinda);

        var pipocaDoQuico = new ItemCardapio(6L, "Pipoca do Quico",
                "Balde de pipoca preparado com carinho pelo Quico.",
                PRATOS_PRINCIPAIS, new BigDecimal("4.99"), new BigDecimal("3.99"));
        itensPorId.put(6L, pipocaDoQuico);

        var aguaDeJamaica = new ItemCardapio(7L, "Água de Jamaica",
                "Água aromatizada com hibisco e toque de açúcar.",
                BEBIDAS, new BigDecimal("2.50"), new BigDecimal("2.00"));
        itensPorId.put(7L, aguaDeJamaica);

        var churrosDoChaves = new ItemCardapio(9L, "Churros do Chaves",
                "Churros recheados com doce de leite, clássicos e irresistíveis.",
                SOBREMESA, new BigDecimal("4.99"), new BigDecimal("3.99"));
        itensPorId.put(9L, churrosDoChaves);
    }

    public List<ItemCardapio> listaDeItensCardapio() {
        return new ArrayList<>(itensPorId.values());

    }

    public Optional<ItemCardapio> itemCardapioPorId(Long itemID){
        ItemCardapio itemCardapio = itensPorId.get(itemID);
        return Optional.ofNullable(itemCardapio);
    }

    public boolean removerItemCardapio(Long itemId) {
        ItemCardapio itemCardapioRemovido = itensPorId.remove(itemId);
        return itemCardapioRemovido!= null;
    }

    public boolean alterarPrecoItemCardapio(Long itemId, BigDecimal novoPreco) {
        ItemCardapio itemAntigo = itensPorId.get(itemId);
        if (itemId == null){
            return false;
        }
        ItemCardapio itemComPrecoAlterado = itemAntigo.alterarPreco(novoPreco);
        itensPorId.put(itemId, itemComPrecoAlterado);
        auditoriaPrecos.put(itemAntigo, novoPreco);
        return true;
    }

    public void imprimirRastroAuditoriaPrecos(){
        System.out.println("\n Auditoria de preços");
        auditoriaPrecos.forEach((itemAntigo, novoPreco)->
                System.out.printf("- %s: %s => %s\n", itemAntigo.nome(), itemAntigo.preco(), novoPreco));
        System.out.println();
    }
}