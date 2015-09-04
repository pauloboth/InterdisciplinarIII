
package set.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "grupo")
@IdClass(GrupoPK.class)
public class Grupo implements Serializable {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "usu_login", referencedColumnName = "usu_login")
    private Usuario usuario;
    @Id
    private String grp_nome;
    
    public Grupo() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String getGrpNome() {
        return grp_nome;
    }

    public void setGrpNome(String grp_nome) {
        this.grp_nome = grp_nome;
    }
    
}
