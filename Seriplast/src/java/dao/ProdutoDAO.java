package dao;

import java.util.ArrayList;
import model.Produto;
import java.util.List;
import model.ProdutoPedido;
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

    public void insert(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(i);
        session.getTransaction().commit();
        session.close();
    }

    public void update(Produto i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(i);
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
        Produto p = (Produto) session.createQuery("select p from Produto p "
                + "left outer join fetch p.lsProdutoDespesa pd "
                + "where p.pro_id = :p")
                .setParameter("p", id).uniqueResult();
        Produto p2 = (Produto) session.createQuery("select p from Produto p "
                + "left outer join fetch p.lsProdutoMaquina pm "
                + "where p.pro_id = :p")
                .setParameter("p", id).uniqueResult();
//        Produto p3 = (Produto) session.createQuery("select p from Produto p "
//                + "left outer join fetch p.lsProdutoPedido pp "
//                + "where p.pro_id = :p")
//                .setParameter("p", id).uniqueResult();
        p.setLsProdutoMaquina(p2.getLsProdutoMaquina());
//        p.setLsProdutoPedido(p3.getLsProdutoPedido());
        session.close();
        return p;
    }

    public List<Produto> produtosCusto(int pro_id, int cus_id, int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Produto> ls = new ArrayList<>();
        if (pro_id != 0 && cus_id == 0) {
            ls = session.createQuery("select p from Produto p where p.pro_id = :p and p.pro_status = 1").setParameter("p", pro_id).list();
        } else if (pro_id == 0 && cus_id != 0) {
            ls = session.createQuery("select p from Produto p inner join CustoDespesa cd on cd.pro_id = p.pro_id where cd.cus_id = :c and p.pro_status = 1").setParameter("c", cus_id).list();
        } else if (pro_id != 0 && cus_id != 0) {
            ls = session.createQuery("select p from Produto p inner join CustoDespesa cd on cd.pro_id = p.pro_id where cd.cus_id = :c and p.pro_id = :p and p.pro_status = 1")
                    .setParameter("p", pro_id).setParameter("c", cus_id).list();
        } else {
            ls = session.createQuery("select p from Produto p p.pro_status = 1").list();
        }
        if (ls != null && ls.size() > 0) {
            for (Produto p : ls) {
                {
                    List<ProdutoPedido> lsPp = session.createQuery("select pp from ProdutoPedido pp inner join Pedido p"
                            + " on  pp.ped_id = p.ped_id"
                            + " where  pp.pro_id = :p"
                            + " and month(p.ped_data_ref) = :mes and year(p.ped_data_ref) = :ano ")
                            .setParameter("p", p.getPro_id()).setParameter("mes", mes).setParameter("ano", ano).list();
                    p.setLsProdutoPedido(lsPp);
                }
            }
        }
        session.close();
        return ls;
    }
}
