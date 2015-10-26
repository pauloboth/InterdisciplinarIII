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
public class CustoBean {

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

    private String Erro = "";
    private double valor = 0;
    private double restValor = valor;
    private int watts = 0;
    private int porcent = 0;
    private int mes = 0;
    private int ano = 0;
    private boolean luz = false;
    private boolean bSave = false;

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

    public String salvar() {
        dao.saveList(CriaProdutoCusto());
        clearSession();
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.restValor = valor;
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
        boolean bRemov = true;
        if (this.lsProdutoCustoMaq != null) {
            for (ProdutoCusto pc : this.lsProdutoCustoMaq) {
                if (pd.getProduto().getPro_id() == pc.getProduto().getPro_id()) {
                    bRemov = false;
                }
            }
        }
        if (bRemov) {
            lsProdutoCusto.remove(pd);
        } else {
            Erro = "Este produto não pode ser removido pporque faz parte de uma máquina";
        }
        reloadProdutos();
    }

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
        CalcularProdutoCusto();
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
        CalculaLuzMaquina();
        return lsProdutoCustoMaq;
    }

    public void setLsProdutoCustoMaq(List<ProdutoCusto> lsProdutoCustoMaq) {
        this.lsProdutoCustoMaq = lsProdutoCustoMaq;
    }

    public boolean isLuz() {
        if (despesa.getDes_tipo() == 1) {
            luz = true;
        } else {
            luz = true;
        }
        return luz;
    }

    public int getWatts() {
        return watts;
    }

    public void setWatts(int watts) {
        this.watts = watts;
    }

    public String getErro() {
        return Erro;
    }

    public boolean isBSave() {
        return bSave;
    }

    private void CalculaLuzMaquina() {
        double valorTotal = 0;
        if (this.lsProdutoCustoMaq != null && !this.lsProdutoCustoMaq.isEmpty()) {
            for (ProdutoCusto pc : this.lsProdutoCustoMaq) {
                try {
                    if (pc.getTotal() > 0 && pc.getTempo() > 0 && pc.getMaquina().getMaq_watss_hora() > 0) {
                        int totalS = pc.getTotal() * pc.getTempo();
                        double totalD = totalS;
                        System.out.println("total* tempo: " + totalD);
                        BigDecimal totalH = BigDecimal.valueOf((totalD / 60) / 60);
                        System.out.println("totalH: " + totalH);
                        BigDecimal precoM = BigDecimal.valueOf(this.valor / this.watts);
                        System.out.println("precoM: " + precoM);
                        BigDecimal wattsGasto = totalH.multiply(new BigDecimal(pc.getMaquina().getMaq_watss_hora()), MathContext.DECIMAL128);
                        System.out.println("wattsGasto: " + wattsGasto);
                        BigDecimal valorF = wattsGasto.multiply(precoM);
                        System.out.println("valorF: " + valorF);
                        double valorF1 = Double.parseDouble(valorF + "");
                        double d1 = Math.round(valorF1 * 1000);
                        valorF1 = d1 / 1000;
                        System.out.println("valorF1: " + valorF1);
                        pc.setValor(valorF1);
                        valorTotal += valorF1;
                        System.out.println("-----------------");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (this.valor > valorTotal) {
                this.restValor = this.valor - valorTotal;
            }
            if (valorTotal > this.valor) {
                this.Erro = "O Valor total gasto foi maior que o informado!";
                this.bSave = false;
            } else if (this.valor > 0) {
                this.bSave = true;
            }

        }
    }

    private void CalcularProdutoCusto() {
        this.porcent = 0;
        if (this.lsProdutoCustoMaq == null) {
            this.lsProdutoCustoMaq = new ArrayList<>();
        }
        if (this.lsProdutoCusto == null) {
            this.lsProdutoCusto = new ArrayList<>();
        }
        for (ProdutoCusto pc : this.lsProdutoCusto) {
            this.porcent += pc.getParticipacao();
            double valorT = pc.getProduto().getPro_preco();
            if (this.restValor > 0) {
                double val = (this.restValor * pc.getParticipacao()) / 100;
                valorT += val;
                val = Math.round(val * 1000);
                val = val / 1000;
                pc.setValor(val);
            }

            for (ProdutoCusto pcm : this.lsProdutoCustoMaq) {
                if (pcm.getProduto().getPro_id() == pc.getProduto().getPro_id()) {
                    valorT += pcm.getValor();
                }
            }

            if (pc.getTotal() > 0) {
                double valU = valorT / pc.getTotal();
                valU = Math.round(valU * 1000);
                valU = valU / 1000;
                pc.setValor_unitario(valU);
            }
            valorT = Math.round(valorT * 1000);
            valorT = valorT / 1000;
            pc.setValor_total(valorT);
        }
        if (this.porcent != 100) {
            this.bSave = false;
        }
    }

    private List<ProdutoCusto> CriaProdutoCusto() {
        List<ProdutoCusto> ls = new ArrayList<>();
        if (this.luz && this.lsProdutoCusto != null && !this.lsProdutoCusto.isEmpty()) {
            for (ProdutoCusto pc : this.lsProdutoCusto) {
                Custo c = new Custo();
                Despesa d = this.despesa;
                if (d.getDes_id() > 0) {
                    c = dao.SearchCusto(d.getDes_id(), getMes(), getAno());
                } else {
                    d.setDes_tipo(5);
                    d.setDes_status(2);
                    d.setDes_date(new Date());
                }
                d.setLsCustoDespesa(null);
                d.setLsDespesaMes(null);

                c.setCus_data(new Date());
                c.setCus_preco_produto(pc.getProduto().getPro_preco());
                c.setProduto(pc.getProduto());
                c.setCus_data(createDate(1, getMes(), getAno()));
                c.setLsCustoDespesa(null);

                DespesaMes dm = new DespesaMes();
                dm.setDespesa(d);
                dm.setDsm_data(new Date());
                dm.setDsm_data_ref(createDate(1, getMes(), getAno()));
                dm.setDsm_quantidade(this.watts);
                dm.setDsm_valor(valor);
                pc.setDespesames(dm);

                CustoDespesa cd = new CustoDespesa();
                cd.setDespesa(d);
                cd.setCusto(c);
                cd.setCsd_notas(pc.getNotas());
                cd.setCsd_cadastro(new Date());
                cd.setCsd_valor(pc.getValor_total());
                pc.setCusto(c);
                pc.setCustodespesa(cd);
                ls.add(pc);
            }
        }
        return ls;
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
        this.Erro = "";
        this.valor = 0;
        this.restValor = valor;
        this.watts = 0;
        this.porcent = 0;
        this.mes = 0;
        this.ano = 0;
        this.luz = false;
        this.bSave = false;
    }
}
