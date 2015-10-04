package dao;

import java.util.List;
import model.Despesa;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class DespesaDAO {

    private Session session;

    public DespesaDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Despesa i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        session.close();
    }

    public void update(Despesa i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        session.close();
    }

    public void delete(Despesa i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        session.close();
    }

    public Despesa findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Despesa m = (Despesa) session.get(Despesa.class, id);
        session.close();
        return m;
    }

    public List<Despesa> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Despesa> ls = session.createQuery("from Despesa").list();
        session.close();
        return ls;
    }

}
