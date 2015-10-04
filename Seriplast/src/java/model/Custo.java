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
    private int cus_quant_prod;//Qunantidade produzida
    private Date cus_cadastro;
    private double cus_preco_unitario;
    @Id
    @ManyToOne
    @JoinColumn(name = "pro_id", referencedColumnName = "pro_id")
    private Produto produto;

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

    public int getCus_quant_prod() {
        return cus_quant_prod;
    }

    public void setCus_quant_prod(int cus_quant_prod) {
        this.cus_quant_prod = cus_quant_prod;
    }

    public Date getCus_cadastro() {
        return cus_cadastro;
    }

    public void setCus_cadastro(Date cus_cadastro) {
        this.cus_cadastro = cus_cadastro;
    }

    public double getCus_preco_unitario() {
        return cus_preco_unitario;
    }

    public void setCus_preco_unitario(double cus_preco_unitario) {
        this.cus_preco_unitario = cus_preco_unitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }


}
