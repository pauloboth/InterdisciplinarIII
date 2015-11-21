package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "custo")
public class Custo implements Serializable {

    @Id
    @SequenceGenerator(name = "cus_id", sequenceName = "seq_cus_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cus_id")
    private int cus_id;
    private String cus_notas;
    private double cus_preco_produto;
    private Date cus_data;
    private Date cus_data_ref;

    @Id
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    private Produto produto;

//    @OneToMany(mappedBy = "custo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "custo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustoDespesa> lsCustoDespesa;

    public Custo() {
    }

    public int getCus_id() {
        return cus_id;
    }

    public void setCus_id(int cus_id) {
        this.cus_id = cus_id;
    }

    public String getCus_notas() {
        return cus_notas;
    }

    public void setCus_notas(String cus_notas) {
        this.cus_notas = cus_notas;
    }

    public double getCus_preco_produto() {
        return cus_preco_produto;
    }

    public void setCus_preco_produto(double cus_preco_produto) {
        this.cus_preco_produto = cus_preco_produto;
    }

    public Date getCus_data() {
        return cus_data;
    }

    public void setCus_data(Date cus_data) {
        this.cus_data = cus_data;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<CustoDespesa> getLsCustoDespesa() {
        return lsCustoDespesa;
    }

    public void setLsCustoDespesa(List<CustoDespesa> lsCustoDespesa) {
        this.lsCustoDespesa = lsCustoDespesa;
    }

    public Date getCus_data_ref() {
        return cus_data_ref;
    }

    public void setCus_data_ref(Date cus_data_ref) {
        this.cus_data_ref = cus_data_ref;
    }

}
