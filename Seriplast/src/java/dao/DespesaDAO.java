package dao;

import java.util.Date;
import java.util.List;
import model.Despesa;
import model.DespesaMes;
import org.hibernate.Query;
import org.hibernate.Session;
import util.HibernateUtil;

public class DespesaDAO {

    private Session session;

    public DespesaDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void save(Despesa i) {
        if (i.getDes_tipo() != 1) {
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            if (i.getDes_id() > 0) {
                session.update(i);
            } else {
                i.setDes_date(new Date());
                session.save(i);
            }
            session.getTransaction().commit();
            session.close();
        }
    }

    public void delete(Despesa i) {
        if (i.getDes_tipo() != 1) {
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            session.delete(i);
            session.getTransaction().commit();
            session.close();
        }
    }

    public Despesa findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Despesa m = (Despesa) session.get(Despesa.class, id);
        session.close();

        return m;
    }

    public List<Despesa> findAll(int status) {
        session = HibernateUtil.getSessionFactory().openSession();
        String sql = "";
        if (status > 0) {
            sql += " and des_status = " + status;
        }
        List<Despesa> ls = session.createQuery("from Despesa where 1 = 1 " + sql).list();
        session.close();
        return ls;
    }

    public Despesa findEdit(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        // Query para retornar o Despesa e a lista de produtos (fetch)
        Despesa d = (Despesa) session.createQuery("select d from Despesa d "
                + "left outer join fetch d.lsProdutoDespesa pd where d.des_id = :d")
                .setParameter("d", id).uniqueResult();

        session.close();
        return d;
    }

    public List<Despesa> searchDespesasMes(int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Despesa> ls = session.createQuery("select d from Despesa d where des_status = 1 "
                + "and (des_tipo = 2 or des_tipo = 1 "
                + "or (des_tipo = 4 and month(des_date) = :m and year(des_date) = :a))")
                .setParameter("m", mes).setParameter("a", ano).list();
        session.close();
        return ls;
    }

    public DespesaMes lastMonth(int id_des) {
        session = HibernateUtil.getSessionFactory().openSession();
        DespesaMes dm = new DespesaMes();
        Query q = session.createQuery("select dm from DespesaMes dm "
                + "left outer join fetch dm.despesa d "
                + "where d.des_id = :d");
        q.setParameter("d", id_des);
        List<DespesaMes> ls = q.list();
        session.close();
        if (!ls.isEmpty()) {
            dm = ls.get(0);
        }
        return dm;
    }
    
    
}
