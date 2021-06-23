package br.com.adriel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.model.Aluno;

public class AlunoDao extends Dao implements Persistencia<Aluno> {

    @Override
    public void gravar(Aluno dado) throws Exception {
        new LeitorDao().gravar(dado);

        String sql = "insert into aluno (leitor_cpf, matricula) values (?,?)";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getCpf());
        ps.setString(2, dado.getMatricula());
        ps.executeUpdate();
    }

    @Override
    public List<Aluno> getDados() throws Exception {
        String sql = "select leitor.cpf, leitor.nome, aluno.matricula from aluno inner join leitor on leitor.cpf = aluno.leitor_cpf order by leitor.nome";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();
        List<Aluno> alunos = new ArrayList<Aluno>();
        while (rs.next()) {
            Aluno aluno = new Aluno();
            aluno.setCpf(rs.getString("cpf"));
            aluno.setMatricula(rs.getString("matricula"));
            aluno.setNome(rs.getString("nome"));
            alunos.add(aluno);
        }
        return alunos;
    }

    @Override
    public void alterar(Aluno dado) throws Exception {
        new LeitorDao().alterar(dado);

        String sql = "update aluno set matricula=? where leitor_cpf =?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getMatricula());
        ps.setString(2, dado.getCpf());
        ps.executeUpdate();

    }

    @Override
    public void excluir(Aluno dado) throws Exception {
        String sql = "delete from aluno where leitor_cpf =?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getCpf());
        ps.executeUpdate();

        new LeitorDao().excluir(dado);

    }

    public Aluno buscarAluno(String cpf) throws Exception {
        String sql = "select leitor.cpf, leitor.nome, aluno.matricula from aluno inner join leitor"
                + " on leitor.cpf = aluno.leitor_cpf where leitor.cpf =?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, cpf);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Aluno aluno = new Aluno();
            aluno.setCpf(rs.getString("cpf"));
            aluno.setMatricula(rs.getString("matricula"));
            aluno.setNome(rs.getString("nome"));
            return aluno;
        } else {
            return null;
        }
    }

}
