package dao;

import model.Grupo;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;

public class GrupoDAO {

    private Session session;

    public GrupoDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void save(Grupo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        if (i.getGro_id() > 0) {
            session.update(i);
        } else {
            session.save(i);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Grupo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(i);
        session.getTransaction().commit();
        session.close();
    }

    public Grupo findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Grupo m = (Grupo) session.get(Grupo.class, id);
        session.close();
        return m;
    }

    public List<Grupo> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Grupo> ls = session.createQuery("from Grupo").list();
        session.close();
        return ls;
    }

}
