package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produtomaquina")
public class ProdutoMaquina implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    private Produto produto;

    @Id
    @ManyToOne
    @JoinColumn(name = "maq_id", referencedColumnName = "maq_id")
    private Maquina maquina;

    private int prm_tem_pro;
    private String prm_notas;

    public ProdutoMaquina() {
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public int getPrm_tem_pro() {
        return prm_tem_pro;
    }

    public void setPrm_tem_pro(int prm_tem_pro) {
        this.prm_tem_pro = prm_tem_pro;
    }

    public String getPrm_notas() {
        return prm_notas;
    }

    public void setPrm_notas(String prm_notas) {
        this.prm_notas = prm_notas;
    }

}
