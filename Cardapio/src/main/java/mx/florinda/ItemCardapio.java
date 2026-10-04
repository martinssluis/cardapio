package mx.florinda;

import java.io.Serializable;
import java.math.BigDecimal;

public record ItemCardapio(Long id, String nome, String descricao, CategoriaCardapio categoria, BigDecimal preco,
                           BigDecimal precoComDesconto)
                            implements Serializable {

    public ItemCardapio alterarPreco(BigDecimal novoPreco) {
        return new ItemCardapio(id, nome, descricao, categoria, novoPreco, precoComDesconto);
    }

    public enum CategoriaCardapio {
        ENTRADA, PRATOS_PRINCIPAIS, BEBIDAS, SOBREMESA
    }

}
