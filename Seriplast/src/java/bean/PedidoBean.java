package bean;

import dao.PedidoDAO;
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
import model.Pedido;
import model.Produto;
import model.ProdutoPedido;

@ManagedBean
@SessionScoped
public class PedidoBean {

    private Pedido pedido = new Pedido();
    private PedidoDAO dao = new PedidoDAO();
    private DataModel pedidos;

    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private Produto produto = new Produto();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private int mes = 0;
    private int ano = 0;

    public PedidoBean() {
    }

    public DataModel getPedidos() {
        clearSession();

        this.pedidos = new ListDataModel(dao.findMes(mes, ano));
        return pedidos;
    }

    public void setPedidos(DataModel i) {
        this.pedidos = i;
    }

    public String edit(Pedido i) {
        pedido = dao.findEdit(i.getPed_id());
        mes = pedido.getPed_data_ref().getMonth() + 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        ano = Integer.parseInt(sdf.format(pedido.getPed_data_ref()));
        return "pedidofrm";
    }

    public String delete(Pedido i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        clearSession();
        return "pedidolst";
    }

    public String selectPedido() {
        pedido = (Pedido) pedidos.getRowData();
        return "pedidoview";
    }

    public String salvar() {
        Date d = createDate(1, mes, ano);
        pedido.setPed_data_ref(d);
        dao.save(pedido);
        clearSession();
        return "pedidolst";
    }

    public String listar() {
        return "pedidolst";
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
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
            if (this.pedido.getLsProdutoPedido() == null) {
                this.pedido.setLsProdutoPedido(new ArrayList<ProdutoPedido>());
            }
            boolean bAdd = true;
            for (ProdutoPedido pm : this.pedido.getLsProdutoPedido()) {
                if (pm.getProduto().getPro_id() == produto.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                ProdutoPedido pm = new ProdutoPedido();
                pm.setPedido(pedido);
                pm.setProduto(produto);
                this.pedido.getLsProdutoPedido().add(pm);
                produto = new Produto();
            }
            reloadProdutos();
        }
    }

    public void removeProduto(ProdutoPedido pd) {
        this.pedido.getLsProdutoPedido().remove(pd);
        reloadProdutos();
    }

    private void reloadProdutos() {
        if (pedido == null) {
            pedido = new Pedido();
        }
        if (pedido.getLsProdutoPedido() == null) {
            pedido.setLsProdutoPedido(new ArrayList<ProdutoPedido>());
        }
        lsProdutos = new ArrayList<>();
        for (Produto p : lsProdutosAll) {
            boolean bAdd = true;
            for (ProdutoPedido pm : this.pedido.getLsProdutoPedido()) {
                if (pm.getProduto().getPro_id() == p.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                lsProdutos.add(p);
            }
        }
    }

    private void clearSession() {
        this.pedido = new Pedido();
        this.pedido.setLsProdutoPedido(new ArrayList<ProdutoPedido>());
        this.produto = new Produto();
        this.lsProdutos = new ArrayList<>();
        this.lsProdutosAll = new ArrayList<>();
        this.pedidos = new ArrayDataModel();
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
