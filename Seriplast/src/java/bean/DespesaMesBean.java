package bean;

import dao.CustoDAO;
import dao.DespesaDAO;
import dao.DespesaMesDAO;
import dao.MaquinaDAO;
import dao.ProducaoDAO;
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
import model.ProdutoProducao;

@ManagedBean
@SessionScoped
public class DespesaMesBean {

    private CustoDAO dao = new CustoDAO();
    private DespesaDAO desDAO = new DespesaDAO();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private ProducaoDAO prdDAO = new ProducaoDAO();
    private MaquinaDAO maqDAO = new MaquinaDAO();
    private DespesaMesDAO dsmDAO = new DespesaMesDAO();

    private DataModel despesasmes;

    private Despesa despesa = new Despesa();
    private Produto produto = new Produto();

    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCusto = new ArrayList<>();
    private List<ProdutoCusto> lsProdutoCustoMaq = new ArrayList<>();
    private List<Maquina> lsMaquinas = new ArrayList<>();
    private List<DespesaMes> lsDespesaMes = new ArrayList<>();

    private String Erro = "";
    private double valor = 0;
    private double restValor = valor;
    private int watts = 0;
    private int porcent = 0;
    private int mes = 0;
    private int ano = 0;
    private boolean luz = false;
    private boolean bSave = false;

    public DespesaMesBean() {
    }

    public List<DespesaMes> getLsDespesaMes() {
        clearSession();
        List<Despesa> des = desDAO.searchDespesasMes(getMes(), getAno());
        lsDespesaMes = new ArrayList<>();
        if (des != null) {
            for (Despesa d : des) {
                DespesaMes dm = dsmDAO.findDespesaMes(d.getDes_id(), getMes(), getAno());
                List<CustoDespesa> lsCd = desDAO.findCustoDespesaMes(d.getDes_id(), getMes(), getAno());
                if (lsCd == null || lsCd.isEmpty()) {
                    dm = new DespesaMes();
                    dm.setDespesa(d);
                    if (d.getDes_status() != 1) {
                        dm = null;
                    }
                }
                if (dm != null) {
                    lsDespesaMes.add(dm);
                }
            }
        }
        return lsDespesaMes;
    }

    public String delete(Custo i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        return "despesameslst";
    }

    public String salvar() {
        CalculaLuzMaquina();
        CalcularProdutoCusto();
        if (bSave) {
            dao.saveList(CriaProdutoCusto());
            clearSession();
            return "despesameslst";
        }
        return "despesamesfrm";
    }

    public String listar() {
        return "despesameslst";
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
                produto.setLsProdutoProducao(prdDAO.totalProducaoMes(produto.getPro_id(), getMes(), getAno()));
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
        if (luz && this.lsProdutoCustoMaq != null) {
            for (ProdutoCusto pc : this.lsProdutoCustoMaq) {
                if (pd.getProduto().getPro_id() == pc.getProduto().getPro_id()) {
                    bRemov = false;
                }
            }
        }
        if (bRemov) {
            lsProdutoCusto.remove(pd);
            Erro = "";
        } else {
            Erro = "Este produto não pode ser removido porque faz parte de uma máquina";
        }
        reloadProdutos();
    }

    public String Lancar(int id) {
        lsProdutoCusto = new ArrayList<>();
        if (id != 0) {
            despesa = desDAO.findEdit(id);
            if (despesa.getDes_tipo() == 1) {
                luz = true;
            }
            DespesaMes desMes = dsmDAO.findDespesaMes(despesa.getDes_id(), getMes(), getAno());
            if (desMes != null && desMes.getDsm_id() > 0) {
                valor = desMes.getDsm_valor();
                watts = desMes.getDsm_quantidade();
            } else {
                valor = 0;
                watts = 0;
            }
            for (ProdutoDespesa pd : despesa.getLsProdutoDespesa()) {
                ProdutoCusto pc = new ProdutoCusto();
                Custo c = new Custo();
                pc.setProduto(pd.getProduto());
                pc.getProduto().setLsProdutoProducao(prdDAO.totalProducaoMes(pd.getProduto().getPro_id(), getMes(), getAno()));

                c.setCus_preco_produto(pd.getProduto().getPro_preco());
                pc.setCusto(c);
                pc.setParticipacao(pd.getPrd_por_part());
                lsProdutoCusto.add(pc);
            }
        } else {
            clearSession();
        }
        return "despesamesfrm";
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

    public DataModel getDespesasmes() {
        clearSession();
        despesasmes = new ListDataModel(Despesas());
        return despesasmes;
    }

    public void setDespesasmes(DataModel despesasmes) {
        this.despesasmes = despesasmes;
    }

    private List<Despesa> Despesas() {
        List<Despesa> desMes = desDAO.searchDespesasMes(getMes(), getAno());
        return desMes;
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

    public int totalProducao(Produto p) {
        p.setLsProdutoProducao(prdDAO.totalProducaoMes(p.getPro_id(), mes, ano));
        int total = 0;
        if (p.getLsProdutoProducao() != null) {
            for (ProdutoProducao pp : p.getLsProdutoProducao()) {
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
        if (luz) {
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
                        boolean bAdd = true;
                        for (ProdutoCusto pcs : lsProdutoCusto) {
                            if (pcs.getProduto().getPro_id() == pc.getProduto().getPro_id()) {
                                bAdd = false;
                            }
                        }
                        if (bAdd) {
                            lsProdutoCusto.add(pc);
                        }
                    }
                }
            }
            CalculaLuzMaquina();
        }
        return lsProdutoCustoMaq;
    }

    public void setLsProdutoCustoMaq(List<ProdutoCusto> lsProdutoCustoMaq) {
        this.lsProdutoCustoMaq = lsProdutoCustoMaq;
    }

    public boolean isLuz() {
        luz = despesa.getDes_tipo() == 1;
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
                Erro = "";
                this.bSave = true;
            }

        }
    }

    private void CalcularProdutoCusto() {
        this.porcent = 0;
        if (this.lsProdutoCustoMaq == null || !luz) {
            this.lsProdutoCustoMaq = new ArrayList<>();
        }
        if (this.lsProdutoCusto == null) {
            this.lsProdutoCusto = new ArrayList<>();
        }
        for (ProdutoCusto pc : this.lsProdutoCusto) {
            this.porcent += pc.getParticipacao();
            double valorT = 0;
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
        } else if (!luz) {
            this.bSave = true;
        }
    }

    private ProdutoCusto CriaProdutoCusto() {
        ProdutoCusto prodCus = new ProdutoCusto();
        if (this.lsProdutoCusto != null && !this.lsProdutoCusto.isEmpty()) {
            Despesa d = this.despesa;
            if (d.getDes_id() == 0) {
                d.setDes_tipo(5);
                d.setDes_status(2);
                d.setDes_date(new Date());
                d.setLsCustoDespesa(null);
            }
            prodCus.setDespesa(d);

            DespesaMes dm = new DespesaMes();
            dm.setDespesa(d);
            dm.setDsm_data(new Date());
            dm.setDsm_data_ref(createDate(1, getMes(), getAno()));
            dm.setDsm_quantidade(this.watts);
            dm.setDsm_valor(valor);
            prodCus.setDespesames(dm);
            List<CustoDespesa> lscd = new ArrayList<>();
            for (ProdutoCusto pc : this.lsProdutoCusto) {
                Custo c = dao.SearchCusto(pc.getProduto().getPro_id(), getMes(), getAno());
                if (c == null) {
                    c = new Custo();
                }
                c.setCus_preco_produto(pc.getProduto().getPro_preco());
                c.setProduto(pc.getProduto());
                c.setCus_data_ref(createDate(1, getMes(), getAno()));
                c.setLsCustoDespesa(null);

                CustoDespesa cd = new CustoDespesa();
                cd.setDespesa(d);
                cd.setCusto(c);
                cd.setCsd_notas(pc.getNotas());
                cd.setCsd_data(new Date());
                cd.setCsd_valor(pc.getValor_total());
                pc.setCusto(c);
                pc.setCustodespesa(cd);
                pc.setDespesa(d);
                lscd.add(cd);
            }
            prodCus.setLsCustoDespsa(lscd);
        }
        return prodCus;
    }

    public String LastMonth(Despesa d) {
        String m = "Nenhum";
        try {
            DespesaMes dm = desDAO.lastMonth(d.getDes_id());
            if (dm.getDsm_id() > 0) {
                m = mesToString(dm.getDsm_data_ref());
            }
        } catch (Exception ex) {
        }
        return m;
    }

    public String mesToString(Date d) {
        if (d != null) {
            String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
            return meses[d.getMonth()];
        }
        return "";
    }

    private void clearSession() {
        this.despesasmes = new ArrayDataModel();
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
//        this.mes = 0;
//        this.ano = 0;
        this.luz = false;
        this.bSave = false;
    }

    public String ExcluirLance(int des_id) {
        try {
            List<CustoDespesa> lsCd = desDAO.findCustoDespesaMes(des_id, getMes(), getAno());
            DespesaMes dm = dsmDAO.findUnicaDespesa(des_id);
            Despesa d = desDAO.findById(des_id);
            desDAO.ExcluirLance(lsCd, dm, d);
        } catch (Exception ex) {
            Erro = "Não foi possível exluir o parcionamento deste lançamento!";
        }
        return "despesameslst";
    }
}
