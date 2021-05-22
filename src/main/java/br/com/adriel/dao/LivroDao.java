package br.com.adriel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.model.Autor;
import br.com.adriel.model.Editora;
import br.com.adriel.model.Livro;

public class LivroDao extends Dao implements Persistencia<Livro>{

    @Override
    public void gravar(Livro dado) throws Exception {
        String sql = "insert into livro (isbn,nome,editora_codigo) values (?,?,?)";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.setString(2, dado.getNome());
        ps.setLong(3, dado.getEditora().getCodigo());
        ps.executeUpdate();

        for (Autor autor: dado.getAutores()){
            sql = "insert into livroautor (livro_isbn,autor_codigo) values (?,?)";
            ps = getPreparedStatement(false, sql);
            ps.setString(1, dado.getIsbn());
            ps.setLong(2, autor.getCodigo());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Livro> getDados() throws Exception {
        String sql = "select livro.isbn, livro.nome as nomelivro. editora.codigo. editora.nome as nomeeditora"
                    + " select from livro inner join editora on livro.codigoeditora = editora.codigoeditora"
                    + " order by livro.nome";

        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();  
        
        List<Livro> livros = new ArrayList<Livro>();
        
        while(rs.next()){
            Livro livro = new Livro();
            livro.setIsbn(rs.getString("isbn"));
            livro.setNome(rs.getString("nomelivro"));
            
            Editora editora = new Editora();
            editora.setCodigo(rs.getLong("codigo"));
            editora.setNome(rs.getString("nomeeditora"));

            livro.setEditora(editora);

            sql = "select autor.* from livroautor inner join autor on livroautor.autor_codigo = autor.codigo"
                + " where livroautor.livro_isbn = ?";
            PreparedStatement ps2 = getPreparedStatement(false, sql);
            ps2.setString(1, livro.getIsbn());
            ResultSet rs2 = ps2.executeQuery();

            while(rs2.next()){
                Autor autor = new Autor();
                autor.setCodigo(rs2.getLong("codigo"));
                autor.setNacionalidade(rs2.getString("nacionalidade"));
                autor.setNome(rs2.getString("nome"));

                livro.adicionarAutor(autor);
            }

            livros.add(livro);
        }

        return livros;
    }

    @Override
    public void alterar(Livro dado) throws Exception {
        String sql = "update into livro set isbn = ?, nome = ?, editora_codigo = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.setString(2, dado.getNome());
        ps.setLong(3, dado.getEditora().getCodigo());
        ps.executeUpdate();

        sql = "delete from livroautor where livro_isbn=?";
        ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.executeUpdate();

        for (Autor autor: dado.getAutores()){
            sql = "insert into livroautor (livro_isbn,autor_codigo) values (?,?)";
            ps = getPreparedStatement(false, sql);
            ps.setString(1, dado.getIsbn());
            ps.setLong(2, autor.getCodigo());
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(Livro dado) throws Exception {
        String sql = "delete from livroautor where livro_isbn=?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.executeUpdate();

        sql = "delete from livro where isbn = ?";
        ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getIsbn());
        ps.executeUpdate();
    }
    
}
