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
        Transaction t = session.beginTransaction();
        session.save(b);
        t.commit();
    }

    public void update(Pessoa b) {
        Transaction t = session.beginTransaction();
        session.merge(b);
        t.commit();
    }

    public void delete(Pessoa b) {
        Transaction t = session.beginTransaction();
        session.delete(b);
        t.commit();
    }

    public Pessoa findById(int pes_codigo) {
        return (Pessoa) session.load(Pessoa.class, pes_codigo);
    }

    public List<Pessoa> findAll() {
        Query q = session.createQuery("from Pessoa");
        return q.list();
    }

    public List<Pessoa> searchPessoa(String name) {
        String sqlPessoa = "";
        if (name != null && name != "") {
            sqlPessoa = " and upper (translate(pes_nome, 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü', 'ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu'))"
                    + " LIKE upper(translate('%" + name + "%', 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü', 'ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu'))";
        }
        return session.createQuery("from Pessoa where 1 = 1 " + sqlPessoa).list();
    }

    public List<Pessoa> findAllFuncionarios() {
        Query q = session.createQuery("from Pessoa where pes_tipo  = 1 ");
        return q.list();
    }

    public List<Pessoa> findCandidatos(String pes_tipo) {
        Query q = session.createQuery("from Pessoa where pes_tipo in(" + pes_tipo + ")");
        return q.list();
    }

    public List<Pessoa> findPesSol() {//Procura as pessoas de uma determinada solicitação
        SQLQuery q = session.createSQLQuery("select pe.* from pessoa pe, trd_pessoas_recebertreinamento ir where pe.pes_codigo = ir.pes_codigo and ir.sol_codigo =" + id).addEntity(Pessoa.class);
        return q.list();
    }

    public List<Pessoa> findByPessoaId(int pes_codigo) {
        Query q = session.createQuery("from Pessoa where pes_codigo = :pes_codigo");
        return q.setParameter("pes_codigo", pes_codigo).list();
    }
    
    public List<Pessoa> findPesTre() {//Procura as pessoas de um determinado treinamento
        SQLQuery q = session.createSQLQuery("select pe.* from pessoa pe, trd_instrutores_treinamento it where pe.pes_codigo = it.pes_codigo and it.tre_codigo =" + id).addEntity(Pessoa.class);
        return q.list();
    }
    
    public List<Pessoa> searchPessoaTre(String name) {
        String sqlPessoa = "";
        String sqlTipoPessoa = " and pes_tipo = 4";
        if (name != null && name != "") {
            sqlPessoa = " and upper (translate(pes_nome, 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü', 'ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu'))"
                    + " LIKE upper(translate('%" + name + "%', 'ÁÇÉÍÓÚÀÈÌÒÙÂÊÎÔÛÃÕËÜáçéíóúàèìòùâêîôûãõëü', 'ACEIOUAEIOUAEIOUAOEUaceiouaeiouaeiouaoeu'))";
        }
        return session.createQuery("from Pessoa where 1 = 1 " + sqlPessoa+sqlTipoPessoa).list();
    }
    
    public List<Pessoa> findPesCur() {//Procura as pessoas cadastradas em um curso
        SQLQuery q = session.createSQLQuery("select distinct pe.* from pessoa pe, trd_cursos_pessoa cs where pe.pes_codigo = cs.pes_codigo").addEntity(Pessoa.class);
        return q.list();
    }
    
    public List<Pessoa> findPesTur() {//Procura as pessoas cadastradas em uma turma
        SQLQuery q = session.createSQLQuery("select pe.* from pessoa pe, trd_alunos_turma tu where pe.pes_codigo = tu.pes_codigo and tu.tur_codigo = "+id).addEntity(Pessoa.class);
        return q.list();
    }
}
