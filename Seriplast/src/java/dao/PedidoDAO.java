package dao;

import java.util.Date;
import model.Pedido;
import java.util.List;
import model.ProdutoPedido;
import org.hibernate.Session;
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

    public void save(Pedido i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        if (i.getPed_id() > 0) {
            session.update(i);
        } else {
            i.setPed_data(new Date());
            session.save(i);
        }
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

    public List<Pedido> findMes(int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "";
        if (mes > 0 && mes < 13 && ano > 0) {
            sql += " and month(ped_data_ref) = " + mes + " and year(ped_data_ref) = " + ano + "";
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

    public List<ProdutoPedido> totalPedidosMes(int pro_id, int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<ProdutoPedido> lsPp = session.createQuery("select pp from ProdutoPedido pp "
                + "join pp.pedido ped "
                + "where pp.produto.pro_id = :p "
                + "and month(ped.ped_data_ref) = :mes and year(ped.ped_data_ref) = :ano")
                .setParameter("p", pro_id).setParameter("mes", mes).setParameter("ano", ano).list();
        session.close();
        return lsPp;
    }
    
      public List<ProdutoPedido> totalPedidosAno(int pro_id, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<ProdutoPedido> lsPp = session.createQuery("select pp from ProdutoPedido pp "
                + "join pp.pedido ped "
                + "where pp.produto.pro_id = :p "
                + "and year(ped.ped_data_ref) = :ano")
                .setParameter("p", pro_id).setParameter("ano", ano).list();
        session.close();
        return lsPp;
    }
}
