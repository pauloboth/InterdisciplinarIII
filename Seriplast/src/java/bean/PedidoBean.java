package bean;

import dao.PedidoDAO;
import dao.ProdutoDAO;
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
    private List<Integer> anos = new ArrayList<>();
    private int ano;

    public PedidoBean() {
    }

    public DataModel getPedidos() {
        clearSession();
        this.pedidos = new ListDataModel(dao.findAll());
        return pedidos;
    }

    public void setPedidos(DataModel i) {
        this.pedidos = i;
    }

    public String edit(Pedido i) {
        pedido = dao.findEdit(i.getPed_id());
        try {
            if (pedido.getPed_data() != null) {
                SimpleDateFormat f = new SimpleDateFormat("yyyy");
                int a = Integer.parseInt(f.format(pedido.getPed_data()));
                ano = a;
            }
        } catch (Exception e) {
        }
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

    public String salvar() {
        if (pedido.getPed_id() > 0) {
            dao.update(pedido);
        } else {
            pedido.setPed_data(new Date());
            dao.insert(pedido);
        }
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

    public List<Integer> getAnos() {
        if (anos == null) {
            anos = new ArrayList<>();
        }
        if (anos.isEmpty()) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy");
            int a = Integer.parseInt(f.format(new Date()));
            anos.add(a - 1);
            anos.add(a);
            anos.add(a + 1);
        }
        return anos;
    }

    public void setAnos(List<Integer> anos) {
        this.anos = anos;
    }

    public int getAno() {
        if (ano == 0) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy");
            ano = Integer.parseInt(f.format(new Date()));
        }
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

}
