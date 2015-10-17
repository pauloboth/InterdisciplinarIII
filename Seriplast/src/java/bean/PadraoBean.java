package bean;

import java.util.Date;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class PadraoBean {

    private TimeZone timeZone = TimeZone.getDefault();

    public PadraoBean() {
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String mesToString(Date d) {
        if (d != null) {
            String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
            return meses[d.getMonth()];
        }
        return "";
    }
}
