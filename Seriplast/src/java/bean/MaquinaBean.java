package bean;

import dao.MaquinaDAO;
import javax.faces.bean.ManagedBean;
import model.Maquina;

@ManagedBean
public class MaquinaBean {

    private Maquina maquina = new Maquina();
    private MaquinaDAO dao = new MaquinaDAO();

    public MaquinaBean() {
    }

}
