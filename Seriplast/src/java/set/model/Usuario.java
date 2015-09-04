
package set.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    public static final String sTitle = "Usuário";
    public static final String pTitle = "Usuários";

    @Id
    private String usu_login;
    private String usu_senha;
    private boolean usu_status;

    @OneToOne(optional = false)
    @JoinColumn(name = "pes_codigo", referencedColumnName = "pes_codigo", nullable = false)
    private Pessoa pessoa;
    
    @OneToMany
    @JoinColumn(name = "usu_login", referencedColumnName = "usu_login")
    private List<Grupo> grupos;
    
    public Usuario() {
    }

    public String getUsuLogin() {
        return usu_login;
    }

    public void setUsuLogin(String usu_login) {
        this.usu_login = usu_login;
    }

    public String getUsuSenha() {
        return usu_senha;
    }

    public boolean getUsuStatus() {
        return usu_status;
    }

    public void setUsuStatus(boolean usu_status) {
        this.usu_status = usu_status;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }
    
}
