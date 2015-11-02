package bean;

import dao.CustoDAO;
import dao.DespesaDAO;
import dao.ProdutoDAO;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import model.Produto;
import model.ProdutoDespesa;
import sun.util.BuddhistCalendar;

@ManagedBean
@SessionScoped
public class DespesaBean {
    
    private Despesa despesa = new Despesa();
    private DespesaDAO dao = new DespesaDAO();
    private CustoDAO cusDAO = new CustoDAO();
    private DataModel despesas;
    private boolean isMachin = false;
    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private Produto produto = new Produto();
    private int porcent = 0;
    private int mes = 0;
    private int ano = 0;
    private String Erro = "";
    
    public DespesaBean() {
    }
    
    public DataModel getDespesas() {
        clearSession();
        this.despesas = new ListDataModel(dao.findAll());
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
        if (despesa.getDes_tipo() == 2 || despesa.getDes_tipo() == 4) {
            despesa.setDes_inicio_depr(null);
            dao.save(despesa);
        } else if (despesa.getDes_tipo() == 3) {
            if (porcent == 100) {
                if (!SalvaDespesaParcelada()) {
                    Erro = "Erro ao salvar despesa!";
                    return "despesafrm";
                }
            } else {
                Erro = "A participação de todos os produto deve somar 100%!";
                return "despesafrm";
            }
        }
        clearSession();
        return "despesalst";
    }
    
    public String listar() {
        return "despesalst";
    }
    
    public Despesa getDespesa() {
        if (despesa != null && despesa.getDes_tipo() == 3 && despesa.getDes_inicio_depr() == null) {
            despesa.setDes_inicio_depr(new Date());
        }
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
        if (produto != null && produto.getPro_id() == 0 && lsProdutos != null && !lsProdutos.isEmpty()) {
            produto = lsProdutos.get(0);
        }
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
    
    public String reloadList() {
        return "despesalst";
    }
    
    public int getMes() {
        if (mes == 0) {
            Date d = new Date();
            mes = d.getMonth() + 1;
        }
        return mes;
    }
    
    public int getAno() {
        if (ano == 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            ano = Integer.parseInt(sdf.format(new Date()));
        }
        return ano;
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
    
    public String getErro() {
        return Erro;
    }
    
    private boolean SalvaDespesaParcelada() {
        Despesa d = this.despesa;
        if (d.getLsProdutoDespesa() != null && !d.getLsProdutoDespesa().isEmpty() && d.getDes_depr_mes() > 0) {
            double valor_mes = 0;
            if (d.getDes_depr_mes() > 0) {
                valor_mes = d.getDes_valor_depr() / d.getDes_depr_mes();
            }
            List<DespesaMes> lsDm = new ArrayList<>();
            List<CustoDespesa> lsCd = new ArrayList<>();
            double valor_mes_roud = Double.parseDouble(Math.round(valor_mes * 1000) + "") / 1000;
            for (int i = 0; i < d.getDes_depr_mes(); i++) {
                Calendar cal = new BuddhistCalendar();
                cal.setTime(d.getDes_inicio_depr());
                cal.set(Calendar.DATE, 1);
                cal.add(Calendar.MONTH, i);
                Date data_ref = cal.getTime();
                
                DespesaMes dm = new DespesaMes();
                dm.setDespesa(d);
                dm.setDsm_data(new Date());
                dm.setDsm_data_ref(data_ref);
                dm.setDsm_valor(valor_mes_roud);
                lsDm.add(dm);
                
                for (ProdutoDespesa pd : d.getLsProdutoDespesa()) {
                    CustoDespesa cd = new CustoDespesa();
                    cd.setDespesa(d);
                    cd.setCsd_data(new Date());
                    double valor_uni = (valor_mes * pd.getPrd_por_part()) / 100;
                    valor_uni = Double.parseDouble(Math.round(valor_uni * 1000) + "") / 1000;
                    cd.setCsd_valor(valor_uni);
                    Custo c = new Custo();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                    if (d.getDes_id() > 0) {
                        c = cusDAO.SearchCusto(pd.getProduto().getPro_id(), data_ref.getMonth() + 1, Integer.parseInt(sdf.format(data_ref)));
                        if (c == null) {
                            c = new Custo();
                        }
                    }
                    if (c.getCus_id() == 0) {
                        c.setCus_data(new Date());
                        c.setCus_preco_produto(0);
                        c.setProduto(pd.getProduto());
                        c.setCus_data_ref(data_ref);
                    }
                    cd.setCusto(c);//Add Custo
                    lsCd.add(cd);
                }
            }
            try {
                dao.saveList(despesa, lsDm, lsCd);
                return true;
            } catch (Exception ex) {
            }
        }
        return false;
    }
    
}
