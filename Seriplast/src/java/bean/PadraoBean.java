package bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public List<Integer> Anos(int size) {
        List<Integer> lsI = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(sdf.format(new Date()));
        for (int i = size; i > 0; i--) {
            lsI.add(year - i);
        }
        lsI.add(year);
        for (int i = 1; i <= size; i++) {
            lsI.add(year + i);
        }
        return lsI;
    }

    public List<Integer> Meses(int size) {
        List<Integer> lsI = new ArrayList<>();
        Date d = new Date();
        int m = d.getMonth() + 1;
        if (size == 12) {
            for (int i = 1; i < 13; i++) {
                lsI.add(i);
            }
        } else {
            for (int i = size; i > 0; i--) {
                int m1 = m - i;
                if (m1 <= 0) {
                    m1 = 12 + m1;
                }
                lsI.add(m1);
            }
            lsI.add(m);
            for (int i = 1; i <= size; i++) {
                int m1 = m + i;
                if (m1 > 12) {
                    m1 = m1 - 12;
                }
                lsI.add(m1);
            }
        }
        return lsI;
    }

    public String mesString(int i) {
        if (i > 0 && i < 13) {
            String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
            return meses[i - 1];
        }
        return "";
    }

    public Date newDate(int d, int m, int a) {
        Date date = new Date(a, m, d);
        return date;
    }
}
