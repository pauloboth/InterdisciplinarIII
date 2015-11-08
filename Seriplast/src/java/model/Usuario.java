package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @SequenceGenerator(name = "usu_id", sequenceName = "seq_usu_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "usu_id")
    private int usu_id;

    @Id
    private String usu_login;
    private String usu_senha;
    private boolean usu_status;
    private String usu_nome;

    @OneToMany
    @JoinColumn(name = "usu_login", referencedColumnName = "usu_login")
    private List<Grupo> grupos;

    public Usuario() {
    }

    public int getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(int usu_id) {
        this.usu_id = usu_id;
    }

    public String getUsu_login() {
        return usu_login;
    }

    public void setUsu_login(String usu_login) {
        this.usu_login = usu_login;
    }

    public String getUsu_senha() {
        return usu_senha;
    }

    public void setUsu_senha(String usu_senha) {
        this.usu_senha = usu_senha;
    }

    public boolean isUsu_status() {
        return usu_status;
    }

    public void setUsu_status(boolean usu_status) {
        this.usu_status = usu_status;
    }

    public String getUsu_nome() {
        return usu_nome;
    }

    public void setUsu_nome(String usu_nome) {
        this.usu_nome = usu_nome;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

}
