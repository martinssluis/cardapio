package mx.florinda;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class SQLDatabase implements Database{
    @Override
    public List<ItemCardapio> listaDeItensCardapio() {
        return List.of();
    }

    @Override
    public Optional<ItemCardapio> itemCardapioPorId(Long itemID) {
        return Optional.empty();
    }

    @Override
    public boolean removerItemCardapio(Long itemId) {
        return false;
    }

    @Override
    public boolean alterarPrecoItemCardapio(Long itemId, BigDecimal novoPreco) {
        return false;
    }

    @Override
    public int totalItensCardapio() {
        return 0;
    }

    @Override
    public void adicionaItemCardapio(ItemCardapio itemCardapio) {

    }
}
