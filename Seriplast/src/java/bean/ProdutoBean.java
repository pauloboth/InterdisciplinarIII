package bean;

import dao.CategoriaDAO;
import dao.DespesaDAO;
import dao.MaquinaDAO;
import dao.ProdutoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Categoria;
import model.Despesa;
import model.Maquina;
import model.Produto;
import model.ProdutoDespesa;
import model.ProdutoMaquina;

@ManagedBean
@SessionScoped
public class ProdutoBean {

    private Produto produto = new Produto();
    private ProdutoDAO dao = new ProdutoDAO();
    private CategoriaDAO catDAO = new CategoriaDAO();
    private List<Categoria> lsCategorias;
    private List<Maquina> lsMaquinas = new ArrayList<>();
    private List<Maquina> lsMaquinasAll = new ArrayList<>();
    private Maquina maquina = new Maquina();
    private MaquinaDAO maqDAO = new MaquinaDAO();

    private List<Despesa> lsDespesas = new ArrayList<>();
    private List<Despesa> lsDespesasAll = new ArrayList<>();
    private Despesa despesa = new Despesa();
    private DespesaDAO desDAO = new DespesaDAO();

    private DataModel produtos;

    public ProdutoBean() {
    }

    public DataModel getProdutos() {
        clearSession();
        this.produtos = new ListDataModel(dao.findAll(0));
        return produtos;
    }

    public void setProdutos(DataModel i) {
        this.produtos = i;
    }

    public String edit(Produto i) {
        produto = dao.findEdit(i.getPro_id());
        return "produtofrm";
    }

    public String delete(Produto i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        return "produtolst";
    }

    public String salvar() {
        dao.save(produto);
        clearSession();
        return "produtolst";
    }

    public String listar() {
        return "produtolst";
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Categoria> getLsCategorias() {
        lsCategorias = catDAO.findAll();
        return lsCategorias;
    }

    public void setLsCategorias(List<Categoria> lsCategorias) {
        this.lsCategorias = lsCategorias;
    }

    public List<Maquina> getLsMaquinas() {
        lsMaquinas = maqDAO.findAll();
        lsMaquinasAll = lsMaquinas;
        reloadMaquinas();
        return lsMaquinas;
    }

    public void setLsMaquinas(List<Maquina> lsMaquinas) {
        this.lsMaquinas = lsMaquinas;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public List<Despesa> getLsDespesas() {
        lsDespesas = desDAO.findAll(1);
        lsDespesasAll = lsDespesas;
        reloadDespesas();
        return lsDespesas;
    }

    public void setLsDespesas(List<Despesa> lsDespesas) {
        this.lsDespesas = lsDespesas;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public void addMaquina() {
        if (maquina != null) {
            if (produto.getLsProdutoMaquina() == null) {
                produto.setLsProdutoMaquina(new ArrayList<ProdutoMaquina>());
            }
            boolean bAdd = true;
            for (ProdutoMaquina pm : produto.getLsProdutoMaquina()) {
                if (pm.getMaquina().getMaq_id() == maquina.getMaq_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                ProdutoMaquina pm = new ProdutoMaquina();
                pm.setMaquina(maquina);
                pm.setProduto(produto);
                produto.getLsProdutoMaquina().add(pm);
            }
            reloadMaquinas();
        }
    }

    public void removeMaquina(ProdutoMaquina pm) {
        this.produto.getLsProdutoMaquina().remove(pm);
        reloadMaquinas();
    }

    public void addDespesa() {
        if (despesa != null) {
            if (produto.getLsProdutoDespesa() == null) {
                produto.setLsProdutoDespesa(new ArrayList<ProdutoDespesa>());
            }
            boolean bAdd = true;
            for (ProdutoDespesa pd : produto.getLsProdutoDespesa()) {
                if (pd.getDespesa().getDes_id() == despesa.getDes_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                ProdutoDespesa pd = new ProdutoDespesa();
                pd.setDespesa(despesa);
                pd.setProduto(produto);
                produto.getLsProdutoDespesa().add(pd);
            }
            reloadDespesas();
        }
    }

    public void removeDespesa(ProdutoDespesa pd) {
        this.produto.getLsProdutoDespesa().remove(pd);
        reloadDespesas();
    }

    private void reloadMaquinas() {
        lsMaquinas = new ArrayList<>();
        for (Maquina m : lsMaquinasAll) {
            boolean bAdd = true;
            for (ProdutoMaquina pm : produto.getLsProdutoMaquina()) {
                if (pm.getMaquina().getMaq_id() == m.getMaq_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                lsMaquinas.add(m);
            }
        }
    }

    private void reloadDespesas() {
        lsDespesas = new ArrayList<>();
        for (Despesa d : lsDespesasAll) {
            boolean bAdd = true;
            for (ProdutoDespesa pd : produto.getLsProdutoDespesa()) {
                if (pd.getDespesa().getDes_id() == d.getDes_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                lsDespesas.add(d);
            }
        }
    }

    public void clearSession() {
        this.despesa = new Despesa();
        this.lsDespesas = new ArrayList<>();
        this.lsDespesasAll = new ArrayList<>();
        this.lsMaquinas = new ArrayList<>();
        this.lsMaquinasAll = new ArrayList<>();
        this.maquina = new Maquina();
        this.produto = new Produto();
    }
}
