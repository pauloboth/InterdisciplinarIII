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
    private int pds_tem_pro;//no banco salvo em segundos
    private int pds_porc_part;//a porcentagem de que o valor da despesa faz parte
    private String pds_notas;

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

    public int getPds_tem_pro() {
        return pds_tem_pro;
    }

    public void setPds_tem_pro(int pds_tem_pro) {
        this.pds_tem_pro = pds_tem_pro;
    }

    public int getPds_porc_part() {
        return pds_porc_part;
    }

    public void setPds_porc_part(int pds_porc_part) {
        this.pds_porc_part = pds_porc_part;
    }

    public String getPds_notas() {
        return pds_notas;
    }

    public void setPds_notas(String pds_notas) {
        this.pds_notas = pds_notas;
    }


}
