package bean;

import dao.DespesaDAO;
import dao.ProdutoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Despesa;
import model.Produto;
import model.ProdutoDespesa;

@ManagedBean
@SessionScoped
public class DespesaBean {

    private Despesa despesa = new Despesa();
    private DespesaDAO dao = new DespesaDAO();
    private DataModel despesas;
    private boolean isMachin;
    private TimeZone timeZone = TimeZone.getDefault();
    private List<ProdutoDespesa> lsProdutoDespesas = new ArrayList<>();
    private List<Produto> lsProdutos = new ArrayList<>();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private ProdutoDespesa produtoDepesaSelect = new ProdutoDespesa();

    public DespesaBean() {
    }

    public DataModel getDespesas() {
        this.despesas = new ListDataModel(dao.findAll());
        return despesas;
    }

    public void setDespesas(DataModel i) {
        this.despesas = i;
    }

    public String edit(Despesa i) {
        despesa = (Despesa) despesas.getRowData();
        return "despesafrm";
    }

    public String delete(Despesa i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        return "despesalst";
    }

    public String salvar() {
        if (despesa.getDes_id() > 0) {
            dao.update(despesa);
        } else {
            dao.insert(despesa);
        }
        return "despesalst";
    }

    public String listar() {
        return "despesalst";
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public boolean isIsMachin() {
        return isMachin;
    }

    public void setIsMachin(boolean isMachin) {
        this.isMachin = isMachin;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public List<ProdutoDespesa> getLsProdutoDespesas() {
        return lsProdutoDespesas;
    }

    public void removeProdutoDespesa(ProdutoDespesa pd) {
        lsProdutoDespesas.remove(pd);
    }

    public void setLsProdutoDespesas(List<ProdutoDespesa> lsProdutoDespesas) {
        this.lsProdutoDespesas = lsProdutoDespesas;
    }

    public List<Produto> getLsProdutos() {
        lsProdutos = proDAO.findAll();
        return lsProdutos;
    }

    public ProdutoDespesa getProdutoDepesaSelect() {
        return produtoDepesaSelect;
    }

    public void setProdutoDepesaSelect(ProdutoDespesa produtoDepesaSelect) {
        this.produtoDepesaSelect = produtoDepesaSelect;
    }

    public void addProdutoDesp() {
        if (lsProdutoDespesas == null) {
            lsProdutoDespesas = new ArrayList<>();
        }
        boolean bAdd = true;
        for (ProdutoDespesa pds : lsProdutoDespesas) {
            if (pds.getProduto().getPro_id() == produtoDepesaSelect.getProduto().getPro_id()) {
                bAdd = false;
            }
        }
        if (bAdd) {
            produtoDepesaSelect.setDespesa(despesa);
            lsProdutoDespesas.add(produtoDepesaSelect);
            produtoDepesaSelect = new ProdutoDespesa();
        }
    }

    public void editProdutoDesp(ProdutoDespesa pd) {
        produtoDepesaSelect = pd;
        lsProdutoDespesas.remove(pd);
    }

}
