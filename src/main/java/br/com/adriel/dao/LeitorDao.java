package br.com.adriel.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.model.Aluno;
import br.com.adriel.model.Leitor;
import br.com.adriel.model.Professor;


public class LeitorDao extends Dao implements Persistencia<Leitor>{

    @Override
    public void gravar(Leitor dado) throws Exception {
        String sql = "insert into leitor (cpf,nome) values (?,?)";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getCpf());
        ps.setString(2, dado.getNome());
        ps.executeUpdate();
        
    }

    @Override
    public List<Leitor> getDados() throws Exception {
        List<Professor> professores = new ProfessorDao().getDados();
        List<Aluno> alunos = new AlunoDao().getDados();

        List<Leitor> leitores = new ArrayList<Leitor>();

        leitores.addAll(professores);
        leitores.addAll(alunos);

        return leitores; 
    }

    @Override
    public void alterar(Leitor dado) throws Exception {
        String sql = "update leitor set nome =? where cpf =?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getNome());
        ps.setString(2, dado.getCpf());
        ps.executeUpdate();
        
    }

    @Override
    public void excluir(Leitor dado) throws Exception {
        String sql = "delete from leitor where cpf =?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getCpf());
        ps.executeUpdate();
        
    }

    public Leitor buscarLeitor(String cpf) throws Exception{
        Leitor leitor = new AlunoDao().buscarAluno(cpf);

        if(leitor == null){
            leitor= new ProfessorDao().buscarProfessor(cpf);
        }

        return leitor; 
    }
    
}
