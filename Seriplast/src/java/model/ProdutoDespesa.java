package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produtodespesa")
public class ProdutoDespesa implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    private Produto produto;

    @Id
    @ManyToOne
    @JoinColumn(name = "des_id", referencedColumnName = "des_id")
    private Despesa despesa;
    private int prd_por_part;
    private String prd_notas;

    public ProdutoDespesa() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public int getPrd_por_part() {
        return prd_por_part;
    }

    public void setPrd_por_part(int prd_por_part) {
        this.prd_por_part = prd_por_part;
    }

    public String getPrd_notas() {
        return prd_notas;
    }

    public void setPrd_notas(String prd_notas) {
        this.prd_notas = prd_notas;
    }

}
