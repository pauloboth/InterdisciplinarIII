package bean;

import dao.CustoDAO;
import dao.DespesaDAO;
import dao.MaquinaDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import java.math.BigDecimal;
import java.math.MathContext;
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
import model.DespesaMes;
import model.Maquina;
import model.Produto;
import model.ProdutoCusto;
import model.ProdutoDespesa;
import model.ProdutoMaquina;
import model.ProdutoPedido;

@ManagedBean
@SessionScoped
public class EdivanBean {

    private CustoDAO dao = new CustoDAO();
    private DespesaDAO desDAO = new DespesaDAO();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private PedidoDAO pedDAO = new PedidoDAO();
    private MaquinaDAO maqDAO = new MaquinaDAO();

    private DataModel custos;
    private DataModel despesasmess;

    private Despesa despesa = new Despesa();
    private Produto produto = new Produto();

    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCusto = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCustoMaq = new ArrayList<>();
    private List<Maquina> lsMaquinas = new ArrayList<>();

    private List<String> lsmes;

    public EdivanBean() {
    }

    public DataModel getCustos() {
        clearSession();
        this.custos = new ListDataModel(dao.findAll());
        return custos;
    }

    public List<String> getLsmes() {
        return lsmes;
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

    public List<ProdutoCusto> getLsProdutoCustoMaq() {
        lsProdutoCustoMaq = new ArrayList<>();
        lsMaquinas = maqDAO.findAll();
        for (Maquina m : lsMaquinas) {
            if (m.getLsProdutoMaquina() != null) {
                for (ProdutoMaquina pm : m.getLsProdutoMaquina()) {
                    ProdutoCusto pc = new ProdutoCusto();
                    pc.setMaquina(m);
                    pc.setProduto(pm.getProduto());
                    pc.setTempo(pm.getPrm_tem_pro());
                    lsProdutoCustoMaq.add(pc);
                }
            }
        }
    
        return lsProdutoCustoMaq;
    }

    public void setLsProdutoCustoMaq(List<ProdutoCusto> lsProdutoCustoMaq) {
        this.lsProdutoCustoMaq = lsProdutoCustoMaq;
    }




  

    private void clearSession() {
        this.custos = new ArrayDataModel();
        this.despesasmess = new ArrayDataModel();
        this.despesa = new Despesa();
        this.produto = new Produto();
        this.lsProdutos = new ArrayList<>();
        this.lsProdutosAll = new ArrayList<>();
        this.lsProdutoCusto = new ArrayList<>();
        this.lsProdutoCustoMaq = new ArrayList<>();
        this.lsMaquinas = new ArrayList<>();
    }
}
