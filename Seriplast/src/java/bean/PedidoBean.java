package bean;

import dao.PedidoDAO;
import dao.ProdutoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Pedido;
import model.Produto;
import model.ProdutoPedido;
import util.HibernateUtil;

@ManagedBean
public class PedidoBean {

    private Pedido pedido = new Pedido();
    private PedidoDAO dao = new PedidoDAO();
    private DataModel pedidos;
    private List<Pedido> lsPedidos = new ArrayList<>();
    private List<Pedido> lsPedidoAll = new ArrayList<>();
    
    //produto
    private List<Produto> lsProdutos = new ArrayList<>();
    private List<Produto> lsProdutosAll = new ArrayList<>();
    private ProdutoDAO proDAO = new ProdutoDAO();
    private Produto produto = new Produto();
    

    public PedidoBean() {
    }
     public void clearSession() {
        pedido = new Pedido();
        pedido.setLsProdutoPedido(new ArrayList<ProdutoPedido>());
        produto = new Produto();
    }
    
     public DataModel getPedidos() {
       // clearSession();
        this.pedidos = new ListDataModel(dao.findAll());
        return pedidos;
    }

    public void setPedidos(DataModel i) {
        this.pedidos = i;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoDAO getDao() {
        return dao;
    }

    public void setDao(PedidoDAO dao) {
        this.dao = dao;
    }

    public List<Pedido> getLsPedidos() {
        return lsPedidos;
    }

    public void setLsPedidos(List<Pedido> lsPedidos) {
        this.lsPedidos = lsPedidos;
    }
    
    //
    public String edit(Pedido i) {
        pedido = dao.findById(i.getPed_id());
        return "pedidofrm";
         }
    
  

    public String delete(Pedido i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        return "pedidolst";
    }

    public String salvar() {
        if (pedido.getPed_id() > 0) {
            dao.update(pedido);
        } else {
            dao.insert(pedido);
        }
        clearSession();
        return "pedidolst";
    }

    public String listar() {
        return "pedidolst";
    }
    
  public List<Produto> getLsProdutos() {
        lsProdutos = proDAO.findAll(1);
        lsProdutosAll = lsProdutos;
        reloadProdutos();
        return lsProdutos;
    }

    public void removeProdutoPedido(ProdutoPedido pp) {
        pedido.getLsProdutoPedido().remove(pp);
        reloadProdutos();
    }

    public void addProdutoPedido() {
        if (pedido != null) {
            if (pedido.getLsProdutoPedido()== null) {
                pedido.setLsProdutoPedido(new ArrayList<ProdutoPedido>());
            }
            boolean bAdd = true;
            for (ProdutoPedido pdp : pedido.getLsProdutoPedido()) {
                if (pdp.getProduto().getPro_id() == produto.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                ProdutoPedido pd = new ProdutoPedido();
                pd.setPedido(pedido);
                pd.setProduto(produto);
                pedido.getLsProdutoPedido().add(pd);
                produto = new Produto();
            }
            reloadProdutos();
        }
    }
    
      private void reloadProdutos() {
        lsProdutos = new ArrayList<>();
        for (Produto p : lsProdutosAll) {
            boolean bAdd = true;
            for (ProdutoPedido pds : pedido.getLsProdutoPedido()) {
                if (pds.getProduto().getPro_id() == p.getPro_id()) {
                    bAdd = false;
                }
            }
            if (bAdd) {
                lsProdutos.add(p);
            }
        }
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    

}
