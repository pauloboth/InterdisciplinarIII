package dao;

import java.util.Calendar;
import java.util.Date;
import model.Custo;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sun.util.BuddhistCalendar;
import util.HibernateUtil;

public class CustoDAO {

    private Session session;

    public CustoDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Custo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(i);
        t.commit();
        session.close();
    }

    public void update(Custo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(i);
        t.commit();
        session.close();
    }

    public void delete(Custo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(i);
        t.commit();
        session.close();
    }

    public Custo findById(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Custo m = (Custo) session.get(Custo.class, id);
        session.close();
        return m;
    }

    public List<Custo> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Custo> ls = session.createQuery("from Custo").list();
        session.close();
        return ls;
    }

    public Custo findEditInDate(int id) {
        session = HibernateUtil.getSessionFactory().openSession();
        Custo c = (Custo) session.createQuery("select c from Custo c "
                + "where c.pro_id = :p "
                + "and month(c.cus_cadastro) = month(:date) "
                + "and year(c.cus_cadastro) = year(:date) "
                + "order by c.cus_cadastro desc")
                .setParameter("p", id)
                .setParameter("date", new Date())
                .uniqueResult();
        session.close();
        return c;
    }

}
