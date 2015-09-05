package set.bean;

import set.dao.PessoaDAO;
import set.model.Pessoa;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@RequestScoped
public class PessoaBean {

    private final String sTitle = Pessoa.sTitle;
    private final String pTitle = Pessoa.pTitle;

    private Pessoa pessoa = new Pessoa();
    private PessoaDAO dao = new PessoaDAO();
    private DataModel pessoas;

    public PessoaBean() {
    }

    public String getsTitle() {
        return sTitle;
    }

    public String getpTitle() {
        return pTitle;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public DataModel getPessoas() {
        this.pessoas = new ListDataModel(dao.findAll());
        return pessoas;
    }

    public void setPessoas(DataModel idiomas) {
        this.pessoas = pessoas;
    }

    public String insert() {
        dao.insert(pessoa);
        return "pessoalst";
    }

    public String edit(Pessoa i) {
        pessoa = (Pessoa) pessoas.getRowData();
        return "pessoafrm";
    }

    public String update() {
        dao.update(pessoa);
        return "pessoalst";
    }

    public String delete(Pessoa i) {
        dao.delete(i);
        return "pessoalst";
    }

    public String salvar() {
        if (pessoa.getPes_codigo() > 0) {
            dao.update(pessoa);
        } else {
            dao.insert(pessoa);
        }
        return "pessoalst";
    }

    public String listar() {
        return "pessoalst";
    }
}
