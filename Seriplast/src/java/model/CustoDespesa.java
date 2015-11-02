package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "custodespesa")
public class CustoDespesa implements Serializable {

    @Id
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "cus_id", referencedColumnName = "cus_id"),
        @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    })
    private Custo custo;

    @Id
    @ManyToOne
    @JoinColumn(name = "des_id", referencedColumnName = "des_id")
    private Despesa despesa;

    private Date csd_data;
    private String csd_notas;
    private double csd_valor;

    public CustoDespesa() {
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public Custo getCusto() {
        return custo;
    }

    public void setCusto(Custo custo) {
        this.custo = custo;
    }

    public Date getCsd_data() {
        return csd_data;
    }

    public void setCsd_data(Date csd_data) {
        this.csd_data = csd_data;
    }

    public String getCsd_notas() {
        return csd_notas;
    }

    public void setCsd_notas(String csd_notas) {
        this.csd_notas = csd_notas;
    }

    public double getCsd_valor() {
        return csd_valor;
    }

    public void setCsd_valor(double csd_valor) {
        this.csd_valor = csd_valor;
    }

}
