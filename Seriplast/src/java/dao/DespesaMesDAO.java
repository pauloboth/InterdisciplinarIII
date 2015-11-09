package dao;

import java.util.List;
import model.Custo;
import model.CustoDespesa;
import model.Despesa;
import model.DespesaMes;
import org.hibernate.Session;
import util.HibernateUtil;

public class DespesaMesDAO {

    private Session session;

    public DespesaMesDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void save(DespesaMes i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        if (i.getDsm_id() > 0) {
            session.update(i);
        } else {
            session.save(i);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void delete(DespesaMes i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(i);
        session.getTransaction().commit();
        session.close();
    }

    public DespesaMes findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        DespesaMes m = (DespesaMes) session.get(DespesaMes.class, id);
        session.close();
        return m;
    }

    public List<DespesaMes> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<DespesaMes> ls = session.createQuery("from DespesaMes").list();
        session.close();
        return ls;
    }

    public DespesaMes findDespesaMes(int des_id, int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "";
        if (des_id > 0) {
            sql += " and dm.despesa.des_id = " + des_id;
        }
        DespesaMes dm = (DespesaMes) session.createQuery("select dm from DespesaMes dm "
                + "where month(dm.dsm_data_ref) = :mes and year(dm.dsm_data_ref) = :ano "
                + sql)
                .setParameter("mes", mes)
                .setParameter("ano", ano).uniqueResult();
        session.close();
        return dm;
    }

    public DespesaMes findUnicaDespesa(int des_id) {
        session = HibernateUtil.getSessionFactory().openSession();
        DespesaMes m = (DespesaMes) session.createQuery("select dm from DespesaMes dm "
                + "join dm.despesa d "
                + "where d.des_id = :des_id")
                .setParameter("des_id", des_id).uniqueResult();
        session.close();
        return m;
    }
}
