package mx.florinda;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Pix implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private BigDecimal valor;
    private String chaveDestino;

    public Pix(Long id, BigDecimal valor, String chaveDestino) {
        this.id = id;
        this.valor = valor;
        this.chaveDestino = chaveDestino;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getChaveDestino() {
        return chaveDestino;
    }

    public void setChaveDestino(String chaveDestino) {
        this.chaveDestino = chaveDestino;
    }

    @Override
    public String toString() {
        return "Pix{" +
                "id=" + id +
                ", valor=" + valor +
                ", chaveDestino='" + chaveDestino + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pix pix = (Pix) o;
        return Objects.equals(id, pix.id) && Objects.equals(valor, pix.valor) && Objects.equals(chaveDestino, pix.chaveDestino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, chaveDestino);
    }
}
