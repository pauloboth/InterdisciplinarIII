package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produtoproducao")
public class ProdutoProducao implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    private Produto produto;

    @Id
    @ManyToOne
    @JoinColumn(name = "prd_id", referencedColumnName = "prd_id")
    private Producao producao;

    private int prp_quantidade;
    private String prp_notas;

    public ProdutoProducao() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Producao getProducao() {
        return producao;
    }

    public void setProducao(Producao producao) {
        this.producao = producao;
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
