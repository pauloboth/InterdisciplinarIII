package model;

import java.io.Serializable;
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
@Table(name = "maquina")
public class Maquina implements Serializable {

    @Id
    @SequenceGenerator(name = "maq_id", sequenceName = "seq_maq_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "maq_id")
    private int maq_id;
    private String maq_nome;
    private int maq_watss_hora;
    private String maq_notas;

    @OneToMany(mappedBy = "maquina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoMaquina> lsProdutoMaquina;

    public Maquina() {
    }

    public int getMaq_id() {
        return maq_id;
    }

    public void setMaq_id(int maq_id) {
        this.maq_id = maq_id;
    }

    public String getMaq_nome() {
        return maq_nome;
    }

    public void setMaq_nome(String maq_nome) {
        this.maq_nome = maq_nome;
    }

    public int getMaq_watss_hora() {
        return maq_watss_hora;
    }

    public void setMaq_watss_hora(int maq_watss_hora) {
        this.maq_watss_hora = maq_watss_hora;
    }

    public String getMaq_notas() {
        return maq_notas;
    }

    public void setMaq_notas(String maq_notas) {
        this.maq_notas = maq_notas;
    }

    public List<ProdutoMaquina> getLsProdutoMaquina() {
        return lsProdutoMaquina;
    }

    public void setLsProdutoMaquina(List<ProdutoMaquina> lsProdutoMaquina) {
        this.lsProdutoMaquina = lsProdutoMaquina;
    }

    public String toString() {
        return this.getMaq_nome();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + this.maq_id;
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
        final Maquina other = (Maquina) obj;
        if (this.maq_id != other.maq_id) {
            return false;
        }
        return true;
    }
    
}
