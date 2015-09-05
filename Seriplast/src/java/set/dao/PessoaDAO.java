package set.dao;

import set.model.Pessoa;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class PessoaDAO {

    private Session session;
    public int id;//ID da solicitalção/curso/treinamento

    public PessoaDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void insert(Pessoa b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(b);
        t.commit();
        session.close();
    }

    public void update(Pessoa b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(b);
        t.commit();
        session.close();
    }

    public void delete(Pessoa b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(b);
        t.commit();
        session.close();
    }

    public Pessoa findById(int pes_codigo) {
        session = HibernateUtil.getSessionFactory().openSession();
        Pessoa p = (Pessoa) session.load(Pessoa.class, pes_codigo);
        session.close();
        return p;
    }

    public List<Pessoa> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Pessoa> ls = session.createQuery("from Pessoa").list();
        session.close();
        return ls;
    }
}
