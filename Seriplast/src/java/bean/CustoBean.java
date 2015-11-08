package bean;

import dao.CustoDAO;
import dao.DespesaDAO;
import dao.ProducaoDAO;
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
import model.Producao;
import model.Produto;
import model.ProdutoCusto;
import model.ProdutoProducao;

@ManagedBean
@SessionScoped
public class CustoBean {

    private DespesaDAO desDAO = new DespesaDAO();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private int i = 0;
    private CustoDAO cusDAO = new CustoDAO();
    private DataModel produtos;

    private DataModel custos;
    private DataModel despesasmess;
    double valor = 0;

    private Producao pedido = new Producao();
    private ProducaoDAO pedDAO = new ProducaoDAO();
    private List<ProdutoProducao> lstprodutopedido = new ArrayList<>();

    private Despesa despesa = new Despesa();
    private Produto produto = new Produto();
    private ProdutoCusto produtocusto = new ProdutoCusto();

    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCusto = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCustoAll = new ArrayList<>();

    private List<Custo> lsCustos = new ArrayList<>();
    private List<Custo> lsCustosAll = new ArrayList<>();

    private String Erro = "";

    private int ano = 0;

    private List<String> lsmes;

    public CustoBean() {
    }

    public DataModel getProdutos() {
        clearSession();
        this.produtos = new ListDataModel(proDAO.findAll(0));
        return produtos;
    }

    public String select2() {
        produto = (Produto) produtos.getRowData();
        return "custoprodutoano";
    }

    public DataModel getCustos() {
        clearSession();
        this.custos = new ListDataModel(cusDAO.findAll());
        return custos;
    }

    public List<String> getLsmes() {
        return lsmes;
    }

    public void setLsmes(List<String> lsmes) {
        lsmes.add("Janeiro");
        lsmes.add("Fevereiro");
        lsmes.add("Março");
        lsmes.add("Abril");
        lsmes.add("Maio");
        lsmes.add("Junho");
        lsmes.add("Julho");
        lsmes.add("Agosto");
        lsmes.add("Setembro");
        lsmes.add("Outubro");
        lsmes.add("Novembro");
        lsmes.add("Dezembro");
        this.lsmes = lsmes;
    }

    public Despesa getDespesa() {
        if (despesa.getDes_id() != 0) {
            despesa = desDAO.findEdit(despesa.getDes_id());
        }
        return despesa;
    }

    public List<Produto> getLsProdutos() {
        lsProdutos = proDAO.findAll(1);
//        lsProdutos = proDAO.produtosCusto(0, 0, mes, ano);
        lsProdutosAll = lsProdutos;

        return lsProdutos;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
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

    public void setDespesasmess(DataModel despesasmess) {
        this.despesasmess = despesasmess;
    }

    public List<Custo> getLsCustos() {
        lsCustos = cusDAO.CustoMensal(produto.getPro_id(), getAno());
        return lsCustos;
    }

    private Date createDate(int d, int m, int a) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = (Date) sdf.parse(d + "-" + m + "-" + a);
        } catch (ParseException ex) {
        }
        return date;
    }

    public List<ProdutoCusto> getLsProdutoCusto() {
        return lsProdutoCusto;
    }

    public void carrega() {
        int quantidade = 0;
        i = 0;
        valor = 0;

        lsProdutoCusto = null;
        lstprodutopedido = null;
        lsProdutoCusto = new ArrayList<>();
        lsCustos = cusDAO.CustoMensal(produto.getPro_id(), getAno());
        if (lsCustos != null && !lsCustos.isEmpty()) {
            for (Custo c : lsCustos) {
                ProdutoCusto pc = new ProdutoCusto();
                double valor_total = 0;
                double valor_uni = 0;
                if (c.getLsCustoDespesa() != null && !c.getLsCustoDespesa().isEmpty()) {
                    for (CustoDespesa cd : c.getLsCustoDespesa()) {

                        valor_total += cd.getCsd_valor();
                    }
                }
                int total = 0;
                List<ProdutoProducao> lsPp = pedDAO.totalProducaoMes(produto.getPro_id(), c.getCus_data_ref().getMonth() + 1, getAno());
                for (ProdutoProducao pp : lsPp) {
                    total += pp.getPrp_quantidade();
                }
                if (total > 0) {
                    valor_uni = valor_total / total;
                    valor_uni = Double.parseDouble(Math.round(valor_uni * 1000) + "") / 1000;
                }
                pc.setTotal(total);
                valor_total = Double.parseDouble(Math.round(valor_total * 1000) + "") / 1000;
                pc.setValor_total(valor_total);
                pc.setValor_unitario(valor_uni);
                pc.setAnodespesa(c.getCus_data_ref());
                lsProdutoCusto.add(pc);

            }
//            for (ProdutoCusto pc : lsProdutoCusto) {
//                lstprodutopedido = pedDAO.totalPedidosAno(produto.getPro_id(), getAno());
//                for (ProdutoProducao pp : lstprodutopedido) {
//                    quantidade = pp.getPrp_quantidade() + quantidade;
//                    pc.setValor_total(pc.getValor_total() / quantidade);
//                }
//            }

        }
        //CARREGA MEDIA DO ANO
        for (ProdutoCusto pc : lsProdutoCusto) {
            i = i + 1;
            valor += pc.getValor_unitario();
        }
        valor = valor / i;
        produtocusto.setMedia_ano(valor);

//        CARREGA MEDIA TOTAL
//        lsCustosAll = cusDAO.findAll();
//
//        if (lsCustosAll != null && !lsCustosAll.isEmpty()) {
//            for (Custo c : lsCustosAll) {
//                ProdutoCusto pc = new ProdutoCusto();
//                double valor_total = 0;
//                double valor_uni = 0;
//                if (c.getLsCustoDespesa() != null && !c.getLsCustoDespesa().isEmpty()) {
//                    for (CustoDespesa cd : c.getLsCustoDespesa()) {
//
//                        valor_total += cd.getCsd_valor();
//                    }
//                }
//                int total = 0;
//                List<ProdutoPedido> lsPp = pedDAO.totalPedidosMes(produto.getPro_id(), c.getCus_data_ref().getMonth() + 1, getAno());
//                for (ProdutoProducao pp : lsPp) {
//                    total += pp.getPrp_quantidade();
//                }
//                if (total > 0) {
//                    valor_uni = valor_total / total;
//                    valor_uni = Double.parseDouble(Math.round(valor_uni * 1000) + "") / 1000;
//                }
//                pc.setTotal(total);
//                valor_total = Double.parseDouble(Math.round(valor_total * 1000) + "") / 1000;
//                pc.setValor_total(valor_total);
//                pc.setValor_unitario(valor_uni);
//                pc.setAnodespesa(c.getCus_data_ref());
//                lsProdutoCustoAll.add(pc);
//            }
//        }
//
//        for (ProdutoCusto pc : lsProdutoCustoAll) {
//            i = i + 1;
//            valor += pc.getValor_unitario();
//        }
//        valor = valor / i;
//        produtocusto.setMedia_total(valor);
    }

    public double getmediaano() {
        carrega();
        return produtocusto.getMedia_ano();
    }

    public double getmediatotal() {
        carrega();
        return produtocusto.getMedia_total();
    }

    public String listar() {
        return "custoprodutolst";
    }

    public void setLsProdutoCusto(List<ProdutoCusto> lsProdutoCusto) {
        this.lsProdutoCusto = lsProdutoCusto;
    }

    public String mesToString(Date d) {
        if (d != null) {
            String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
            return meses[d.getMonth()];
        }
        return "";
    }

    private void clearSession() {
        this.custos = new ArrayDataModel();
        this.despesasmess = new ArrayDataModel();
        this.despesa = new Despesa();
        this.produto = new Produto();
        this.lsProdutos = new ArrayList<>();
        this.lsProdutosAll = new ArrayList<>();
        this.lsProdutoCusto = new ArrayList<>();

        this.Erro = "";

    }
}
