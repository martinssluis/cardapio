package mx.florinda;

public class Main {
    static void main() {
        Database database = new Database();

        HistoricoVisualizacao historico = new HistoricoVisualizacao(database);
        historico.registrarVisualizacao(1l);
        historico.registrarVisualizacao(2l);
        historico.registrarVisualizacao(4l);
        historico.registrarVisualizacao(6l);

        historico.mostrarTotalVitualizados();
        historico.listaVisualizacoes();
    }
}
