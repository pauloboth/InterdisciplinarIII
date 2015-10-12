package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produtopedido")
public class ProdutoPedido implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    private Produto produto;

    @Id
    @ManyToOne
    @JoinColumn(name = "ped_id", referencedColumnName = "ped_id")
    private Pedido pedido;

    private int prp_quantidade;
    private String prp_notas;

    public ProdutoPedido() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getPrp_quantidade() {
        return prp_quantidade;
    }

    public void setPrp_quantidade(int prp_quantidade) {
        this.prp_quantidade = prp_quantidade;
    }

    public String getPrp_notas() {
        return prp_notas;
    }

    public void setPrp_notas(String prp_notas) {
        this.prp_notas = prp_notas;
    }

}
