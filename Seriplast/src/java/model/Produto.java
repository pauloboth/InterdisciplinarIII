package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    @Id
    @SequenceGenerator(name = "pro_id", sequenceName = "seq_pro_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pro_id")
    private int pro_id;

    @ManyToOne
    @JoinColumn(name = "cat_id", referencedColumnName = "cat_id")
    private Categoria categoria;

    private String pro_nome;
    private int pro_status;
    private double pro_preco;
    private String pro_notas;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProdutoMaquina> lsProdutoMaquina;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProdutoProducao> lsProdutoProducao;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProdutoDespesa> lsProdutoDespesa;

//    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    private List<Custo> lsCusto;
    public Produto() {
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getPro_nome() {
        return pro_nome;
    }

    public void setPro_nome(String pro_nome) {
        this.pro_nome = pro_nome;
    }

    public int getPro_status() {
        return pro_status;
    }

    public void setPro_status(int pro_status) {
        this.pro_status = pro_status;
    }

    public double getPro_preco() {
        return pro_preco;
    }

    public void setPro_preco(double pro_preco) {
        this.pro_preco = pro_preco;
    }

    public String getPro_notas() {
        return pro_notas;
    }

    public void setPro_notas(String pro_notas) {
        this.pro_notas = pro_notas;
    }

    public List<ProdutoMaquina> getLsProdutoMaquina() {
        return lsProdutoMaquina;
    }

    public void setLsProdutoMaquina(List<ProdutoMaquina> lsProdutoMaquina) {
        this.lsProdutoMaquina = lsProdutoMaquina;
    }

    public List<ProdutoProducao> getLsProdutoProducao() {
        return lsProdutoProducao;
    }

    public void setLsProdutoProducao(List<ProdutoProducao> lsProdutoProducao) {
        this.lsProdutoProducao = lsProdutoProducao;
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
        hash = 11 * hash + this.pro_id;
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
        final Produto other = (Produto) obj;
        if (this.pro_id != other.pro_id) {
            return false;
        }
        return true;
    }

    public String toString() {
        return getPro_nome();
    }

    public String toStatus() {
        if (this.pro_status == 1) {
            return "Ativo";
        } else if (this.pro_status == 2) {
            return "Bloqueado";
        } else if (this.pro_status == 3) {
            return "Excuído";
        } else {
            return "";
        }
    }

}
