package dao;

import model.Maquina;
import java.util.List;
import org.hibernate.Session;
import util.HibernateUtil;

public class MaquinaDAO {

    private Session session;

    public MaquinaDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void save(Maquina i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        if (i.getMaq_id() > 0) {
            session.update(i);
        } else {
            session.save(i);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Maquina i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(i);
        session.getTransaction().commit();
        session.close();
    }

    public Maquina findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Maquina m = (Maquina) session.get(Maquina.class, id);
        session.close();
        return m;
    }

    public List<Maquina> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Maquina> ls = session.createQuery("from Maquina").list();
        session.close();
        return ls;
    }

    public Maquina findEdit(int maq_id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Maquina m = (Maquina) session.createQuery("select m from Maquina m "
                + "left outer join fetch m.lsProdutoMaquina pm "
                + "where m.maq_id = :maq_id")
                .setParameter("maq_id", maq_id).uniqueResult();

        session.close();
        return m;
    }
}
