package bean;

import dao.MaquinaDAO;
import dao.ProdutoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Maquina;
import model.Produto;
import model.ProdutoMaquina;

@ManagedBean
@SessionScoped
public class MaquinaBean {

    private Maquina maquina = new Maquina();
    private MaquinaDAO dao = new MaquinaDAO();
    private DataModel maquinas;

    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private Produto produto = new Produto();
    private ProdutoDAO proDAO = new ProdutoDAO();

    public MaquinaBean() {
    }

    public DataModel getMaquinas() {
        clearSession();
        this.maquinas = new ListDataModel(dao.findAll());
        return maquinas;
    }

    public void setMaquinas(DataModel i) {
        this.maquinas = i;
    }

    public String edit(Maquina i) {
        maquina = dao.findEdit(i.getMaq_id());
        return "maquinafrm";
    }

    public String delete(Maquina i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        return "maquinalst";
    }

    public String salvar() {
        if (maquina.getMaq_id() > 0) {
            dao.update(maquina);
        } else {
            dao.insert(maquina);
        }
        clearSession();
        return "maquinalst";
    }

    public String listar() {
        return "maquinalst";
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public List<Produto> getLsProdutos() {
        lsProdutos = proDAO.findAll(1);
        lsProdutosAll = lsProdutos;
        reloadProdutos();
        return lsProdutos;
    }

    public void setLsProdutos(List<Produto> lsProdutos) {
        this.lsProdutos = lsProdutos;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void addProduto() {
        if (produto != null) {
            if (this.maquina.getLsProdutoMaquina() == null) {
                this.maquina.setLsProdutoMaquina(new ArrayList<ProdutoMaquina>());
            }
            boolean bAdd = true;
            for (ProdutoMaquina pm : this.maquina.getLsProdutoMaquina()) {
                if (pm.getProduto().getPro_id() == produto.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                ProdutoMaquina pm = new ProdutoMaquina();
                pm.setMaquina(maquina);
                pm.setProduto(produto);
                this.maquina.getLsProdutoMaquina().add(pm);
                produto = new Produto();
            }
            reloadProdutos();
        }
    }

    public void removeProduto(ProdutoMaquina pd) {
        this.maquina.getLsProdutoMaquina().remove(pd);
        reloadProdutos();
    }

    private void reloadProdutos() {
        lsProdutos = new ArrayList<>();
        for (Produto p : lsProdutosAll) {
            boolean bAdd = true;
            for (ProdutoMaquina pm : this.maquina.getLsProdutoMaquina()) {
                if (pm.getProduto().getPro_id() == p.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                lsProdutos.add(p);
            }
        }
    }

    private void clearSession() {
        this.maquina = new Maquina();
        this.maquina.setLsProdutoMaquina(new ArrayList<ProdutoMaquina>());
        this.produto = new Produto();
        this.lsProdutos = new ArrayList<>();
        this.lsProdutosAll = new ArrayList<>();
    }
}
