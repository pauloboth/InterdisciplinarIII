package dao;

import java.util.Date;
import model.Custo;
import java.util.List;
import model.CustoDespesa;
import model.ProdutoCusto;
import model.ProdutoDespesa;
import org.hibernate.Session;
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
        session.getTransaction().begin();
        session.save(i);
        session.getTransaction().commit();
        session.close();
    }

    public void update(Custo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.update(i);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(Custo i) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.delete(i);
        session.getTransaction().commit();
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

    public void saveList(List<ProdutoCusto> ls) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        if (ls != null && !ls.isEmpty()) {
            for (ProdutoCusto pc : ls) {
                if (pc.getCusto().getCus_id() > 0) {
                    session.update(pc.getCusto());
                } else {
                    session.save(pc.getCusto());
                }
                if (pc.getDespesa().getDes_id() > 0) {
                    session.update(pc.getDespesa());
                } else {
                    session.save(pc.getDespesa());
                }
                if (pc.getDespesames().getDsm_id() > 0) {
                    session.update(pc.getDespesames());
                } else {
                    session.save(pc.getDespesames());
                }
                session.save(pc);
            }
        }
        session.getTransaction().commit();
        session.close();
    }

    public Custo SearchCusto(int id, int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        Custo c = null;
        CustoDespesa cd = (CustoDespesa) session.createQuery("select cd from CustoDespesa cd "
                + "join cd.custo cus "
                + "where cd.despesa.des_id = :d "
                + "and month(cus.cus_data_ref) = m "
                + "and year(cus.cus_data_ref) = a ")
                .setParameter("d", id)
                .setParameter("m", mes)
                .setParameter("a", ano)
                .uniqueResult();
        session.close();
        if (cd != null) {
            c = cd.getCusto();
        }
        return c;
    }
}
