package dao;

import java.util.Date;
import java.util.List;
import model.Despesa;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

    public void insert(Despesa i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(i);
        session.getTransaction().commit();
        session.close();
    }

    public void update(Despesa i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(i);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Despesa i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(i);
        session.getTransaction().commit();
        session.close();
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

    public List<Despesa> searchDespesasMes(Date d) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Despesa> ls = session.createQuery("select d from Despesa d where des_status = 1").list();
        session.close();
        return ls;
    }
//        List<Despesa> ls = session.createQuery("select d from Despesa d "
//                + "where (des_tipo = 2 or (des_inicio_depr + des_depr_mes) >= :date) "
//                + "and des_status = 1")
//                .setParameter("date", d).list();
}
