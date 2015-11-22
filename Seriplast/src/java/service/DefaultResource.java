package service;

import dao.CategoriaDAO;
import dao.DespesaDAO;
import dao.MaquinaDAO;
import dao.ProdutoDAO;
import dao.UsuarioDAO;
import java.util.List;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Categoria;
import model.Despesa;
import model.Maquina;
import model.Produto;
import model.Usuario;

@Path("/Default")
public class DefaultResource {

    public DefaultResource() {
    }

    @GET
    @Path("/Categorias")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Categoria> GetCategorias() {
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> lsCat = dao.findAll();
        if (lsCat != null && !lsCat.isEmpty()) {
            for (Categoria c : lsCat) {
                c.setLsProduto(null);
            }
        }
        return lsCat;
    }

    @GET
    @Path("/Maquinas")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Maquina> GetMaquinas() {
        MaquinaDAO dao = new MaquinaDAO();
        List<Maquina> lsMaq = dao.findAll();
        if (lsMaq != null && !lsMaq.isEmpty()) {
            for (Maquina m : lsMaq) {
                m.setLsProdutoMaquina(null);
            }
        }
        return lsMaq;
    }

    @GET
    @Path("/Despesas")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Despesa> GetDespesas() {
        DespesaDAO dao = new DespesaDAO();
        List<Despesa> lsDes = dao.findAll();
        if (lsDes != null && !lsDes.isEmpty()) {
            for (Despesa d : lsDes) {
                d.setLsCustoDespesa(null);
                d.setLsProdutoDespesa(null);
            }
        }
        return lsDes;
    }

    @GET
    @Path("/Usuarios")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Usuario> GetUsuarios() {
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> lsUsu = dao.findAll();
        if (lsUsu != null && !lsUsu.isEmpty()) {
            for (Usuario u : lsUsu) {
                u.setGrupo(null);
            }
        }
        return lsUsu;
    }

    @GET
    @Path("/Produtos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Produto> GetProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> lsPro = dao.findAll(0);
        if (lsPro != null && !lsPro.isEmpty()) {
            for (Produto p : lsPro) {
                p.setCategoria(null);
                p.setLsProdutoDespesa(null);
                p.setLsProdutoMaquina(null);
                p.setLsProdutoProducao(null);
            }
        }
        return lsPro;
    }

}
