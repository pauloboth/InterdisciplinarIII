package dao;

import model.Pedido;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class PedidoDAO {

    private Session session;

    public PedidoDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Pedido i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        session.close();
    }

    public void update(Pedido i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        session.close();
    }

    public void delete(Pedido i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        session.close();
    }

    public Pedido findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Pedido m = (Pedido) session.get(Pedido.class, id);
        session.close();
        return m;
    }

    public List<Pedido> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Pedido> ls = session.createQuery("from Pedido").list();
        session.close();
        return ls;
    }
    
     public Pedido findEdit(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Pedido p = (Pedido) session.createQuery("select p from Pedido p "
                + "left outer join fetch p.lsProdutoPedido pm "
                + "where ped_id = :m")
                .setParameter("m", id).uniqueResult();

        session.close();
        return p;
    }

}
