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

    public void saveList(ProdutoCusto pc) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        if (pc != null) {
            if (pc.getDespesa().getDes_id() == 0) {
                session.save(pc.getDespesa());
            }
            if (pc.getDespesames().getDsm_id() > 0) {
                session.update(pc.getDespesames());
            } else {
                pc.getDespesames().setDsm_data(new Date());
                session.save(pc.getDespesames());
            }
            if (pc.getLsCustoDespsa() != null && !pc.getLsCustoDespsa().isEmpty()) {
                for (CustoDespesa cd : pc.getLsCustoDespsa()) {
                    if (cd.getCusto().getCus_id() > 0) {
                        session.update(cd.getCusto());
                    } else {
                        cd.getCusto().setCus_data(new Date());
                        session.save(cd.getCusto());
                    }
                    session.save(cd);
                }
            }
        }
        session.getTransaction().commit();
        session.close();
    }

    public Custo SearchCusto(int pro_id, int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Custo> lsC = session.createQuery("select c from Custo c "
                + "where c.produto.pro_id = :pro_id "
                + "and month(c.cus_data_ref) = :m "
                + "and year(c.cus_data_ref) = :a ")
                .setParameter("pro_id", pro_id)
                .setParameter("m", mes)
                .setParameter("a", ano)
                .list();
        session.close();
        if (lsC != null && !lsC.isEmpty()) {
            return lsC.get(0);
        }
        return null;
    }

     public List<Custo> CustoMensal(int pro_id, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
//        List<Custo> cus = session.createQuery("select cus from Custo cus "
//                + "left outer join fetch cus.lsCustoDespesa pd "
//                + "where  cus.produto.pro_id = :pro_id "
//                + "and year(cus.cus_data_ref) = :a ")
//                .setParameter("pro_id", pro_id)
//                .setParameter("a", ano)
//                .list();
//        session.close();
        List<Custo> cus = session.createQuery("select cus from Custo cus "
                + "where cus.produto.pro_id = :pro_id "
                + "and year(cus.cus_data_ref) = :a ")
                .setParameter("pro_id", pro_id)
                .setParameter("a", ano)
                .list();
        if (cus != null) {
            for (Custo c : cus) {
                List<CustoDespesa> lscd = session.createQuery("select cd from CustoDespesa cd "
                        + "where cd.custo.cus_id = :c")
                        .setParameter("c", c.getCus_id())
                        .list();
                c.setLsCustoDespesa(lscd);
            }
        }
        session.close();
        return cus;
    }
       public List<Custo> CustoAnual(int pro_id) {
        session = HibernateUtil.getSessionFactory().openSession();
//        List<Custo> cus = session.createQuery("select cus from Custo cus "
//                + "left outer join fetch cus.lsCustoDespesa pd "
//                + "where  cus.produto.pro_id = :pro_id "
//                + "and year(cus.cus_data_ref) = :a ")
//                .setParameter("pro_id", pro_id)
//                .setParameter("a", ano)
//                .list();
//        session.close();
        List<Custo> cus = session.createQuery("select cus from Custo cus "
                + "where cus.produto.pro_id = :pro_id "
                )
                .setParameter("pro_id", pro_id)
                .list();
        if (cus != null) {
            for (Custo c : cus) {
                List<CustoDespesa> lscd = session.createQuery("select cd from CustoDespesa cd "
                        + "where cd.custo.cus_id = :c")
                        .setParameter("c", c.getCus_id())
                        .list();
                c.setLsCustoDespesa(lscd);
            }
        }
        session.close();
        return cus;
    }
}
    
      
