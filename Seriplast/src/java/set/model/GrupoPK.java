package set.model;

import java.io.Serializable;
import java.util.Objects;

// From http://stackoverflow.com/questions/3585034/how-to-map-a-composite-key-with-hibernate
public class GrupoPK implements Serializable {
    
    protected Usuario usuario;
    protected String grp_nome;

    public GrupoPK() {
    }

    public GrupoPK(Usuario usuario, String grp_nome) {
        this.usuario = usuario;
        this.grp_nome = grp_nome;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.usuario);
        hash = 79 * hash + Objects.hashCode(this.grp_nome);
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
        final GrupoPK other = (GrupoPK) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.grp_nome, other.grp_nome)) {
            return false;
        }
        return true;
    }

}
