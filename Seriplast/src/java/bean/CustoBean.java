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
    private double valormes;
    private DataModel custos;
    private DataModel despesasmess;
    double valor = 0;
    double valor_total = 0;
    int producao_total = 0;
    int producao_total_geral = 0;
    double valor_total_geral = 0;

    private Producao pedido = new Producao();
    private ProducaoDAO pedDAO = new ProducaoDAO();
    private List<ProdutoProducao> lstprodutopedido = new ArrayList<>();

    private Despesa despesa = new Despesa();
    private Produto produto = new Produto();
    private ProdutoCusto produtocusto = new ProdutoCusto();
    private DataModel produtocustos;

    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCusto = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCusto_juntando_mes = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCusto_detalhado = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCusto_juntando_mes2 = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCustoAll = new ArrayList<>();

    private List<Custo> lsCustos2 = new ArrayList<>();
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

    public String select3() {
        produtocusto = (ProdutoCusto) produtocustos.getRowData();
        return "custoprodutomes";
    }

    public ProdutoCusto getProdutocusto() {
        return produtocusto;
    }

    public void setProdutocusto(ProdutoCusto produtocusto) {
        this.produtocusto = produtocusto;
    }

    public DataModel getProdutocustos() {
        this.produtocustos = new ListDataModel(lsProdutoCusto_juntando_mes2);
        return produtocustos;
    }

    public void setProdutocustos(DataModel produtocustos) {
        this.produtocustos = produtocustos;
    }

    public List<ProdutoCusto> getLsProdutoCusto_juntando_mes() {
        return lsProdutoCusto_juntando_mes;
    }

    public void setLsProdutoCusto_juntando_mes(List<ProdutoCusto> lsProdutoCusto_juntando_mes) {
        this.lsProdutoCusto_juntando_mes = lsProdutoCusto_juntando_mes;
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
        valor_total = 0;
        producao_total = 0;
        double vm1 = 0, vm2 = 0, vm3 = 0, vm4 = 0, vm5 = 0, vm6 = 0, vm7 = 0, vm8 = 0, vm9 = 0, vm10 = 0, vm11 = 0, vm12 = 0;
        double vc1 = 0, vc2 = 0, vc3 = 0, vc4 = 0, vc5 = 0, vc6 = 0, vc7 = 0, vc8 = 0, vc9 = 0, vc10 = 0, vc11 = 0, vc12 = 0;
        double vmd1 = 0, vmd2 = 0, vmd3 = 0, vmd4 = 0, vmd5 = 0, vmd6 = 0, vmd7 = 0, vmd8 = 0, vmd9 = 0, vmd10 = 0, vmd11 = 0, vmd12 = 0;
        double vu1 = 0, vu2 = 0, vu3 = 0, vu4 = 0, vu5 = 0, vu6 = 0, vu7 = 0, vu8 = 0, vu9 = 0, vu10 = 0, vu11 = 0, vu12 = 0;
        int prom1 = 0, prom2 = 0, prom3 = 0, prom4 = 0, prom5 = 0, prom6 = 0, prom7 = 0, prom8 = 0, prom9 = 0, prom10 = 0, prom11 = 0, prom12 = 0;

        //Cria listas
        // lsCustos = null;
        lsProdutoCusto = null;
        lsProdutoCusto_juntando_mes = null;
        lsProdutoCusto_juntando_mes2 = null;
        lstprodutopedido = null;
        lsProdutoCusto = new ArrayList<>();
        lsProdutoCusto_juntando_mes = new ArrayList<>();
        lsProdutoCusto_juntando_mes2 = new ArrayList<>();

        //pega todos os custos do ano, que tiver o produto selecionado
        lsCustos = cusDAO.CustoMensal(produto.getPro_id(), getAno());

        //testa se lista de custo não esta vazia
        if (lsCustos != null && !lsCustos.isEmpty()) {

            // se não estiver, passa todos os custos      
            for (Custo c : lsCustos) {

                double valor_total_mes = 0;
                double valor_total_mesc = 0;
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
//                List<ProdutoProducao> lsPp3 = pedDAO.totalProducaoAno(produto.getPro_id(), c.getCus_data_ref().getYear());
//                for (ProdutoProducao pp : lsPp) {
//                    //passa a lista de produção (saber quantas unidades)
//                   producao_total += pp.getPrp_quantidade();
//
//                }

                //testa se tem despesas ligadas ao custo passado no for
                if (c.getLsCustoDespesa() != null && !c.getLsCustoDespesa().isEmpty()) {
                    //se tiver passa a lista de despesas do custo 

                    for (CustoDespesa cd : c.getLsCustoDespesa()) {
                        valor_total_mes = 0;
                        valor_total_mesc = 0;
                        ProdutoCusto pc = new ProdutoCusto();
                        ///passa valor da despesa para valor_total
                        valor_total_mes += cd.getCsd_valor();
                        valor_total_mesc += cd.getCsd_valor();
                        valor_compra_mes = cd.getCusto().getCus_preco_produto();
                        valor_total_custo = cd.getCsd_valor();

                        //pega o valor total do mes e soma com o preço de compra do produto (soma todo o valor de compra junto ao custo)
                        valor_mes_sp = valor_total_mes;
                        valor_total_compra_mes = valor_compra_mes * total_produzido_mes;
                        //valor_total_mes +=  valor_total_compra_mes;
                        // valor_total_mesc  +=  valor_total_compra_mes;

                        //testa se produziu realmente
                        if (total_produzido_mes > 0) {
                            valor_unitario = (valor_total_mesc + (total_produzido_mes * (produto.getPro_preco() / c.getLsCustoDespesa().size()))) / total_produzido_mes;
                        }
                       // valor_total += valor_mes_sp;

                        //salva
                        pc.setNomedespesa(cd.getDespesa().getDes_nome());
                        pc.setCusto_compra_total(valor_total_compra_mes);
                        pc.setValor_unitario(valor_unitario);
                        pc.setAnodespesa(c.getCus_data_ref());
                        //pc.setValor(valor_total_compra_mes + valor_mes_sp);
                        pc.setValor(valor_mes_sp);
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
            lsProdutoCusto_juntando_mes.add(pcc);

        }

        for (ProdutoCusto pc : lsProdutoCusto_juntando_mes) {
            if (pc.getAnodespesa().getMonth() + 1 == 1) {
                prom1 = pc.getQuantidade_produzida_mes();
                vc1 = pc.getCusto_compra_total();
                vmd1 += pc.getValor_mes_sp();
                vm1 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu1 = vm1 / prom1;
                }
            }
            if (pc.getAnodespesa().getMonth() + 1 == 2) {
                prom2 = pc.getQuantidade_produzida_mes();
                vc2 = pc.getCusto_compra_total();
                vmd2 += pc.getValor_mes_sp();
                vm2 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu2 = vm2 / prom2;
                }
            }
            if (pc.getAnodespesa().getMonth() + 1 == 3) {
                prom3 = pc.getQuantidade_produzida_mes();
                vc3 = pc.getCusto_compra_total();
                vmd3 += pc.getValor_mes_sp();
                vm3 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu3 = vm3 / prom3;
                }
            }
            if (pc.getAnodespesa().getMonth() + 1 == 4) {
                prom4 = pc.getQuantidade_produzida_mes();
                vc4 = pc.getCusto_compra_total();
                vmd4 += pc.getValor_mes_sp();
                vm4 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu4 = vm4 / prom4;
                }
            }
            if (pc.getAnodespesa().getMonth() + 1 == 5) {
                prom5 = pc.getQuantidade_produzida_mes();
                vc5 = pc.getCusto_compra_total();
                vmd5 += pc.getValor_mes_sp();
                vm5 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu5 = vm5 / prom5;
                }
            }
            if (pc.getAnodespesa().getMonth() + 1 == 6) {
                prom6 = pc.getQuantidade_produzida_mes();
                vc6 = pc.getCusto_compra_total();
                vmd6 += pc.getValor_mes_sp();
                vm6 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu6 = vm6 / prom6;
                }
            }
            if (pc.getAnodespesa().getMonth() + 1 == 7) {
                prom7 = pc.getQuantidade_produzida_mes();
                vc7 = pc.getCusto_compra_total();
                vmd7 += pc.getValor_mes_sp();
                vm7 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu7 = vm7 / prom7;
                }
            }
            if (pc.getAnodespesa().getMonth() + 1 == 8) {
                prom8 = pc.getQuantidade_produzida_mes();
                vc8 = pc.getCusto_compra_total();
                vmd8 += pc.getValor_mes_sp();
                vm8 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu8 = vm8 / prom8;
                }
            }
            if (pc.getAnodespesa().getMonth() + 1 == 9) {
                prom9 = pc.getQuantidade_produzida_mes();
                vc9 = pc.getCusto_compra_total();
                vmd9 += pc.getValor_mes_sp();
                vm9 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu9 = vm9 / prom9;
                }

            }
            if (pc.getAnodespesa().getMonth() + 1 == 10) {
                prom10 = pc.getQuantidade_produzida_mes();
                vc10 = pc.getCusto_compra_total();
                vmd10 += pc.getValor_mes_sp();
                vm10 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu10 = vm10 / prom10;
                }
            }
            if (pc.getAnodespesa().getMonth() + 1 == 11) {
                prom11 = pc.getQuantidade_produzida_mes();
                vc11 = pc.getCusto_compra_total();
                vmd11 += pc.getValor_mes_sp();
                vm11 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu11 = vm11 / prom11;
                }
            }
            if (pc.getAnodespesa().getMonth() == 12) {
                prom12 = pc.getQuantidade_produzida_mes();
                vc12 = pc.getCusto_compra_total();
                vmd12 += pc.getValor_mes_sp();
                vm12 += pc.getValor();
                if (pc.getQuantidade_produzida_mes() > 0) {
                    vu12 = vm12 / prom12;
                }

            }
        }

        for (int i = 1; i <= 12; i++) {
            if (i == 1) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(1);
                pc.setMesstring("Janeiro");
                pc.setQuantidade_produzida_mes(prom1);
                pc.setCusto_compra_total(vc1);
                pc.setValor_mes_sp(vmd1);
                pc.setValor(vm1);
                pc.setValor_unitario(vu1);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 2) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(2);
                pc.setMesstring("Fevereiro");
                pc.setQuantidade_produzida_mes(prom2);
                pc.setCusto_compra_total(vc2);
                pc.setValor_mes_sp(vmd2);
                pc.setValor(vm2);
                pc.setValor_unitario(vu2);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 3) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(3);
                pc.setMesstring("Março");
                pc.setQuantidade_produzida_mes(prom3);
                pc.setCusto_compra_total(vc3);
                pc.setValor_mes_sp(vmd3);
                pc.setValor(vm3);
                pc.setValor_unitario(vu3);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 4) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(4);
                pc.setMesstring("Abril");
                pc.setQuantidade_produzida_mes(prom4);
                pc.setCusto_compra_total(vc4);
                pc.setValor_mes_sp(vmd4);
                pc.setValor(vm4);
                pc.setValor_unitario(vu4);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 5) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(5);
                pc.setMesstring("Maio");
                pc.setQuantidade_produzida_mes(prom5);
                pc.setCusto_compra_total(vc5);
                pc.setValor_mes_sp(vmd5);
                pc.setValor(vm5);
                pc.setValor_unitario(vu5);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 6) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(6);
                pc.setMesstring("Junho");
                pc.setQuantidade_produzida_mes(prom6);
                pc.setCusto_compra_total(vc6);
                pc.setValor_mes_sp(vmd6);
                pc.setValor(vm6);
                pc.setValor_unitario(vu6);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 7) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(7);
                pc.setMesstring("Julho");
                pc.setQuantidade_produzida_mes(prom7);
                pc.setCusto_compra_total(vc7);
                pc.setValor_mes_sp(vmd7);
                pc.setValor(vm7);
                pc.setValor_unitario(vu7);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 8) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(8);
                pc.setMesstring("Agosto");
                pc.setQuantidade_produzida_mes(prom8);
                pc.setCusto_compra_total(vc8);
                pc.setValor_mes_sp(vmd8);
                pc.setValor(vm8);
                pc.setValor_unitario(vu8);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 9) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(9);
                pc.setMesstring("Setembro");
                pc.setQuantidade_produzida_mes(prom9);
                pc.setCusto_compra_total(vc9);
                pc.setValor_mes_sp(vmd9);
                pc.setValor(vm9);
                pc.setValor_unitario(vu9);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 10) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(10);
                pc.setMesstring("Outubro");
                pc.setQuantidade_produzida_mes(prom10);
                pc.setCusto_compra_total(vc10);
                pc.setValor_mes_sp(vmd10);
                pc.setValor(vm10);
                pc.setValor_unitario(vu10);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 11) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(11);
                pc.setMesstring("Novembro");
                pc.setQuantidade_produzida_mes(prom11);
                pc.setCusto_compra_total(vc11);
                pc.setValor_mes_sp(vmd11);
                pc.setValor(vm11);
                pc.setValor_unitario(vu11);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
            if (i == 12) {
                ProdutoCusto pc = new ProdutoCusto();
                pc.setMess(12);
                pc.setMesstring("Dezembro");
                pc.setQuantidade_produzida_mes(prom12);
                pc.setCusto_compra_total(vc12);
                pc.setValor_mes_sp(vmd12);
                pc.setValor(vm12);
                pc.setValor_unitario(vu12);
                lsProdutoCusto_juntando_mes2.add(pc);
            }
        }
        for (ProdutoCusto pc : lsProdutoCusto_juntando_mes2) {
            producao_total += pc.getQuantidade_produzida_mes();
            valor_total += pc.getValor();
        }
        valor_total += producao_total * produto.getPro_preco();

        //formata valor
        double transforma;
        valor = valor_total / producao_total;
        transforma = valor * 100;
        transforma = Double.parseDouble("" + Math.round(transforma)) / 100;
        //seta o valor para pegar na tela
        produtocusto.setMedia_ano(transforma);

    }

    public double getmediaano() {
        //  produtocusto.setMedia_ano(valor_total);
        return produtocusto.getMedia_ano();
    }

    public double getmediatotal(Produto p) {
        valor_total_geral = 0;
        producao_total_geral = 0;
        lsCustos2 = null;

        lsCustos2 = cusDAO.CustoAnual(p.getPro_id());
        List<ProdutoProducao> lsPp = pedDAO.totalProducao(p.getPro_id());
        for (ProdutoProducao pp : lsPp) {
            //passa a lista de produção (saber quantas unidades)
            producao_total_geral += pp.getPrp_quantidade();
        }
        for (Custo c : lsCustos2) {
            for (CustoDespesa cd : c.getLsCustoDespesa()) {
                valor_total_geral += cd.getCsd_valor();
            }
        }
        valor_total_geral += producao_total_geral * p.getPro_preco();
        valor_total_geral = valor_total_geral / producao_total_geral;
        return valor_total_geral;

    }

    public String listar() {
        return "custoprodutolst";
    }

    public String listar2() {
        return "custoprodutoano";
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

    public List<ProdutoCusto> getLsProdutoCusto_juntando_mes2() {
        return lsProdutoCusto_juntando_mes2;
    }

    public void setLsProdutoCusto_juntando_mes2(List<ProdutoCusto> lsProdutoCusto_juntando_mes2) {
        this.lsProdutoCusto_juntando_mes2 = lsProdutoCusto_juntando_mes2;
    }

    public List<ProdutoCusto> getLsProdutoCusto_detalhado() {
        lsProdutoCusto_detalhado = null;
        int total_produzido_mess = 0;
        double valorr = 0;
        double cc;
        lsProdutoCusto_detalhado = new ArrayList<>();
        List<ProdutoProducao> lsPp = pedDAO.totalProducaoMes(produto.getPro_id(), produtocusto.getMess(), getAno());

        for (ProdutoProducao pp : lsPp) {
            //passa a lista de produção (saber quantas unidades)
            total_produzido_mess += pp.getPrp_quantidade();
        }
        ProdutoCusto pcc = new ProdutoCusto();
        pcc.setNomedespesa("Custo de Compra");
        pcc.setValor(total_produzido_mess * produto.getPro_preco());
        pcc.setValor_unitario(total_produzido_mess * produto.getPro_preco());
        lsProdutoCusto_detalhado.add(pcc);
        for (ProdutoCusto pc : lsProdutoCusto) {

            if (pc.getAnodespesa().getMonth() + 1 == produtocusto.getMess()) {
                lsProdutoCusto_detalhado.add(pc);
            }

        }

        return lsProdutoCusto_detalhado;
    }

    public void setLsProdutoCusto_detalhado(List<ProdutoCusto> lsProdutoCusto_detalhado) {
        this.lsProdutoCusto_detalhado = lsProdutoCusto_detalhado;
    }

    public double getValormes() {
        return valormes;
    }

    public void setValormes(double valormes) {
        this.valormes = valormes;
    }
}
