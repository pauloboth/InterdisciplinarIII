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
    private int cus_mes;

    @Id
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    private Produto produto;

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

    public int getCus_mes() {
        return cus_mes;
    }

    public void setCus_mes(int cus_mes) {
        this.cus_mes = cus_mes;
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

    public String getMes() {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        String mes = "";
        if (this.cus_mes <= 12 && this.cus_mes > 0) {
            mes = meses[this.cus_mes - 1];
        }
        return mes;
    }
}
