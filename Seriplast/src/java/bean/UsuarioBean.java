package bean;

import dao.GrupoDAO;
import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Grupo;

@ManagedBean
public class UsuarioBean {

    private Usuario usuario = new Usuario();
    private UsuarioDAO dao = new UsuarioDAO();
    private DataModel usuarios;
    private List<Grupo> lsGrupo = new ArrayList<>();
    private GrupoDAO gruDAO = new GrupoDAO();

    public UsuarioBean() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DataModel getUsuarios() {
        this.usuarios = new ListDataModel(dao.findAll());
        return usuarios;
    }

    public void setUsuario(DataModel usuarios) {
        this.usuarios = usuarios;
    }

    public String edit(Usuario i) {
        usuario = (Usuario) usuarios.getRowData();
        return "usuariofrm";
    }

    public String delete(Usuario i) {
        dao.delete(i);
        return "usuariolst";
    }

    public String salvar() {
        dao.save(usuario);
        return "usuariolst";
    }

    public String listar() {
        return "usuariolst";
    }

    public List<Grupo> getLsGrupo() {
        lsGrupo = gruDAO.findAll();
        return lsGrupo;
    }

}
