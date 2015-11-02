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
import javax.persistence.Table;

@Entity
@Table(name = "producao")
public class Producao implements Serializable {

    @Id
    @SequenceGenerator(name = "prd_id", sequenceName = "seq_prd_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prd_id")
    private int prd_id;
    private String prd_cliente;
    private Date prd_data;
    private String prd_notas;
    private Date prd_data_ref;

    @OneToMany(mappedBy = "producao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProdutoProducao> lsProdutoProducao;

    public Producao() {
    }

    public int getPrd_id() {
        return prd_id;
    }

    public void setPrd_id(int prd_id) {
        this.prd_id = prd_id;
    }

    public String getPrd_cliente() {
        return prd_cliente;
    }

    public void setPrd_cliente(String prd_cliente) {
        this.prd_cliente = prd_cliente;
    }

    public Date getPrd_data() {
        return prd_data;
    }

    public void setPrd_data(Date prd_data) {
        this.prd_data = prd_data;
    }

    public String getPrd_notas() {
        return prd_notas;
    }

    public void setPrd_notas(String prd_notas) {
        this.prd_notas = prd_notas;
    }

    public Date getPrd_data_ref() {
        return prd_data_ref;
    }

    public void setPrd_data_ref(Date prd_data_ref) {
        this.prd_data_ref = prd_data_ref;
    }

    public List<ProdutoProducao> getLsProdutoProducao() {
        return lsProdutoProducao;
    }

    public void setLsProdutoProducao(List<ProdutoProducao> lsProdutoProducao) {
        this.lsProdutoProducao = lsProdutoProducao;
    }

    public String getMes() {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        String mes = "";
//        if (this.ped_mes <= 12 && this.ped_mes > 0) {
//            mes = meses[this.ped_mes - 1];
//        }
        return mes;
    }

    public boolean bEdit() {
        long dataOperacao = this.prd_data.getTime();
        long dataHoje = new Date().getTime();
        long resultado = dataHoje - dataOperacao;
        resultado = resultado / 1000 / 60 / 60 / 24;
        return resultado < 30;
    }

}
