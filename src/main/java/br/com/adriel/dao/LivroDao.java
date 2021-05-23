package br.com.adriel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.model.Autor;
import br.com.adriel.model.Editora;
import br.com.adriel.model.Exemplar;
import br.com.adriel.model.Livro;
import br.com.adriel.model.Estado;

public class LivroDao extends Dao implements Persistencia<Livro>{

    @Override
    public void gravar(Livro dado) throws Exception {
        String sql = "insert into livro(isbn,nome,editora_codigo) values(?,?,?)";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.setString(2, dado.getNome());
        ps.setLong(3, dado.getEditora().getCodigo());
        ps.executeUpdate();

        for (Autor autor: dado.getAutores()){
            sql = "insert into livroautor(livro_isbn,autor_codigo) values(?,?)";
            ps = getPreparedStatement(false, sql);
            ps.setString(1, dado.getIsbn());
            ps.setLong(2, autor.getCodigo());
            ps.executeUpdate();
        }

        for(Exemplar exemplar: dado.getExemplares()){
            sql = "insert into exemplar(livro_isbn,status) values(?,?)";
            ps=getPreparedStatement(true, sql);
            ps.setString(1, dado.getIsbn());
            ps.setString(2, exemplar.getStatus().toString());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                exemplar.setCodigo(rs.getLong("codigo"));
            }
        }
    }

    @Override
    public List<Livro> getDados() throws Exception {
        String sql = " select livro.isbn, livro.nome as nomelivro, editora.codigo, editora.nome as nomeeditora "
                    + " from livro inner join editora on livro.editora_codigo = editora.codigo "
                    + " order by livro.nome";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();
        
        List<Livro> livros = new ArrayList<Livro>();
        
        while (rs.next()) {
            Livro livro = new Livro();
            livro.setIsbn(rs.getString("isbn"));
            livro.setNome(rs.getString("nomelivro"));
            
            Editora editora = new Editora();
            editora.setCodigo(rs.getLong("codigo"));
            editora.setNome(rs.getString("nomeeditora"));
            livro.setEditora(editora);

            sql = "select autor.* from livroautor inner join autor on livroautor.autor_codigo = autor.codigo "
                + " where livroautor.livro_isbn = ?";
            PreparedStatement ps2 = getPreparedStatement(false, sql);
            ps2.setString(1, livro.getIsbn());
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                Autor autor = new Autor();
                autor.setCodigo(rs2.getLong("codigo"));
                autor.setNacionalidade(rs2.getString("nacionalidade"));
                autor.setNome(rs2.getString("nome"));

                livro.adicionarAutor(autor);
            }


            sql = "select * from exemplar where livro_isbn = ?";
            PreparedStatement ps3 = getPreparedStatement(false, sql);
            ps3.setString(1, livro.getIsbn());
            ResultSet rs3 = ps3.executeQuery();

            while (rs3.next()) {
                Exemplar exemplar = new Exemplar();
                exemplar.setCodigo(rs3.getLong("codigo"));
                exemplar.setStatus(Estado.valueOf(rs3.getString("status")));
                livro.adicionarExemplar(exemplar);
            }

            livros.add(livro);
        }

        return livros;
    }

    @Override
    public void alterar(Livro dado) throws Exception {
        String sql = "update livro set nome =?,editora_codigo=? where isbn=?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getNome());
        ps.setLong(2, dado.getEditora().getCodigo());
        ps.setString(3, dado.getIsbn());
        ps.executeUpdate();

        sql = "delete from livroautor where livro_isbn=?";
        ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.executeUpdate();

        for (Autor autor: dado.getAutores()){
            sql = "insert into livroautor(livro_isbn,autor_codigo) values(?,?)";
            ps = getPreparedStatement(false, sql);
            ps.setString(1, dado.getIsbn());
            ps.setLong(2, autor.getCodigo());
            ps.executeUpdate();
        }

        for (Exemplar exemplar: dado.getExemplares()){
            if(exemplar.getCodigo() == null){
                sql = "insert into exemplar(livro_isbn,status) values(?,?)";
                ps = getPreparedStatement(true, sql);
                ps.setString(1, dado.getIsbn());
                ps.setString(2, exemplar.getStatus().toString());
                ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                exemplar.setCodigo(rs.getLong("codigo"));
            }else{
                sql = "update exemplar set status =? where codigo=?";
                ps = getPreparedStatement(false, sql);
                ps.setString(1, exemplar.getStatus().toString());
                ps.setLong(2, exemplar.getCodigo());
                ps.executeUpdate();
            }
            }
        }
    }

    @Override
    public void excluir(Livro dado) throws Exception {
        String sql = "delete from exemplar where livro_isbn=?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.executeUpdate();

        sql = "delete from livroautor where livro_isbn=?";
        ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.executeUpdate();

        sql = "delete from livro where isbn=?";
        ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.executeUpdate();
    }
    
}
