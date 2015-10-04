package bean;

import dao.CategoriaDAO;
import dao.ProdutoDAO;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Categoria;
import model.Produto;

@ManagedBean
public class ProdutoBean {

    private Produto produto = new Produto();
    private ProdutoDAO dao = new ProdutoDAO();
    private CategoriaDAO catDAO = new CategoriaDAO();
    private List<Categoria> lsCategorias;
    private DataModel produtos;

    public ProdutoBean() {
    }

    public DataModel getProdutos() {
        this.produtos = new ListDataModel(dao.findAll());
        return produtos;
    }

    public void setProdutos(DataModel i) {
        this.produtos = i;
    }

    public String edit(Produto i) {
        produto = (Produto) produtos.getRowData();
        return "produtofrm";
    }

    public String delete(Produto i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        return "produtolst";
    }

    public String salvar() {
        if (produto.getPro_id() > 0) {
            dao.update(produto);
        } else {
            dao.insert(produto);
        }
        return "produtolst";
    }

    public String listar() {
        return "produtolst";
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public List<Categoria> getLsCategorias() {
        lsCategorias = catDAO.findAll();
        return lsCategorias;
    }

    public void setLsCategorias(List<Categoria> lsCategorias) {
        this.lsCategorias = lsCategorias;
    }

}
