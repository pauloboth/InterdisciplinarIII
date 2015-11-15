package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "grupo")
public class Grupo implements Serializable {

    @Id
    @SequenceGenerator(name = "gro_id", sequenceName = "seq_gro_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gro_id")
    private int gro_id;
    private String gro_nome;

    public Grupo() {
    }

    public int getGro_id() {
        return gro_id;
    }

    public void setGro_id(int gro_id) {
        this.gro_id = gro_id;
    }

    public String getGro_nome() {
        return gro_nome;
    }

    public void setGro_nome(String gro_nome) {
        this.gro_nome = gro_nome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.gro_id;
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
        final Grupo other = (Grupo) obj;
        if (this.gro_id != other.gro_id) {
            return false;
        }
        return true;
    }

    public String toString() {
        return getGro_nome();
    }
}
