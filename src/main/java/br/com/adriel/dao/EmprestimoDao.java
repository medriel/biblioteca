package br.com.adriel.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.model.Aluno;
import br.com.adriel.model.Emprestimo;
import br.com.adriel.model.Status;
import br.com.adriel.model.Professor;

public class EmprestimoDao extends Dao implements Persistencia<Emprestimo> {

    @Override
    public void gravar(Emprestimo dado) throws Exception {
        String sql = "insert into emprestimo (dataemprestimo, leitor_cpf, exemplar_codigo, endereco, telefone) values (?,?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setDate(1, Date.valueOf(dado.getDataEmprestimo()));
        ps.setString(2, dado.getLeitor().getCpf());
        ps.setLong(3, dado.getExemplar().getCodigo());
        ps.setString(4, dado.getEndereco());
        ps.setString(5, dado.getTelefone());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            dado.setCodigo(rs.getLong("codigo"));
        }
    }

    @Override
    public List<Emprestimo> getDados() throws Exception {
        String sql = "select codigo, dataemprestimo, datadevolucao, leitor_cpf, exemplar_codigo, endereco, telefone from emprestimo order by dataemprestimo";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();

        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
        while (rs.next()) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setCodigo(rs.getLong("codigo"));
            if (!(rs.getDate("datadevolucao") == null)) {
                emprestimo.setDataDevolucao(rs.getDate("datadevolucao").toLocalDate());
            }
            emprestimo.setDataEmprestimo(rs.getDate("dataemprestimo").toLocalDate());
            emprestimo.setLeitor(new LeitorDao().buscarLeitor(rs.getString("leitor_cpf")));
            emprestimo.setExemplar(new LivroDao().buscarExemplar(rs.getLong("exemplar_codigo")));
            emprestimo.setEndereco(rs.getString("endereco"));
            emprestimo.setTelefone(rs.getString("telefone"));

            emprestimos.add(emprestimo);
        }
        return emprestimos;
    }

    @Override
    public void alterar(Emprestimo dado) throws Exception {
        String sql = "update exemplar set status = ? where exemplar.codigo = ?";
        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setString(1, Status.disponivel.toString());
        ps.setLong(2, dado.getExemplar().getCodigo());
        ps.executeUpdate();

        String sql2 = "update emprestimo set datadevolucao = ? where codigo = ?";
        ps = getPreparedStatement(true, sql2);
        ps.setDate(1, Date.valueOf(dado.getDataDevolucao()));
        ps.setLong(2, dado.getCodigo());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Emprestimo dado) throws Exception {
        String sql = "update exemplar set status = ? where exemplar.codigo = ?";
        PreparedStatement ps = getPreparedStatement(true, sql);
        ps.setString(1, Status.disponivel.toString());
        ps.setLong(2, dado.getExemplar().getCodigo());
        ps.executeUpdate();

        String sql2 = "delete from emprestimo where codigo = ?";
        ps = getPreparedStatement(true, sql2);
        ps.setLong(1, dado.getCodigo());
        ps.executeUpdate();
        dado = null;
    }

    public List<Emprestimo> getDisponiveis() throws Exception {
        List<Emprestimo> emprestimos = getDados();
        List<Emprestimo> emprestimosDisponiveis = new ArrayList<Emprestimo>();

        for (Emprestimo e : emprestimos) {
            if (e.getDataDevolucao() == null) {
                emprestimosDisponiveis.add(e);
            }
        }
        return emprestimosDisponiveis;
    }

    public List<Emprestimo> getPendentes() throws Exception {
        String sql = "select codigo, dataemprestimo, datadevolucao, leitor_cpf, exemplar_codigo, endereco, telefone from emprestimo where datadevolucao is null order by dataemprestimo";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();

        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        while (rs.next()) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setCodigo(rs.getLong("codigo"));
            if (!(rs.getDate("datadevolucao") == null)) {
                emprestimo.setDataDevolucao(rs.getDate("datadevolucao").toLocalDate());
            }
            emprestimo.setDataEmprestimo(rs.getDate("dataemprestimo").toLocalDate());
            emprestimo.setLeitor(new LeitorDao().buscarLeitor(rs.getString("leitor_cpf")));
            emprestimo.setExemplar(new LivroDao().buscarExemplar(rs.getLong("exemplar_codigo")));

            if (emprestimo.getLeitor() instanceof Aluno
                    && ChronoUnit.DAYS.between(emprestimo.getDataEmprestimo(), LocalDate.now()) > Aluno
                            .getLimitedevolucao()
                    || ChronoUnit.DAYS.between(emprestimo.getDataEmprestimo(), LocalDate.now()) > Professor
                            .getLimitedevolucao()) {
                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }
}
