package mx.florinda;

public class Main {
    static void main() throws InterruptedException {
        Database database = new Database();

        HistoricoVisualizacao historico = new HistoricoVisualizacao(database);
        historico.registrarVisualizacao(1l);
        historico.registrarVisualizacao(2l);
        historico.registrarVisualizacao(4l);
        historico.registrarVisualizacao(6l);

        historico.mostrarTotalVitualizados();
        historico.listaVisualizacoes();

        Long idParaRemover = 1L;
        boolean removido = database.removerItemCardapio(idParaRemover);

        if (removido){
            System.out.println("Item removido: " + idParaRemover);
        } else{
            System.out.println("Item não encontrado: " + idParaRemover);
        }

        database.listaDeItensCardapio().forEach(System.out::println);

        System.out.println("Solicitando garbage collector ...");
        System.gc();
        Thread.sleep(500);

        historico.mostrarTotalVitualizados();
        historico.listaVisualizacoes();
    }
}
