package bean;

import dao.CategoriaDAO;
import dao.DespesaDAO;
import java.util.List;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.Categoria;
import model.Despesa;

@ManagedBean
public class DespesaBean {

    private Despesa despesa = new Despesa();
    private DespesaDAO dao = new DespesaDAO();
    private CategoriaDAO catDAO = new CategoriaDAO();
    private List<Categoria> lsCategorias;
    private DataModel despesas;
    private boolean isMachin;
    private TimeZone timeZone = TimeZone.getDefault();

    public DespesaBean() {
    }

    public DataModel getDespesas() {
        this.despesas = new ListDataModel(dao.findAll());
        return despesas;
    }

    public void setDespesas(DataModel i) {
        this.despesas = i;
    }

    public String edit(Despesa i) {
        despesa = (Despesa) despesas.getRowData();
        return "despesafrm";
    }

    public String delete(Despesa i) {
        try {
            dao.delete(i);
        } catch (Exception e) {
        }
        return "despesalst";
    }

    public String salvar() {
        if (despesa.getDes_id() > 0) {
            dao.update(despesa);
        } else {
            dao.insert(despesa);
        }
        return "despesalst";
    }

    public String listar() {
        return "despesalst";
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public void setDespesa(Despesa despesa) {
        this.despesa = despesa;
    }

    public List<Categoria> getLsCategorias() {
        lsCategorias = catDAO.findAll();
        return lsCategorias;
    }

    public void setLsCategorias(List<Categoria> lsCategorias) {
        this.lsCategorias = lsCategorias;
    }

    public boolean isIsMachin() {
        return isMachin;
    }

    public void setIsMachin(boolean isMachin) {
        this.isMachin = isMachin;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

}
