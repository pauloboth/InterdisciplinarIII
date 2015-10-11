package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "despesa")
public class Despesa implements Serializable {

    @Id
    @SequenceGenerator(name = "des_id", sequenceName = "seq_des_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "des_id")
    private int des_id;
    private String des_nome;
    private int des_tipo;//se é com depreciação, sem depreciação, se é de luz
    //1 se for luz, 2 se for com depreciação, 0 qualquer outra criada
    private int des_depr_mes;//quantidade de meses
    private double des_valor_depr;
    private Date des_inicio_depr;
    private int des_status;
    private int des_tipo_maq;//tipo máquina ou outro bem
    private int des_gasto_hora;//quantidade que gasta por hora
    private String des_notas;
    @OneToMany(mappedBy = "despesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoDespesa> lsProdutoDespesa;

    public Despesa() {
    }

    public int getDes_id() {
        return des_id;
    }

    public void setDes_id(int des_id) {
        this.des_id = des_id;
    }

    public String getDes_nome() {
        return des_nome;
    }

    public void setDes_nome(String des_nome) {
        this.des_nome = des_nome;
    }

    public int getDes_tipo() {
        return des_tipo;
    }

    public void setDes_tipo(int des_tipo) {
        this.des_tipo = des_tipo;
    }

    public int getDes_depr_mes() {
        return des_depr_mes;
    }

    public void setDes_depr_mes(int des_depr_mes) {
        this.des_depr_mes = des_depr_mes;
    }

    public double getDes_valor_depr() {
        return des_valor_depr;
    }

    public void setDes_valor_depr(double des_valor_depr) {
        this.des_valor_depr = des_valor_depr;
    }

    public Date getDes_inicio_depr() {
        return des_inicio_depr;
    }

    public void setDes_inicio_depr(Date des_inicio_depr) {
        this.des_inicio_depr = des_inicio_depr;
    }

    public int getDes_status() {
        return des_status;
    }

    public void setDes_status(int des_status) {
        this.des_status = des_status;
    }

    public int getDes_tipo_maq() {
        return des_tipo_maq;
    }

    public void setDes_tipo_maq(int des_tipo_maq) {
        this.des_tipo_maq = des_tipo_maq;
    }

    public int getDes_gasto_hora() {
        return des_gasto_hora;
    }

    public void setDes_gasto_hora(int des_gasto_hora) {
        this.des_gasto_hora = des_gasto_hora;
    }

    public String getDes_notas() {
        return des_notas;
    }

    public void setDes_notas(String des_notas) {
        this.des_notas = des_notas;
    }

    public List<ProdutoDespesa> getLsProdutoDespesa() {
        return lsProdutoDespesa;
    }

    public void setLsProdutoDespesa(List<ProdutoDespesa> lsProdutoDespesa) {
        this.lsProdutoDespesa = lsProdutoDespesa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.des_id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Despesa other = (Despesa) obj;
        if (this.des_id != other.des_id) {
            return false;
        }
        return true;
    }

    public String toString() {
        return getDes_nome();
    }

}
