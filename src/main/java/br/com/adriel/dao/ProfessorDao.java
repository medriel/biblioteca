package br.com.adriel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.model.Professor;

public class ProfessorDao extends Dao implements Persistencia<Professor>{

    @Override
    public void gravar(Professor dado) throws Exception {
        new LeitorDao().gravar(dado);
        
        String sql = "insert into professor (leitor_cpf, disciplina) values (?,?)";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getCpf());
        ps.setString(2, dado.getDisciplina());
        ps.executeUpdate();
    }

    @Override
    public List<Professor> getDados() throws Exception {
        String sql = "select leitor.cpf, leitor.nome, professor.disciplina from  professor inner join leitor"
                + "on leitor.cpf = professor.leitor_cpf order by leitor.nome";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();
        List<Professor> professores = new ArrayList<Professor>();
        while(rs.next()){
            Professor professor = new Professor();
            professor.setCpf(rs.getString("cpf"));
            professor.setDisciplina(rs.getString("matricula"));
            professor.setNome(rs.getString("nome"));
        }
        return professores;
    }

    @Override
    public void alterar(Professor dado) throws Exception {
        new LeitorDao().alterar(dado);

        String sql = "update professor set disciplina=? where leitor_cpf =?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getDisciplina());
        ps.setString(2, dado.getCpf());
        ps.executeUpdate();
        
    }

    @Override
    public void excluir(Professor dado) throws Exception {
        String sql = "delete from professor where leitor_cpf =?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getCpf());
        ps.executeUpdate();

        new LeitorDao().excluir(dado);
        
    }
    
    public Professor buscarProfessor(String cpf) throws Exception{
        String sql = "select leitor.cpf, leitor.nome, professor.disciplina from  professor inner join leitor"
                + "on leitor.cpf = professor.leitor_cpf where leitor.cpf =?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, cpf);
        ResultSet rs = ps.executeQuery();
        
        if(rs.next()){
            Professor professor = new Professor();
            professor.setCpf(rs.getString("cpf"));
            professor.setDisciplina(rs.getString("matricula"));
            professor.setNome(rs.getString("nome"));
            return professor;
        }else{
            return null;
        }
    }
}
