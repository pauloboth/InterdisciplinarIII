package bean;

import dao.CategoriaDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Categoria;

@ManagedBean
public class CategoriaBean {

    private Categoria categoria = new Categoria();
    private CategoriaDAO dao = new CategoriaDAO();
    private DataModel categorias;

    public CategoriaBean() {
    }

    public DataModel getCategorias() {
        this.categorias = new ListDataModel(dao.findAll());
        return categorias;
    }

    public void setCategorias(DataModel i) {
        this.categorias = i;
    }

    public String insert() {
        dao.insert(categoria);
        return "categorialst";
    }

    public String edit(Categoria i) {
        categoria = (Categoria) categorias.getRowData();
        return "categoriafrm";
    }

    public String delete(Categoria i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        return "categorialst";
    }

    public String salvar() {
        if (categoria.getCat_id() > 0) {
            dao.update(categoria);
        } else {
            dao.insert(categoria);
        }
        return "categorialst";
    }

    public String listar() {
        return "categorialst";
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
