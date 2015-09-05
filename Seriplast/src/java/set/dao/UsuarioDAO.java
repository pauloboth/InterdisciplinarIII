package set.dao;

import set.model.Usuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class UsuarioDAO {

    private Session session;

    public UsuarioDAO() {
    }

    public Session getSession() {
        if (session == null || !session.isOpen() || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public void insert(Usuario b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(b);
        t.commit();
        session.close();
    }

    public void update(Usuario b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.merge(b);
        t.commit();
        session.close();
    }

    public void delete(Usuario b) {
        session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.delete(b);
        t.commit();
        session.close();
    }

    public Usuario findByLogin(String usu_login) {
        return (Usuario) getSession().load(Usuario.class, usu_login);
    }

    public List<Usuario> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Usuario> ls = session.createQuery("from Usuario").list();
        session.close();
        return ls;
    }

    public Usuario findUser(String usu_login, String usu_senha) {
        Query q = getSession().createQuery("from Usuario where usu_login = '" + usu_login + "' and usu_senha = '" + usu_senha + "'");
        if (q.list().isEmpty()) {
            return null;
        } else {
            return (Usuario) q.list().get(0);
        }
    }

//    public void insert(Usuario usuario) {
//        try {
//            Transaction t = getSession().beginTransaction();
//            try {
//                getSession().save(usuario);
//                t.commit();
//            } catch (Exception ex) {
//                t.rollback();
//                throw ex;
//            }
//        } finally {
//            getSession().close();
//        }
//    }
//
//    public void update(Usuario usuario) {
//        try {
//            Transaction t = getSession().beginTransaction();
//            try {
//                getSession().merge(usuario);
//                t.commit();
//            } catch (Exception ex) {
//                t.rollback();
//                throw ex;
//            }
//        } finally {
//            getSession().close();
//        }
//    }
//
//    public void delete(Usuario usuario) {
//        try {
//            Transaction t = getSession().beginTransaction();
//            try {
//                getSession().delete(usuario);
//                t.commit();
//            } catch (Exception ex) {
//                t.rollback();
//                throw ex;
//            }
//        } finally {
//            getSession().close();
//        }
//    }
//
//    public Usuario findByLogin(String usu_login) {
//        return (Usuario) getSession().load(Usuario.class, usu_login);
//    }
//
//    public Usuario findUser(String usu_login, String usu_senha) {
//        Query q = getSession().createQuery("from Usuario where usu_login = '" + usu_login + "' and usu_senha = '" + usu_senha + "'");
//        if (q.list().isEmpty()) {
//            return null;
//        } else {
//            return (Usuario) q.list().get(0);
//        }
//    }
//
//    public List<Usuario> findAll() {
//        Query q = getSession().createQuery("from Usuario");
//        return q.list();
//    }
}
