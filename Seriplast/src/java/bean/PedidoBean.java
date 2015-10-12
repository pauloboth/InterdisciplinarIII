package bean;

import dao.PedidoDAO;
import javax.faces.bean.ManagedBean;
import model.Pedido;

@ManagedBean
public class PedidoBean {

    private Pedido pedido = new Pedido();
    private PedidoDAO dao = new PedidoDAO();

    public PedidoBean() {
    }

}
