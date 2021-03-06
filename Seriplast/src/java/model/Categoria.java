package model;

import java.io.Serializable;
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
@Table(name = "categoria")
public class Categoria implements Serializable {

    @Id
    @SequenceGenerator(name = "cat_id", sequenceName = "seq_cat_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cat_id")
    private int cat_id;
    private String cat_nome;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Produto> lsProduto;

    public Categoria() {
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_nome() {
        return cat_nome;
    }

    public void setCat_nome(String cat_nome) {
        this.cat_nome = cat_nome;
    }

    public List<Produto> getLsProduto() {
        return lsProduto;
    }

    public void setLsProduto(List<Produto> lsProduto) {
        this.lsProduto = lsProduto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.cat_id;
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
        final Categoria other = (Categoria) obj;
        if (this.cat_id != other.cat_id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCat_nome();
    }
}
