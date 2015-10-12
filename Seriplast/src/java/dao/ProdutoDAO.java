package dao;

import model.Produto;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class ProdutoDAO {

    private Session session;

    public ProdutoDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        session.close();
    }

    public void update(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.update(i);
        t.commit();
        session.close();
    }

    public void delete(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        session.close();
    }

    public Produto findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Produto m = (Produto) session.get(Produto.class, id);
        session.close();
        return m;
    }

    public List<Produto> findAll(int status) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "";
        if (status != 0) {
            sql += " and pro_status = " + status;
        }
        List<Produto> ls = session.createQuery("from Produto where 1 = 1 " + sql).list();
        session.close();
        return ls;
    }

    public Produto findEdit(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Produto p = (Produto) session.createQuery("select p from Produto p "
                + "left outer join fetch p.lsProdutoDespesa pd "
                + "where p.pro_id = :p")
                .setParameter("p", id).uniqueResult();
        Produto p2 = (Produto) session.createQuery("select p from Produto p "
                + "left outer join fetch p.lsProdutoMaquina pm "
                + "where p.pro_id = :p")
                .setParameter("p", id).uniqueResult();
        Produto p3 = (Produto) session.createQuery("select p from Produto p "
                + "left outer join fetch p.lsProdutoPedido pp "
                + "where p.pro_id = :p")
                .setParameter("p", id).uniqueResult();
        p.setLsProdutoMaquina(p2.getLsProdutoMaquina());
        p.setLsProdutoPedido(p3.getLsProdutoPedido());
        session.close();
        return p;
    }

}
