
package set.bean;

import set.dao.UsuarioDAO;
import set.model.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean
@RequestScoped
public class UsuarioBean {
    
    private final String sTitle = Usuario.sTitle;
    private final String pTitle = Usuario.pTitle;
    
    private Usuario usuario = new Usuario();
    private UsuarioDAO dao = new UsuarioDAO();
    private DataModel usuarios;
    
    public UsuarioBean() {
    }

    public String getsTitle() {
        return sTitle;
    }

    public String getpTitle() {
        return pTitle;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario= usuario;
    }

    public DataModel getUsuarios() {
        this.usuarios = new ListDataModel(dao.findAll());
        return usuarios;
    }

    public void setUsuario(DataModel usuarios) {
        this.usuarios = usuarios;
    }
    
    public String insert() {
        dao.insert(usuario);
        return "usuariolst";
    }
    
    public String edit(Usuario i) {
        usuario = (Usuario) usuarios.getRowData();
        return "usuariofrm";
    }
    
    public String update() {
        dao.update(usuario);
        return "usuariolst";
    }
    
    public String delete(Usuario i) {
        dao.delete(i);
        return "usuariolst";
    }
    
    public String salvar() {
        //Merge serve para insert também
            //dao.update(usuario);
//        else 
            dao.insert(usuario);
        
        return "usuariolst";
    }
    
    public String listar() {
        return "usuariolst";
    }
    
}
