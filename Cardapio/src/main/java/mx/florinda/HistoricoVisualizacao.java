package mx.florinda;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoricoVisualizacao {

    private final Database database;

    private final Map<ItemCardapio, LocalDateTime> visualizacoes = new HashMap<>();

    public HistoricoVisualizacao(Database database){
        this.database = database;
    }

    public void registrarVisualizacao(Long itemId) {
        Optional<ItemCardapio> optionalItemCardapio = database.itemCardapioPorId(itemId);
        if (optionalItemCardapio.isEmpty()) {
            System.out.println("Item não foi encontrado: " + itemId);
            return;
        }

        ItemCardapio itemCardapio = optionalItemCardapio.get();
        LocalDateTime agora = LocalDateTime.now();
        visualizacoes.put(itemCardapio, agora);
        System.out.printf("%s visualizado em %s\n", itemCardapio.nome(), agora);
    }

        public void mostrarTotalVitualizados(){
            System.out.println("\nTotal de itens visualizados: " + visualizacoes.size());
        }
        public  void listaVisualizacoes(){
            if(visualizacoes.isEmpty()){
                System.out.println("Nenhum item foi visualizado ainda");
                return;
            }
            System.out.println("\nHistórico de Visualização");
            visualizacoes.forEach((item, hora)->
                    System.out.printf(" - %s em %s\n", item.nome(), hora));
            System.out.println();
        }
}
