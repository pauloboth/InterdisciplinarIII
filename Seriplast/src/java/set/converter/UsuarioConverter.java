package set.converter;

import set.dao.UsuarioDAO;
import set.model.Usuario;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

@FacesConverter("usuarioConverter")
public class UsuarioConverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) {
        return new UsuarioDAO().findByLogin(string);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) {
        return "" + ((Usuario) object).getUsuLogin();
    }
}
