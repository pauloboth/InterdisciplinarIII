package dao;

import java.util.Date;
import model.Producao;
import java.util.List;
import model.ProdutoProducao;
import org.hibernate.Session;
import util.HibernateUtil;

public class ProducaoDAO {

    private Session session;

    public ProducaoDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void save(Producao i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        if (i.getPrd_id() > 0) {
            session.update(i);
        } else {
            i.setPrd_data(new Date());
            session.save(i);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Producao i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(i);
        session.getTransaction().commit();
        session.close();
    }

    public Producao findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Producao m = (Producao) session.get(Producao.class, id);
        session.close();
        return m;
    }

    public List<Producao> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Producao> ls = session.createQuery("from Producao").list();
        session.close();
        return ls;
    }

    public List<Producao> findMes(int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "";
        if (mes > 0 && mes < 13 && ano > 0) {
            sql += " and month(prd_data_ref) = " + mes + " and year(prd_data_ref) = " + ano + "";
        }
        List<Producao> ls = session.createQuery("from Producao where 1 = 1 " + sql).list();
        session.close();
        return ls;
    }

    public Producao findEdit(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Producao p = (Producao) session.createQuery("from Producao where prd_id = :p")
                .setParameter("p", id).uniqueResult();
//        List<ProdutoProducao> lsPp = session.createQuery("from ProdutoProducao where producao.prd_id = :p")
//                .setParameter("p", id).list();
//        p.setLsProdutoProducao(lsPp);
        session.close();
        return p;
    }

    public List<ProdutoProducao> totalProducaoMes(int pro_id, int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<ProdutoProducao> lsPp = session.createQuery("select pp from ProdutoProducao pp "
                + "join pp.producao prd "
                + "where pp.produto.pro_id = :p "
                + "and month(prd.prd_data_ref) = :mes and year(prd.prd_data_ref) = :ano")
                .setParameter("p", pro_id).setParameter("mes", mes).setParameter("ano", ano).list();
        session.close();
        return lsPp;
    }

    public List<ProdutoProducao> totalProducaoAno(int pro_id, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<ProdutoProducao> lsPp = session.createQuery("select pp from ProdutoProducao pp "
                + "join pp.producao prd "
                + "where pp.produto.pro_id = :p "
                + "and year(prd.prd_data_ref) = :ano")
                .setParameter("p", pro_id).setParameter("ano", ano).list();
        session.close();
        return lsPp;
    }

    public List<ProdutoProducao> totalProducao(int pro_id) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<ProdutoProducao> lsPp = session.createQuery("select pp from ProdutoProducao pp "
                + "join pp.producao prd "
                + "where pp.produto.pro_id = :p "
        )
                .setParameter("p", pro_id).list();
        session.close();
        return lsPp;
    }
}
