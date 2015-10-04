package dao;

import model.Categoria;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class CategoriaDAO {

    private Session session;

    public CategoriaDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Categoria i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        session.close();
    }

    public void update(Categoria i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        session.close();
    }

    public void delete(Categoria i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        session.close();
    }

    public Categoria findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Categoria m = (Categoria) session.get(Categoria.class, id);
        session.close();
        return m;
    }

    public List<Categoria> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Categoria> ls = session.createQuery("from Categoria").list();
        session.close();
        return ls;
    }

}
