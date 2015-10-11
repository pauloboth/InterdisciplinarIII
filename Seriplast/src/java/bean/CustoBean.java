package bean;

import dao.CustoDAO;
import dao.DespesaDAO;
import dao.ProdutoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Custo;
import model.CustoDespesa;
import model.Despesa;
import model.Produto;
import model.ProdutoDespesa;

@ManagedBean
@SessionScoped
public class CustoBean {

    private CustoDAO dao = new CustoDAO();
    private DataModel custos;
    private Despesa despesa = new Despesa();
    private DespesaDAO desDAO = new DespesaDAO();
    private List<CustoDespesa> lsCustoDespesa = new ArrayList<>();
    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private Produto produto = new Produto();
    private double valor = 0;

    public CustoBean() {
    }

    public DataModel getCustos() {
        this.custos = new ListDataModel(dao.findAll());
        return custos;
    }

    public void setCustos(DataModel i) {
        this.custos = i;
    }

    public String delete(Custo i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        return "custolst";
    }

//    public String salvar() {
//        if (custo.getCus_id() > 0) {
//            dao.update(custo);
//        } else {
//            dao.insert(custo);
//        }
//        clearSession();
//        return "custolst";
//    }
    public String listar() {
        return "custolst";
    }

    public Despesa getDespesa() {
        if (despesa.getDes_id() != 0) {
            despesa = desDAO.findEdit(despesa.getDes_id());
        }
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public void clearSession() {
        despesa = new Despesa();
        lsCustoDespesa = new ArrayList<>();
    }

    public List<CustoDespesa> getLsCustoDespesa() {
        return lsCustoDespesa;
    }

    public void setLsCustoDespesa(List<CustoDespesa> lsCustoDespesa) {
        this.lsCustoDespesa = lsCustoDespesa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
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

    public void addProduto() {
        if (produto != null) {
            if (lsCustoDespesa == null) {
                lsCustoDespesa = new ArrayList<>();
            }
            boolean bAdd = true;
            for (CustoDespesa cd : lsCustoDespesa) {
                if (cd.getCusto().getProduto().getPro_id() == produto.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                CustoDespesa cd = new CustoDespesa();
                Custo c = new Custo();
                c.setProduto(produto);
                cd.setCusto(c);
                cd.setDespesa(despesa);
                lsCustoDespesa.add(cd);
                produto = new Produto();
            }
            reloadProdutos();
        }
    }

    public void removeProduto(CustoDespesa cd) {
        lsCustoDespesa.remove(cd);
        reloadProdutos();
    }

    public void CarregaLsCustoDespesa() {
        lsCustoDespesa = new ArrayList<>();
        if (despesa.getDes_id() != 0) {
            despesa = desDAO.findEdit(despesa.getDes_id());
            for (ProdutoDespesa pd : despesa.getLsProdutoDespesa()) {
                CustoDespesa cd = new CustoDespesa();
                Custo c = new Custo();
                c.setProduto(pd.getProduto());
                cd.setCusto(c);
                lsCustoDespesa.add(cd);
            }
        } else {
            clearSession();
        }
    }

    public String New(int id) {
        lsCustoDespesa = new ArrayList<>();
        if (id != 0) {
            despesa = desDAO.findEdit(id);
            for (ProdutoDespesa pd : despesa.getLsProdutoDespesa()) {
                CustoDespesa cd = new CustoDespesa();
                Custo c = new Custo();
                c.setProduto(pd.getProduto());
                cd.setCusto(c);
                lsCustoDespesa.add(cd);
            }
        } else {
            clearSession();
        }
        return "custofrm";
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
            for (CustoDespesa cd : lsCustoDespesa) {
                if (cd.getCusto().getProduto().getPro_id() == p.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                lsProdutos.add(p);
            }
        }
    }

}
