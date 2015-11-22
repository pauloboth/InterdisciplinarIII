package dao;

import model.Maquina;
import java.util.List;
import model.ProdutoMaquina;
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

    public Maquina findEdit(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Maquina m = (Maquina) session.createQuery("from Maquina where maq_id = :m").setParameter("m", id).uniqueResult();
//        List<ProdutoMaquina> lsPm = session.createQuery("from ProdutoMaquina "
//                + "where maquina.maq_id = :m").setParameter("m", id).list();
//        m.setLsProdutoMaquina(lsPm);
        session.close();
        return m;
    }
}
