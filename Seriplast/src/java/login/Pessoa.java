package login;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

    public static final String sTitle = "Pessoa";
    public static final String pTitle = "Pessoas";

    @Id
    @SequenceGenerator(name = "pes_codigo", sequenceName = "pes_codigo")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pes_codigo")
    private int pes_codigo;

    private String pes_nome;
    private String pes_cpf;
    private String pes_rg;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date pes_datanasc;
    private String pes_telefone;
    private String pes_email;
    private int pes_tipo;
    private int pes_genero;
    private int pes_papel;
    private String pes_observacoes;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date pes_datacadastro;

    public Pessoa() {
    }




    public int getPes_codigo() {
        return pes_codigo;
    }

    public void setPes_codigo(int pes_codigo) {
        this.pes_codigo = pes_codigo;
    }

    public String getPes_nome() {
        return pes_nome;
    }

    public void setPes_nome(String pes_nome) {
        this.pes_nome = pes_nome;
    }

    public String getPes_cpf() {
        return pes_cpf;
    }

    public void setPes_cpf(String pes_cpf) {
        this.pes_cpf = pes_cpf;
    }

    public String getPes_rg() {
        return pes_rg;
    }

    public void setPes_rg(String pes_rg) {
        this.pes_rg = pes_rg;
    }

    public Date getPes_datanasc() {
        return pes_datanasc;
    }

    public void setPes_datanasc(Date pes_datanasc) {
        this.pes_datanasc = pes_datanasc;
    }

    public String getPes_telefone() {
        return pes_telefone;
    }

    public void setPes_telefone(String pes_telefone) {
        this.pes_telefone = pes_telefone;
    }

    public String getPes_email() {
        return pes_email;
    }

    public void setPes_email(String pes_email) {
        this.pes_email = pes_email;
    }

    public int getPes_tipo() {
        return pes_tipo;
    }

    public void setPes_tipo(int pes_tipo) {
        this.pes_tipo = pes_tipo;
    }

    public int getPes_genero() {
        return pes_genero;
    }

    public void setPes_genero(int pes_genero) {
        this.pes_genero = pes_genero;
    }

    public int getPes_papel() {
        return pes_papel;
    }

    public void setPes_papel(int pes_papel) {
        this.pes_papel = pes_papel;
    }

    public String getPes_observacoes() {
        return pes_observacoes;
    }

    public void setPes_observacoes(String pes_observacoes) {
        this.pes_observacoes = pes_observacoes;
    }

    public Date getPes_datacadastro() {
        return pes_datacadastro;
    }

    public void setPes_datacadastro(Date pes_datacadastro) {
        this.pes_datacadastro = pes_datacadastro;
    }
    
    
        @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.pes_codigo;
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
        final Pessoa other = (Pessoa) obj;
        if (this.pes_codigo != other.pes_codigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getPes_nome();
    }

}
