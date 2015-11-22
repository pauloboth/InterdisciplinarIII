package service;

import dao.CustoDAO;
import dao.ProducaoDAO;
import dao.ProdutoDAO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Custo;
import model.CustoDespesa;
import model.Produto;
import model.ProdutoProducao;

@Path("/Custos")
public class CustoResource {

    public CustoResource() {
    }

    @GET
//    @Path("Custos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Produto> SelectAllCustos() {

        ProdutoDAO dao = new ProdutoDAO();
        ProducaoDAO pedDAO = new ProducaoDAO();
        CustoDAO cusDAO = new CustoDAO();
        List<Produto> lsP = dao.findAll(0);
        if (lsP != null && !lsP.isEmpty()) {
            for (Produto p : lsP) {
                p.setCategoria(null);
                p.setLsProdutoDespesa(null);
                p.setLsProdutoMaquina(null);
                p.setLsProdutoProducao(null);
                double valor = 0;
                int producao_total_geral = 0;

                List<Custo> lsCustos = cusDAO.CustoAnual(p.getPro_id());
                List<ProdutoProducao> lsPp = pedDAO.totalProducao(p.getPro_id());
                for (ProdutoProducao pp : lsPp) {
                    producao_total_geral += pp.getPrp_quantidade();
                }
                for (Custo c : lsCustos) {
                    for (CustoDespesa cd : c.getLsCustoDespesa()) {
                        valor += cd.getCsd_valor();
                    }
                }
                valor += producao_total_geral * p.getPro_preco();
                valor = valor / producao_total_geral;
                valor = Double.parseDouble(Math.round(valor * 1000) + "") / 1000;
                p.setPro_preco(valor);
            }
        }
        return lsP;
    }
}
