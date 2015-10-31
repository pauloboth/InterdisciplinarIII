package bean;

import dao.CustoDAO;
import dao.DespesaDAO;
import dao.ProdutoDAO;
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
import model.ProdutoCusto;
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

    public DespesaBean() {
    }

    public DataModel getDespesas() {
        clearSession();
        this.despesas = new ListDataModel(dao.findAll(0));
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
            dao.save(despesa);
        } else if (despesa.getDes_tipo() == 3) {
            Calendar cal = new BuddhistCalendar();
            cal.add(Calendar.MONTH, 1);
            cal.getTime();
        }
        clearSession();
        return "despesalst";
    }

    public String listar() {
        return "despesalst";
    }

    public Despesa getDespesa() {
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
        if (produto.getPro_id() == 0) {
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

//    public void CalculaPorcent(ProdutoDespesa pd) {
//        if (this.porcent > 100) {
//            List<ProdutoDespesa> lsPD = despesa.getLsProdutoDespesa();
//            lsPD.remove(pd);
//            porcent = 0;
//            for (ProdutoDespesa p : lsPD) {
//                porcent += p.getPds_porc_part();
//            }
//            pd.setPds_porc_part(100 - porcent);
//            porcent = 100;
//        }
//    }
    public String reloadList() {
        return "despesalst";
    }

    private List<ProdutoCusto> CriaProdutosCusto() {
        List<ProdutoCusto> lsprodCus = new ArrayList<>();
        Despesa d = this.despesa;
        if (d.getLsProdutoDespesa() != null && !d.getLsProdutoDespesa().isEmpty()) {
            for (ProdutoDespesa pd : d.getLsProdutoDespesa()) {
                ProdutoCusto pc = new ProdutoCusto();
                DespesaMes dm = new DespesaMes();
                dm.setDespesa(d);
                dm.setDsm_data(new Date());
                dm.setDsm_data_ref(createDate(1, getMes(), getAno()));
                dm.setDsm_valor(d.getDes_valor_depr());
                pc.setDespesames(dm);
                Custo c = new Custo();

                if (d.getDes_id() > 0) {
                    c = cusDAO.SearchCusto(d.getDes_id(), getMes(), getAno());
                    if (c == null) {
                        c = new Custo();
                    }
                }
                c.setCus_data(new Date());
                c.setCus_preco_produto(pc.getProduto().getPro_preco());
                c.setProduto(pc.getProduto());
                c.setCus_data_ref(createDate(1, getMes(), getAno()));
                c.setLsCustoDespesa(null);

                CustoDespesa cd = new CustoDespesa();
                cd.setDespesa(d);
                cd.setCusto(c);
                cd.setCsd_notas(pc.getNotas());
                cd.setCsd_cadastro(new Date());
                cd.setCsd_valor(pc.getValor_total());
                pc.setCusto(c);
                pc.setCustodespesa(cd);
                pc.setDespesa(d);
                lsprodCus.add(pc);
            }
        }
        return lsprodCus;
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
}
