package mx.florinda;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorItensCardapioComSocket {

    private static final Logger logger = Logger.getLogger(ServidorItensCardapioComSocket.class.getName());

    private static final Database database = new SQLDatabase();

    static void main() throws Exception {

        try (ExecutorService executorService = Executors.newFixedThreadPool(50)) { // limitar número de threads com pool de threads

            try (ServerSocket serverSocket = new ServerSocket(8000)) {
                logger.info("Subiu o servidor!");

                while (true) {
                    Socket clientScoket = serverSocket.accept();
                    executorService.execute(() -> trataRequisicao(clientScoket));
                }
            }
        }
    }

    private static void trataRequisicao(Socket clientScoket) {
        try (clientScoket) {
            InputStream clientIS = clientScoket.getInputStream();

            StringBuilder requestBuilder = new StringBuilder();

            int data;
            do {
                data = clientIS.read();
                requestBuilder.append((char) data);

            } while (clientIS.available() > 0);

            String request = requestBuilder.toString();
            logger.finest(request);
            logger.fine("\n\nChegou um novo request");

            Thread.sleep(250);

            //Separando a request por pedaços - metodo e uri
            String[] requestChunks = request.split("\r\n\r\n");
            String requestLineAndHeaders = requestChunks[0];
            String[] requestLineAndHeadersChunks = requestLineAndHeaders.split("\r\n\r\n");
            String requestLine = requestLineAndHeadersChunks[0];
            String[] requestLineChunks = requestLine.split(" ");

            // metodo (GET/POST)
            String method = requestLineChunks[0];
            String requestURI = requestLineChunks[1];
            String httpversion = requestLineChunks[2];

            logger.finer(() -> "Method: " + method); //usa o supplier(lambda) para fazer a concatenação apenas se o finner estiver habilitado (para evitar computação desnecessária)
            logger.finer(() -> "Request URI" + requestURI);
            logger.finer(() -> "Http Version" + httpversion);
            // uri

            OutputStream clientOS = clientScoket.getOutputStream();
            PrintStream clientOUt = new PrintStream(clientOS);

            try {

                if (method.equals("GET") && requestURI.equals("/itens-cardapio.json")) {
                    logger.fine("Chamou arquivo JSON");
                    Path path = Path.of("itensCardapio.json");
                    String json = Files.readString(path);

                    clientOUt.println("HTTP/1.1 200 OK");
                    clientOUt.println("Content-type: application/json; charset=UTF-8");
                    clientOUt.println();
                    clientOUt.println(json);
                } else if (method.equals("GET") && requestURI.equals("/itens-cardapio")) {
                    logger.fine("Chamou arquivo listagem de itens de cardápio");

                    List<ItemCardapio> listaItensCardapiotemCardapios = database.listaDeItensCardapio();

                    Gson gson = new Gson();
                    String json = gson.toJson(listaItensCardapiotemCardapios);

                    clientOUt.println("HTTP/1.1 200 OK");
                    clientOUt.println("Content-type: application/json; charset=UTF-8");
                    clientOUt.println();
                    clientOUt.println(json);
                } else if (method.equals("GET") && requestURI.equals("/itens-cardapio/total")) {
                    logger.fine("Chamou total de itens de cardápio");

                    List<ItemCardapio> listaItensCardapiotemCardapios = database.listaDeItensCardapio();

                    clientOUt.println("HTTP/1.1 200 OK");
                    clientOUt.println("Content-type: application/json; charset=UTF-8");
                    clientOUt.println();
                    int total = listaItensCardapiotemCardapios.size();
                    clientOUt.println(total);
                } else if (method.equals("POST") && requestURI.equals("/itens-cardapio")) {
                    logger.fine("Chamou adição de item de cardápio");

                    if (requestChunks.length == 1) {
                        clientOUt.println("HTTP/1.1 400 Bad Request");
                        return;
                    }
                    String body = requestChunks[1];

                    Gson gson = new Gson();
                    ItemCardapio novoItemCardapio = gson.fromJson(body, ItemCardapio.class);

                    logger.fine(() -> "Novo item cardapio: " + novoItemCardapio);

                    database.adicionaItemCardapio(novoItemCardapio);

                    clientOUt.println("HTTP/1.1 201 Created");

                } else if (method.equals("GET") && requestURI.startsWith("/itens-cardapio/")) {
                    logger.fine("Procurando um item pelo id");

                    String idTexto = requestURI.substring("/itens-cardapio/".length());
                    long id = Long.parseLong(idTexto);


                    Optional<ItemCardapio> itemCardapioPorId = database.itemCardapioPorId(id);
                    logger.fine(() -> "Item cardapio buscado pelo id: " + itemCardapioPorId);

                    if (itemCardapioPorId.isPresent()) {
                        String json = new Gson().toJson(itemCardapioPorId.get());

                        clientOUt.println("HTTP/1.1 200 ok");
                        clientOUt.println("Content-type: application/json; charset=UTF-8");
                        clientOUt.println();
                        clientOUt.println(json);
                    } else {
                        clientOUt.println("HTTP/1.1 404 Not Found");
                    }

                } else if (method.equals("PATCH") && requestURI.startsWith("/itens-cardapio/")) {
                    logger.fine("Alterando valor pelo id");

                    String idTexto = requestURI.substring("/itens-cardapio/".length());
                    long id = Long.parseLong(idTexto);

                    String body = requestChunks[1];
                    ItemCardapio itemRecebido = new Gson().fromJson(body, ItemCardapio.class);

                    BigDecimal preco = itemRecebido.preco();

                    if (database.alterarPrecoItemCardapio(id, preco)) {
                        logger.fine("Valor alterado");
                        clientOUt.println("HTTP/1.1 200 OK");
                    } else {
                        logger.warning("Não foi possível alterar o valor do item desejado");
                        clientOUt.println("HTTP/1.1 404 Not Found");
                    }
                } else if (method.equals("DELETE") && requestURI.startsWith("/itens-cardapio/")) {
                    logger.fine("Deletando item");

                    String idTexto = requestURI.substring("/itens-cardapio/".length());
                    long id = Long.parseLong(idTexto);

                    if (database.removerItemCardapio(id)) {
                        clientOUt.println("HTTP/1.1 200 OK");
                    } else {
                        clientOUt.println("HTTP/1.1 404 Not Found");
                    }
                } else if (method.equals("GET") && requestURI.equals("/")) {

                    List<ItemCardapio> listaDeItensCardapio = database.listaDeItensCardapio();


                    StringBuilder htmlTodosItens = new StringBuilder();
                    for (ItemCardapio item: listaDeItensCardapio){
                        String htmlPrecoItem;
                        if (item.precoComDesconto() == null){
                            htmlPrecoItem = "<strong>" + item.preco() + "</strong>";
                        }else {
                            htmlPrecoItem = "<mark>Em promoção</mark> <strong>" + item.precoComDesconto() + "</strong> <s>"+ item.preco() + "</s>";
                        }

                        String htmlItem = """
                                <article>
                                 <kbd>%s</kbd>
                                 <h3>%s</h3>
                                 <p>%s</p>
                                 %s
                                 </article>
                                
                                """.formatted(item.categoria().name(), item.nome(), item.descricao(), htmlPrecoItem);
                        htmlTodosItens.append(htmlItem);
                    }

                    String html = """
                            <!DOCTYPE html>
                            <html lang="en">
                            <head>
                             <meta charset="UTF-8">
                             <title>Florinda Eats - Cardápio</title>
                             <link rel="stylesheet"
                             href="https://cdn.jsdelivr.net/npm/@picocss/pico@2.1.1/css/pico.min.css">
                            </head>
                            <body>
                             <header class="container">
                             <hgroup>
                             <h1>Florinda Eats</h1>
                             <p>O sabor da Vila direto pra você</p>
                             </hgroup>
                             </header>
                             
                             %s
                             
                             <footer class="container">
                             <p><small><em>Preços de acordo com 30 de Agosto de 2025 15:18</em></small></p>
                             <p><strong>Florinda Eats</strong> Todos os direitos reservados - Agosto/2025</p>
                             </footer>
                            </body>
                            </html>
                            """.formatted(htmlTodosItens.toString());

                    clientOUt.print("HTTP/1.1 200 ok\r\n");
                    clientOUt.print("Content-type: text/html; charset=UTF-8\r\n\r\n");
                    clientOUt.println(html);
                    clientOUt.println("\r\n");

                } else {
                    logger.warning(() -> "URI não encontrada: " + request);
                    clientOUt.println("HTTP/1.1 404 Not Found");
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, ex, () -> "Erro ao tratar " + method + " " + request);
                clientOUt.println("HTTP/1.1 500 Internal Server Error");
                clientOUt.println("");
                clientOUt.println(ex.getMessage());
            }

        } catch (Exception ex) {
            //logger.severe("Erro no servidor");
            logger.log(Level.SEVERE, "Erro no servidor", ex);//faz qualquer nível do logger
            throw new RuntimeException(ex);
        }

    }
}
