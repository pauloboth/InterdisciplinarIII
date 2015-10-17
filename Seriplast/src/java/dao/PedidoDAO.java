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
        session.getTransaction().begin();
        session.save(i);
        session.getTransaction().commit();
        session.close();
    }

    public void update(Pedido i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(i);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Pedido i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(i);
        session.getTransaction().commit();
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

    public List<Pedido> findMes(int m) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "";
        if (m > 0 && m < 13) {
            sql += " and ped_mes =" + m;
        }
        List<Pedido> ls = session.createQuery("from Pedido where 1 = 1 " + sql).list();
        session.close();
        return ls;
    }

    public Pedido findEdit(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Pedido p = (Pedido) session.createQuery("select p from Pedido p "
                + "left outer join fetch p.lsProdutoPedido pm "
                + "where p.ped_id = :p")
                .setParameter("p", id).uniqueResult();
        session.close();
        return p;
    }

}
