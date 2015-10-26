package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class DespesaMes implements Serializable {

    @Id
    @SequenceGenerator(name = "dsm_id", sequenceName = "seq_dsm_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "dsm_id")
    private int dsm_id;
    private double dsm_valor;
    private String dsm_notas;
    private int dsm_quantidade;
    private Date dsm_data;
    private Date dsm_data_ref;

    @Id
    @ManyToOne
    @JoinColumn(name = "des_id", referencedColumnName = "des_id")
    private Despesa despesa;

    public DespesaMes() {
    }

    public int getDsm_id() {
        return dsm_id;
    }

    public void setDsm_id(int dsm_id) {
        this.dsm_id = dsm_id;
    }

    public double getDsm_valor() {
        return dsm_valor;
    }

    public void setDsm_valor(double dsm_valor) {
        this.dsm_valor = dsm_valor;
    }

    public String getDsm_notas() {
        return dsm_notas;
    }

    public void setDsm_notas(String dsm_notas) {
        this.dsm_notas = dsm_notas;
    }

    public int getDsm_quantidade() {
        return dsm_quantidade;
    }

    public void setDsm_quantidade(int dsm_quantidade) {
        this.dsm_quantidade = dsm_quantidade;
    }

    public Date getDsm_data() {
        return dsm_data;
    }

    public void setDsm_data(Date dsm_data) {
        this.dsm_data = dsm_data;
    }

    public Date getDsm_data_ref() {
        return dsm_data_ref;
    }

    public void setDsm_data_ref(Date dsm_data_ref) {
        this.dsm_data_ref = dsm_data_ref;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }
    
    
}
