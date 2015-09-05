package set.bean;

import set.dao.UsuarioDAO;
import set.model.Usuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

@ManagedBean
@SessionScoped
public class LoginBean {

    private Usuario usuario;
    private String user;
    private UsuarioDAO dao = new UsuarioDAO();

    private Part file;

    public LoginBean() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUsuarioByLogin(String usu_login) {
        this.usuario = dao.findByLogin(usu_login);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void save() {
        dao.update(usuario);
    }

//    public String getImageUrl(int rep_codigo) {
//        return "/PraticaII/DirectServlet?method=image&id=" + rep_codigo;
//    }

}
