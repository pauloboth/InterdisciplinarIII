package bean;

import dao.DespesaDAO;
import dao.ProdutoDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ArrayDataModel;
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
    private boolean isMachin = false;
    private TimeZone timeZone = TimeZone.getDefault();
    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private Produto produto = new Produto();
    private int porcent = 0;
    private int mes = 0;
    private DataModel despesasmess;

    public DespesaBean() {
    }

    public DataModel getDespesas() {
        clearSession();
        this.despesas = new ListDataModel(dao.findAll(0));
        return despesas;
    }

    public void setDespesas(DataModel i) {
        this.despesas = i;
    }

    public String edit(Despesa i) {
        clearSession();
        despesa = dao.findEdit(i.getDes_id());
        return "despesafrm";
    }

    public String delete(Despesa i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        clearSession();
        return "despesalst";
    }

    public String salvar() {
        if (despesa.getDes_id() > 0) {
            dao.update(despesa);
        } else {
            dao.insert(despesa);
        }
        clearSession();
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

    public List<Produto> getLsProdutos() {
        lsProdutos = proDAO.findAll(1);
        lsProdutosAll = lsProdutos;
        reloadProdutos();
        return lsProdutos;
    }

    public void removeProdutoDespesa(ProdutoDespesa pd) {
        despesa.getLsProdutoDespesa().remove(pd);
        reloadProdutos();
    }

    public void addProdutoDesp() {
        if (produto != null) {
            if (despesa.getLsProdutoDespesa() == null) {
                despesa.setLsProdutoDespesa(new ArrayList<ProdutoDespesa>());
            }
            boolean bAdd = true;
            for (ProdutoDespesa pds : despesa.getLsProdutoDespesa()) {
                if (pds.getProduto().getPro_id() == produto.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                ProdutoDespesa pd = new ProdutoDespesa();
                pd.setDespesa(despesa);
                pd.setProduto(produto);
                despesa.getLsProdutoDespesa().add(pd);
                produto = new Produto();
            }
            reloadProdutos();
        }
    }

    public void clearSession() {
        this.despesa = new Despesa();
        this.despesa.setLsProdutoDespesa(new ArrayList<ProdutoDespesa>());
        this.produto = new Produto();
        this.despesas = new ArrayDataModel();
        this.lsProdutos = new ArrayList<>();
        this.lsProdutosAll = new ArrayList<>();
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    private void reloadProdutos() {
        lsProdutos = new ArrayList<>();
        for (Produto p : lsProdutosAll) {
            boolean bAdd = true;
            for (ProdutoDespesa pds : despesa.getLsProdutoDespesa()) {
                if (pds.getProduto().getPro_id() == p.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                lsProdutos.add(p);
            }
        }
    }

    public int getPorcent() {
        porcent = 0;
        if (despesa.getLsProdutoDespesa() != null) {
            for (ProdutoDespesa pd : despesa.getLsProdutoDespesa()) {
                porcent += pd.getPrd_por_part();
            }
        }
        return porcent;
    }

    public void setPorcent(int porcent) {
        this.porcent = porcent;
    }

//    public void CalculaPorcent(ProdutoDespesa pd) {
//        if (this.porcent > 100) {
//            List<ProdutoDespesa> lsPD = despesa.getLsProdutoDespesa();
//            lsPD.remove(pd);
//            porcent = 0;
//            for (ProdutoDespesa p : lsPD) {
//                porcent += p.getPds_porc_part();
//            }
//            pd.setPds_porc_part(100 - porcent);
//            porcent = 100;
//        }
//    }
    public int getMes() {
        Date d = new Date();
        mes = d.getMonth() + 1;
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public DataModel getDespesasmess() {
        despesasmess = new ListDataModel(dao.findAll(0));
        return despesasmess;
    }

    public void setDespesasmess(DataModel despesasmess) {
        this.despesasmess = despesasmess;
    }

    public String reloadList() {
        return "despesalst";
    }
}
