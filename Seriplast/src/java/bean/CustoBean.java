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
    double valor_total = 0;

    private Producao pedido = new Producao();
    private ProducaoDAO pedDAO = new ProducaoDAO();
    private List<ProdutoProducao> lstprodutopedido = new ArrayList<>();

    private Despesa despesa = new Despesa();
    private Produto produto = new Produto();
    private ProdutoCusto produtocusto = new ProdutoCusto();

    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCusto = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCusto_juntando_mes = new ArrayList<>();
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
        //cria atributos
        int quantidade = 0;
        int total_produzido_mes = 0;
        i = 0;
        valor = 0;

        //Cria listas
        lsProdutoCusto = null;
        lsProdutoCusto_juntando_mes = null;
        lstprodutopedido = null;
        lsProdutoCusto = new ArrayList<>();
        lsProdutoCusto_juntando_mes = new ArrayList<>();

        //pega todos os custos do ano, que tiver o produto selecionado
        lsCustos = cusDAO.CustoMensal(produto.getPro_id(), getAno());

        //testa se lista de custo não esta vazia
        if (lsCustos != null && !lsCustos.isEmpty()) {

            // se não estiver, passa todos os custos      
            for (Custo c : lsCustos) {

                double valor_total_mes = 0;
                double valor_total_custo = 0;
                double valor_total_compra_mes = 0;
                double valor_compra_mes = 0;
                double valor_unitario = 0;
                double valor_mes_sp = 0;

                total_produzido_mes = 0;
                //verifica a quantidade produzida do produto selecionado
                List<ProdutoProducao> lsPp = pedDAO.totalProducaoMes(produto.getPro_id(), c.getCus_data_ref().getMonth() + 1, getAno());
                for (ProdutoProducao pp : lsPp) {
                    //passa a lista de produção (saber quantas unidades)
                    total_produzido_mes += pp.getPrp_quantidade();

                }

                //testa se tem despesas ligadas ao custo passado no for
                if (c.getLsCustoDespesa() != null && !c.getLsCustoDespesa().isEmpty()) {
                    //se tiver passa a lista de despesas do custo 
                    
                    for (CustoDespesa cd : c.getLsCustoDespesa()) {
                        valor_total_mes = 0;
                        ProdutoCusto pc = new ProdutoCusto();
                        ///passa valor da despesa para valor_total
                        valor_total_mes += cd.getCsd_valor();
                        valor_compra_mes = cd.getCusto().getCus_preco_produto();
                        valor_total_custo = cd.getCsd_valor();

                        //pega o valor total do mes e soma com o preço de compra do produto (soma todo o valor de compra junto ao custo)
                        valor_mes_sp = valor_total_mes;
                        valor_total_compra_mes = valor_compra_mes * total_produzido_mes;
                        valor_total_mes = valor_total_mes + valor_total_compra_mes;
                        //testa se produziu realmente
                        if (total_produzido_mes > 0) {
                            valor_unitario = valor_total_mes / total_produzido_mes;
                        }
                        //salva
                        
                        pc.setCusto_compra_total(valor_total_compra_mes);
                        pc.setValor_unitario(valor_unitario);
                        pc.setAnodespesa(c.getCus_data_ref());
                        pc.setValor(valor_total_compra_mes+valor_mes_sp);
                        pc.setCusto_compra_unidade(valor_compra_mes);
                        pc.setQuantidade_produzida_mes(total_produzido_mes);
                        pc.setValor_mes(valor_total_custo);
                        pc.setValor_mes_sp(valor_mes_sp);
                       
                        lsProdutoCusto.add(pc);
                    }

                }

            }

        }
        //CARREGA MEDIA DO ANO
        // //passa toda a lista de produtos com custos criada (para calcular o custo anual)
        for (ProdutoCusto pcc : lsProdutoCusto) {
            //pega o valor unitario do ano
            valor += pcc.getValor_unitario();

        }
        //formata valor
        double transforma;
        valor = valor / lsProdutoCusto.size();
        transforma = valor * 100;
        transforma = Double.parseDouble("" + Math.round(transforma)) / 100;
        //seta o valor para pegar na tela
        produtocusto.setMedia_ano(transforma);

    }

//      public void carregabkp() {
//        //cria atributos
//        int quantidade = 0;
//        int quantidade_total = 0;
//        i = 0;
//        valor = 0;
//        
//        //Cria listas
//        lsProdutoCusto = null;
//        lstprodutopedido = null;
//        lsProdutoCusto = new ArrayList<>();
//        //pega todos os custos do ano, que tiver o produto selecionado
//        lsCustos = cusDAO.CustoMensal(produto.getPro_id(), getAno());
//        
//        //testa se lista de custo não esta vazia
//        if (lsCustos != null && !lsCustos.isEmpty()) {
//        // se não estiver, passa todos os custos      
//            for (Custo c : lsCustos) {
//                ProdutoCusto pc = new ProdutoCusto();
//                double valor_total = 0;        
//                double valor_unitario = 0;
//               // double valor_uni_total = 0;
//                
//              //
//                if (c.getLsCustoDespesa() != null && !c.getLsCustoDespesa().isEmpty()) {
//                    for (CustoDespesa cd : c.getLsCustoDespesa()) {
//
//                        valor_total += cd.getCsd_valor();
//                    }
//                }
//                int total_produzido = 0;
//                int total_produzido_total = 0;
//                List<ProdutoProducao> lsPp = pedDAO.totalProducaoMes(produto.getPro_id(), c.getCus_data_ref().getMonth() + 1, getAno());
//                for (ProdutoProducao pp : lsPp) {
//                    total_produzido += pp.getPrp_quantidade();
//                }
//               
//                
//                //////////////////<
////                List<ProdutoProducao> lsPptotal = pedDAO.totalProducao(produto.getPro_id());
////                for (ProdutoProducao pptotal : lsPp) {
////                    total_produzido += pptotal.getPrp_quantidade();
////                    
////                    valor_total_total = valor_total_total + produto.getPro_preco()*total_produzido;
////                if (total_produzido_total > 0) {
////                    valor_uni_total = valor_total_total / total_produzido_total; 
////                }
////                }
//                //////////////////>
//                
//                
//                valor_total = valor_total + produto.getPro_preco()*total_produzido;
//                if (total_produzido > 0) {
//                    valor_unitario = valor_total / total_produzido; 
//                }
//                
//                pc.setCusto_compra(produto.getPro_preco());
//                pc.setTotal(total_produzido);
//                pc.setValor_total(valor_total);
//                pc.setValor_unitario(valor_unitario);
//                pc.setAnodespesa(c.getCus_data_ref());
//                lsProdutoCusto.add(pc);
//
//            }
//            for (ProdutoCusto pc : lsProdutoCusto) {
//                lstprodutopedido = pedDAO.totalProducaoAno(produto.getPro_id(), getAno());
//                for (ProdutoProducao pp : lstprodutopedido) {
//                    quantidade = pp.getPrp_quantidade() + quantidade;
//                 //   pc.setValor_total(pc.getValor_total() / quantidade);
//                   
//                }
//            }
//
//        }
//        //CARREGA MEDIA DO ANO
//        for (ProdutoCusto pcc : lsProdutoCusto) {
//          //  i = i + 1;
//            valor += pcc.getValor_unitario();
//            
//        }
//        //formata valor
//        double transforma;
//        valor = valor / lsProdutoCusto.size();
//        transforma = valor *100;
//        transforma = Double.parseDouble(""+ Math.round(transforma))/100;
//        
//        produtocusto.setMedia_ano(transforma);
//        
//    }
    public void carrega2() {

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
//                List<ProdutoProducao> lsPp = pedDAO.totalProducao(produto.getPro_id());
//                for (ProdutoProducao pp : lsPp) {
//                    total += pp.getPrp_quantidade();
//                }
//                if (total > 0) {
//                    valor_uni = valor_total / total;
//                    //valor_uni = Double.parseDouble(Math.round(valor_uni * 1000) + "") / 1000;
//                }
//                pc.setTotal(total);
//                //valor_total = Double.parseDouble(Math.round(valor_total * 1000) + "") / 1000;
//                pc.setValor_total(valor_total);
//                pc.setValor_unitario(valor_uni);
//                pc.setAnodespesa(c.getCus_data_ref());
//                lsProdutoCustoAll.add(pc);
//            }
//        }
//
//        for (ProdutoCusto pc : lsProdutoCustoAll) {
//           // i = i + 1;
//            valor += pc.getValor_unitario();
//        }
//        double transforma2=0;
//        valor = valor / lsProdutoCustoAll.size();
//        transforma2 = valor *100;
//        transforma2 = Double.parseDouble(""+ Math.round(transforma2))/100;
//        produtocusto.setMedia_total(transforma2);
    }

    public double getmediaano() {
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
