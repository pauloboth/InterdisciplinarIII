package dao;

import java.util.List;
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

    public Despesa findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Despesa m = (Despesa) session.get(DespesaMes.class, id);
        session.close();

        return m;
    }

    public List<DespesaMes> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<DespesaMes> ls = session.createQuery("from DespesaMes").list();
        session.close();
        return ls;
    }

}
