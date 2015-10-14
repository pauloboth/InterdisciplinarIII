package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Pedido implements Serializable {

    @Id
    @SequenceGenerator(name = "ped_id", sequenceName = "seq_ped_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ped_id")
    private int ped_id;
    private int ped_mes;
    private String ped_cliente;
    private Date ped_data;
    private String ped_notas;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProdutoPedido> lsProdutoPedido;

    public Pedido() {
    }

    public int getPed_id() {
        return ped_id;
    }

    public void setPed_id(int ped_id) {
        this.ped_id = ped_id;
    }

    public int getPed_mes() {
        if (ped_id == 0 && ped_mes == 0) {
            Date d = new Date();
            ped_mes = d.getMonth() + 1;
        }
        return ped_mes;
    }

    public void setPed_mes(int ped_mes) {
        this.ped_mes = ped_mes;
    }

    public String getPed_cliente() {
        return ped_cliente;
    }

    public void setPed_cliente(String ped_cliente) {
        this.ped_cliente = ped_cliente;
    }

    public Date getPed_data() {
        return ped_data;
    }

    public void setPed_data(Date ped_data) {
        this.ped_data = ped_data;
    }

    public String getPed_notas() {
        return ped_notas;
    }

    public void setPed_notas(String ped_notas) {
        this.ped_notas = ped_notas;
    }

    public List<ProdutoPedido> getLsProdutoPedido() {
        return lsProdutoPedido;
    }

    public void setLsProdutoPedido(List<ProdutoPedido> lsProdutoPedido) {
        this.lsProdutoPedido = lsProdutoPedido;
    }

    public String getMes() {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        String mes = "";
        if (this.ped_mes <= 12 && this.ped_mes > 0) {
            mes = meses[this.ped_mes - 1];
        }
        return mes;
    }

}
