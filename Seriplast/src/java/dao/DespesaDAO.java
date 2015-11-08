package dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.CustoDespesa;
import model.Despesa;
import model.DespesaMes;
import model.ProdutoCusto;
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

    private void deleteFuturosLancamentos(int des_id) {
        int mes = new Date().getDate() + 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int ano = Integer.parseInt(sdf.format(new Date()));
        List<CustoDespesa> lsCd = session.createQuery("select cd from CustoDespesa cd "
                + "join cd.custo c "
                + "where cd.despesa.des_id = :des_id "
                + "and month(c.cus_data_ref) >= :mes "
                + "and year(c.cus_data_ref) >= :ano")
                .setParameter("des_id", des_id)
                .setParameter("mes", mes)
                .setParameter("ano", ano)
                .list();
        if (lsCd != null && !lsCd.isEmpty()) {
            for (CustoDespesa cd : lsCd) {
                session.delete(cd);
            }
        }
        List<DespesaMes> lsDm = session.createQuery("select dm from DespesaMes dm "
                + "where dm.despesa.des_id = :des_id "
                + "and month(dm.dsm_data_ref) >= :mes "
                + "and year(dm.dsm_data_ref) >= :ano")
                .setParameter("des_id", des_id)
                .setParameter("mes", mes)
                .setParameter("ano", ano)
                .list();
        if (lsDm != null && !lsDm.isEmpty()) {
            for (DespesaMes dm : lsDm) {
                session.delete(dm);
            }
        }
    }

    public void saveList(Despesa i, List<DespesaMes> lsDm, List<CustoDespesa> lsCd) {
        session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        if (i.getDes_id() > 0) {
            session.update(i);
            deleteFuturosLancamentos(i.getDes_id());
        } else {
            i.setDes_date(new Date());
            session.save(i);
        }
        if (lsDm != null) {
            for (DespesaMes dm : lsDm) {
                if (dm.getDsm_id() > 0) {
                    session.update(dm);
                } else {
                    dm.setDsm_data(new Date());
                    session.save(dm);
                }
            }
        }
        for (CustoDespesa cd : lsCd) {
            if (cd.getCusto().getCus_id() > 0) {
                session.update(cd.getCusto());
            } else {
                cd.setCsd_data(new Date());
                session.save(cd.getCusto());
            }
            cd.setCsd_data(new Date());
            session.save(cd);
        }
        session.getTransaction().commit();
        session.close();
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

    public List<Despesa> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Despesa> ls = session.createQuery("from Despesa").list();
        session.close();
        return ls;
    }

    public List<Despesa> findAllEdit() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Despesa> ls = session.createQuery("from Despesa where des_tipo in (1, 2) and des_status = 1").list();
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
        List<Despesa> ls = session.createQuery("select d from Despesa d where 1=1 "
                + "and (des_tipo in (1, 2)"
                + "or (des_tipo in (4, 5) and month(des_date) = :mes and year(des_date) = :ano))")
                .setParameter("mes", mes).setParameter("ano", ano).list();
        session.close();
        return ls;
    }

    public DespesaMes lastMonth(int id_des) {
        session = HibernateUtil.getSessionFactory().openSession();
        DespesaMes dm = null;
        Query q = session.createQuery("select dm from DespesaMes dm "
                + "left outer join dm.despesa d "
                + "where d.des_id = :d");
        q.setParameter("d", id_des);
        List<DespesaMes> ls = q.list();
        session.close();
        if (!ls.isEmpty()) {
            dm = ls.get(0);
        }
        return dm;
    }

    public List<CustoDespesa> findCustoDespesaMes(int des_id, int mes, int ano) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<CustoDespesa> lsCd = session.createQuery("select cd from CustoDespesa cd "
                + "join cd.custo c "
                + "where cd.despesa.des_id = :des_id "
                + "and month(c.cus_data_ref) = :mes and year(c.cus_data_ref) = :ano")
                .setParameter("des_id", des_id)
                .setParameter("mes", mes)
                .setParameter("ano", ano).list();
        session.close();
        return lsCd;
    }

    public void ExcluirLance(List<CustoDespesa> lsCd, DespesaMes dm, Despesa d) {
        if (lsCd != null && !lsCd.isEmpty()) {
            session = HibernateUtil.getSessionFactory().openSession();
            session.getTransaction().begin();
            for (CustoDespesa cd : lsCd) {
                session.delete(cd);
            }
            if (dm != null && dm.getDsm_id() > 0) {
                session.delete(dm);
            }
            if (d != null && d.getDes_id() > 0) {
                session.delete(d);
            }
            session.getTransaction().commit();
            session.close();
        }
    }
}
