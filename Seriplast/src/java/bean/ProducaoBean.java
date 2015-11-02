package bean;

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
import model.Producao;
import model.Produto;
import model.ProdutoProducao;

@ManagedBean
@SessionScoped
public class ProducaoBean {

    private Producao producao = new Producao();
    private ProducaoDAO dao = new ProducaoDAO();
    private DataModel producoes;

    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private Produto produto = new Produto();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private int mes = 0;
    private int ano = 0;

    public ProducaoBean() {
    }

    public DataModel getProducoes() {
        clearSession();

        this.producoes = new ListDataModel(dao.findMes(mes, ano));
        return producoes;
    }

    public void setProducoes(DataModel i) {
        this.producoes = i;
    }

    public String edit(Producao i) {
        producao = dao.findEdit(i.getPrd_id());
        mes = producao.getPrd_data_ref().getMonth() + 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        ano = Integer.parseInt(sdf.format(producao.getPrd_data_ref()));
        return "producaofrm";
    }

    public String delete(Producao i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        clearSession();
        return "producaolst";
    }

    public String selectProducao() {
        producao = (Producao) producoes.getRowData();
        return "producaoview";
    }

    public String salvar() {
        Date d = createDate(1, mes, ano);
        producao.setPrd_data_ref(d);
        dao.save(producao);
        clearSession();
        return "producaolst";
    }

    public String listar() {
        return "producaolst";
    }

    public Producao getProducao() {
        return producao;
    }

    public void setProducao(Producao producao) {
        this.producao = producao;
    }

    public List<Produto> getLsProdutos() {
        lsProdutos = proDAO.findAll(1);
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

    public void addProdutoP() {
        if (produto != null) {
            if (this.producao.getLsProdutoProducao()== null) {
                this.producao.setLsProdutoProducao(new ArrayList<ProdutoProducao>());
            }
            boolean bAdd = true;
            for (ProdutoProducao pm : this.producao.getLsProdutoProducao()) {
                if (pm.getProduto().getPro_id() == produto.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                ProdutoProducao pm = new ProdutoProducao();
                pm.setProducao(producao);
                pm.setProduto(produto);
                this.producao.getLsProdutoProducao().add(pm);
                produto = new Produto();
            }
            reloadProdutos();
        }
    }

    public void removeProduto(ProdutoProducao pd) {
        this.producao.getLsProdutoProducao().remove(pd);
        reloadProdutos();
    }

    private void reloadProdutos() {
        if (producao == null) {
            producao = new Producao();
        }
        if (producao.getLsProdutoProducao() == null) {
            producao.setLsProdutoProducao(new ArrayList<ProdutoProducao>());
        }
        lsProdutos = new ArrayList<>();
        if (lsProdutosAll != null) {
            for (Produto p : lsProdutosAll) {
                boolean bAdd = true;
                for (ProdutoProducao pm : this.producao.getLsProdutoProducao()) {
                    if (pm.getProduto().getPro_id() == p.getPro_id()) {
                        bAdd = false;
                    }
                }
                if (bAdd) {
                    lsProdutos.add(p);
                }
            }
        }
    }

    private void clearSession() {
        this.producao = new Producao();
        this.producao.setLsProdutoProducao(new ArrayList<ProdutoProducao>());
        this.produto = new Produto();
        this.lsProdutos = new ArrayList<>();
        this.lsProdutosAll = new ArrayList<>();
        this.producoes = new ArrayDataModel();
    }

    public int getMes() {
        if (mes == 0) {
            Date d = new Date();
            mes = d.getMonth() + 1;
        }
        return mes;
    }

    public String getMesString() {
        if (mes == 0) {
            Date d = new Date();
            mes = d.getMonth() + 1;
        }
        if (mes == 1) {
            return "Janeiro";
        }
        if (mes == 2) {
            return "Fevereiro";
        }
        if (mes == 3) {
            return "Março";
        }
        if (mes == 4) {
            return "Abril";
        }
        if (mes == 5) {
            return "Maio";
        }
        if (mes == 6) {
            return "Junho";
        }
        if (mes == 7) {
            return "Julho";
        }
        if (mes == 8) {
            return "Agosto";
        }
        if (mes == 9) {
            return "Setembro";
        }
        if (mes == 10) {
            return "Outubro";
        }
        if (mes == 11) {
            return "Novembro";
        }
        if (mes == 12) {
            return "Dezembro";
        } else {
            return "ERRO";
        }

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

    private Date createDate(int d, int m, int a) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = (Date) sdf.parse(d + "-" + m + "-" + a + "-");
        } catch (ParseException ex) {
        }
        return date;
    }
}
