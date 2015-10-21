package bean;

import dao.CustoDAO;
import dao.DespesaDAO;
import dao.MaquinaDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Custo;
import model.CustoDespesa;
import model.Despesa;
import model.Maquina;
import model.Produto;
import model.ProdutoCusto;
import model.ProdutoDespesa;
import model.ProdutoPedido;

@ManagedBean
@SessionScoped
public class CustoBean {

    private CustoDAO dao = new CustoDAO();
    private DespesaDAO desDAO = new DespesaDAO();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private PedidoDAO pedDAO = new PedidoDAO();
    private MaquinaDAO maqDAO = new MaquinaDAO();

    private DataModel custos;
    private DataModel despesasmess;
    private Despesa despesa = new Despesa();

    private List<CustoDespesa> lsCustoDespesa = new ArrayList<>();
    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();

    private Produto produto = new Produto();
    private double valor = 0;
    private int porcent = 0;
    private int mes = 0;
    private int ano = 0;

    private List<ProdutoCusto> lsProdutoCusto = new ArrayList<>();
    private List<Maquina> lsMaquinas = new ArrayList<>();

    public CustoBean() {
    }

    public DataModel getCustos() {
        clearSession();
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
        this.despesa = new Despesa();
        this.lsCustoDespesa = new ArrayList<>();
        this.valor = 0;
        this.lsCustoDespesa = new ArrayList<>();
        this.lsProdutos = new ArrayList<>();
        this.lsProdutosAll = new ArrayList<>();
        this.produto = new Produto();
        this.custos = new ArrayDataModel();
        this.lsProdutoCusto = new ArrayList<>();
    }

    public List<CustoDespesa> getLsCustoDespesa() {
        porcent = 0;
        for (CustoDespesa cd : lsCustoDespesa) {
//            porcent += cd.getCsd_participacao();
//            double v = cd.getCsd_participacao() * valor;
//            v = v / 100;
//            if (cd.getCusto().getCus_quant_prod() > 0) {
//                v = v / cd.getCusto().getCus_quant_prod();
//            } else {
//                v = 0;
//            }
//            v = v * 100;
//            v = Math.round(v);
//            v = v / 100;
//            cd.getCusto().setCus_preco_unitario(v);
        }
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
//        lsProdutos = proDAO.produtosCusto(0, 0, mes, ano);
        lsProdutosAll = lsProdutos;
        reloadProdutos();
        return lsProdutos;
    }

    public void setLsProdutos(List<Produto> lsProdutos) {
        this.lsProdutos = lsProdutos;
    }

    public void addProduto() {
        if (produto != null) {
            if (lsProdutoCusto == null) {
                lsProdutoCusto = new ArrayList<>();
            }
            boolean bAdd = true;
            for (ProdutoCusto pc : lsProdutoCusto) {
                if (pc.getProduto().getPro_id() == produto.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                ProdutoCusto pc = new ProdutoCusto();
                Custo c = new Custo();
                produto.setLsProdutoPedido(pedDAO.totalPedidosMes(produto.getPro_id(), mes, ano));
                pc.setProduto(produto);
                c.setCus_preco_produto(produto.getPro_preco());
                pc.setCusto(c);
                lsProdutoCusto.add(pc);
                produto = new Produto();
            }
            reloadProdutos();
        }
    }

    public void removeProduto(ProdutoCusto pd) {
        lsProdutoCusto.remove(pd);
        reloadProdutos();
    }

//    public void CarregaLsCustoDespesa() {
//        lsCustoDespesa = new ArrayList<>();
//        if (despesa.getDes_id() != 0) {
//            despesa = desDAO.findEdit(despesa.getDes_id());
//            for (ProdutoDespesa pd : despesa.getLsProdutoDespesa()) {
//                CustoDespesa cd = new CustoDespesa();
//                Custo c = new Custo();
//                c.setProduto(pd.getProduto());
//                cd.setCsd_valor(12.34);
//                cd.setCusto(c);
//                lsCustoDespesa.add(cd);
//            }
//        } else {
//            clearSession();
//        }
//    }
    public String New(int id) {
        lsProdutoCusto = new ArrayList<>();
        if (id != 0) {
            despesa = desDAO.findEdit(id);
            for (ProdutoDespesa pd : despesa.getLsProdutoDespesa()) {
                ProdutoCusto pc = new ProdutoCusto();
                Custo c = new Custo();
                pc.setProduto(pd.getProduto());
                pc.getProduto().setLsProdutoPedido(pedDAO.totalPedidosMes(pd.getProduto().getPro_id(), getMes(), getAno()));

                c.setCus_preco_produto(pd.getProduto().getPro_preco());
                pc.setCusto(c);
                pc.setParticipacao(pd.getPrd_por_part());
                lsProdutoCusto.add(pc);
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
            for (ProdutoCusto pc : lsProdutoCusto) {
                if (pc.getProduto().getPro_id() == p.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                lsProdutos.add(p);
            }
        }
    }

    public int getPorcent() {
        return porcent;
    }

    public void setPorcent(int porcent) {
        this.porcent = porcent;
    }

    public int getMes() {
        if (mes == 0) {
            Date d = new Date();
            mes = d.getMonth() + 1;
        }
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        if (ano == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            ano = Integer.parseInt(sdf.format(new Date()));
        }
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public DataModel getDespesasmess() {
        clearSession();
        despesasmess = new ListDataModel(Despesas(createDate(1, mes, ano)));
        return despesasmess;
    }

    public void setDespesasmess(DataModel despesasmess) {
        this.despesasmess = despesasmess;
    }

    private List<Despesa> Despesas(Date d) {
        List<Despesa> desMes = desDAO.searchDespesasMes(d);
        return desMes;
    }

    private Date createDate(int d, int m, int a) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = (Date) sdf.parse(d + "-" + m + "-" + a + "-");
        } catch (ParseException ex) {
        }
        return date;
    }

    public int totalPedido(Produto p) {
        p.setLsProdutoPedido(pedDAO.totalPedidosMes(p.getPro_id(), mes, ano));
        int total = 0;
        if (p.getLsProdutoPedido() != null) {
            for (ProdutoPedido pp : p.getLsProdutoPedido()) {
                total += pp.getPrp_quantidade();
            }
        }
        return total;
    }

    public List<ProdutoCusto> getLsProdutoCusto() {
        return lsProdutoCusto;
    }

    public void setLsProdutoCusto(List<ProdutoCusto> lsProdutoCusto) {
        this.lsProdutoCusto = lsProdutoCusto;
    }

    public List<Maquina> getLsMaquinas() {
        lsMaquinas = maqDAO.findAll();
        return lsMaquinas;
    }

    public void setLsMaquinas(List<Maquina> lsMaquinas) {
        this.lsMaquinas = lsMaquinas;
    }

}
