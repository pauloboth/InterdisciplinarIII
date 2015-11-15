package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @SequenceGenerator(name = "usu_id", sequenceName = "seq_usu_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "usu_id")
    private int usu_id;

    private String usu_nome;
    private String usu_email;
    private String usu_login;
    private String usu_senha;
    private boolean usu_status;

    @ManyToOne
    @JoinColumn(name = "gro_id", referencedColumnName = "gro_id")
    private Grupo grupo;

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

    public String getUsu_email() {
        return usu_email;
    }

    public void setUsu_email(String usu_email) {
        this.usu_email = usu_email;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String toString() {
        return getUsu_nome();
    }
}
