package service;

import dao.CustoDAO;
import dao.ProducaoDAO;
import dao.ProdutoDAO;
import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Custo;
import model.CustoDespesa;
import model.Produto;
import model.ProdutoProducao;

@Path("/Custo")
public class ProdutoResource {

    ProdutoDAO dao = new ProdutoDAO();
    ProducaoDAO pedDAO = new ProducaoDAO();
    CustoDAO cusDAO = new CustoDAO();

    public ProdutoResource() {
    }

    @GET
    //@Path("SelectAll")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Produto> SelectAll() {
        List<Produto> lsP = dao.findAll(0);
        if (lsP != null && !lsP.isEmpty()) {
            for (Produto p : lsP) {
                p.setCategoria(null);
                p.setLsProdutoDespesa(null);
                p.setLsProdutoMaquina(null);
                p.setLsProdutoProducao(null);
            }
        }
//       double valor_total_geral = 0;
//        List<Custo> lsCustos2 = cusDAO.CustoAnual(p.getPro_id());
//        for (Custo c : lsCustos2) {
//
//            for (CustoDespesa cd : c.getLsCustoDespesa()) {
//                List<ProdutoProducao> lsPp = pedDAO.totalProducao(p.getPro_id());
//                for (ProdutoProducao pp : lsPp) {
//                    //passa a lista de produção (saber quantas unidades)
//                    producao_total_geral += pp.getPrp_quantidade();
//                }
//                valor_total_geral += cd.getCsd_valor();
//            }
//            valor_total_geral += producao_total_geral * p.getPro_preco();
//            valor_total_geral = valor_total_geral / producao_total_geral;
//        }
        return lsP;
    }
}
