package dao;

import model.Produto;
import java.util.List;
import model.ProdutoDespesa;
import model.ProdutoMaquina;
import org.hibernate.Session;
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

    public void save(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        if (i.getPro_id() > 0) {
            session.update(i);
        } else {
            session.save(i);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(i);
        session.getTransaction().commit();
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
        Produto p = (Produto) session.createQuery("from Produto where pro_id = :p").setParameter("p", id).uniqueResult();
//        List<ProdutoMaquina> lsPm = session.createQuery("from ProdutoMaquina "
//                + "where produto.pro_id = :p").setParameter("p", id).list();
//        p.setLsProdutoMaquina(lsPm);
//        List<ProdutoDespesa> lsPd = session.createQuery("from ProdutoDespesa "
//                + "where produto.pro_id = :p").setParameter("p", id).list();
//        p.setLsProdutoDespesa(lsPd);
//        p.setLsProdutoProducao(null);
        session.close();
        return p;
    }
}
