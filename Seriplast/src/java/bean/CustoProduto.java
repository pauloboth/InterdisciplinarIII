package bean;

import dao.CategoriaDAO;
import dao.CustoDAO;
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
import model.CustoDespesa;
import model.Despesa;
import model.Maquina;
import model.Produto;
import model.ProdutoDespesa;
import model.ProdutoMaquina;

@ManagedBean
@SessionScoped
public class CustoProduto {
//PRODUTO
    private Produto produto = new Produto();
    private ProdutoDAO dao = new ProdutoDAO();
    private DataModel produtos;
//MAQUINA   
    private List<Maquina> lsMaquinas = new ArrayList<>();
    private List<Maquina> lsMaquinasAll = new ArrayList<>();
    private Maquina maquina = new Maquina();
    private MaquinaDAO maqDAO = new MaquinaDAO();
//DESPESA
    private List<Despesa> lsDespesas = new ArrayList<>();
    private List<Despesa> lsDespesasAll = new ArrayList<>();
    private Despesa despesa = new Despesa();
    private DespesaDAO desDAO = new DespesaDAO();
//CUSTO
    private CustoDAO Custodao = new CustoDAO();
    private DataModel custos;
//CUSTO DESPESA
    private List<CustoDespesa> lsCustoDespesa = new ArrayList<>();
    ////////////////////////////////////
    private double valor = 0;
    private int porcent = 0;

    public CustoProduto() {
    }

    
    //PRODUTO
    public DataModel getProdutos() {
        clearSession();
        this.produtos = new ListDataModel(dao.findAll(0));
        return produtos;
    }
    public void setProdutos(DataModel i) {
        this.produtos = i;
    }
    public String listarProdutos() {
        return "produtolst";
    }
    public Produto getProduto() {
        return produto;
    }
    
    
    
    //MAQUINA
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

    
    //DESPESA
    public List<Despesa> getLsDespesas() {
        lsDespesas = desDAO.findAll(1);
        lsDespesasAll = lsDespesas;
        reloadDespesas();
        return lsDespesas;
    }

    public Despesa getDespesa() {
        return despesa;
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
    
    
    //CUSTO
     public DataModel getCustos() {
        this.custos = new ListDataModel(Custodao.findAll());
        clearSession();
        return custos;
    }
     public String listarCustos() {
        return "custolst";
    }
      public String select() {
        produto = (Produto) produtos.getRowData();
        return "custoview";
    }
       public String select2() {
        produto = (Produto) produtos.getRowData();
        return "custoprodutoano";
    }
     
     //CUSTODESPESA
      public List<CustoDespesa> getLsCustoDespesa() {
        porcent = 0;
        for (CustoDespesa cd : lsCustoDespesa) {

        }
        return lsCustoDespesa;
    }
      ///////////////////////////////
      public int getPorcent() {
        return porcent;
    }
      public Double getValor() {
        return valor;
    }
      
    
    
    //SESSION
    public void clearSession() {
        this.despesa = new Despesa();
        this.lsDespesas = new ArrayList<>();
        this.lsDespesasAll = new ArrayList<>();
        this.lsMaquinas = new ArrayList<>();
        this.lsMaquinasAll = new ArrayList<>();
        this.maquina = new Maquina();
        this.produto.setLsProdutoDespesa(new ArrayList<ProdutoDespesa>());
        this.produto.setLsProdutoMaquina(new ArrayList<ProdutoMaquina>());
    }
   
}
