package br.com.adriel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.model.Autor;

public class AutorDao extends Dao implements Persistencia<Autor>{
    
    public void gravar(Autor autor) throws Exception{
        String sql = "Insert into autor (nome, nacionalidade) values (?,?)";

        PreparedStatement ps = getPreparedStatement(true,sql);
        ps.setString(1, autor.getNome());
        ps.setString(2, autor.getNacionalidade());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if(rs.next()) {
            autor.setCodigo(rs.getLong("codigo"));
        }
    }

    public List<Autor> getDados() throws Exception{
        String sql = "SELECT * FROM autor order by nome";
        PreparedStatement ps = getPreparedStatement(false, sql);
        
        ResultSet rs = ps.executeQuery();
        
        List<Autor> autores = new ArrayList<Autor>();
        while (rs.next()) {
            Autor autor = new Autor();
            autor.setCodigo(rs.getLong("codigo"));
            autor.setNome(rs.getString("nome"));
            autor.setNacionalidade(rs.getString("nacionalidade"));

            autores.add(autor);
        }

        return autores;
    }
    @Override
    public void alterar(Autor dado) throws Exception {
        String sql = "update autor set nome = ? , nacionalidade = ? where codigo = ?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getNome());
        ps.setString(2, dado.getNacionalidade());
        ps.setLong(3, dado.getCodigo());

        ps.executeUpdate();

    }

    @Override
    public void excluir(Autor dado) throws Exception {
        String sql = "delete from autor where codigo = ?";
        PreparedStatement ps = getPreparedStatement(false,sql);
        ps.setLong(1, dado.getCodigo());
        ps.executeUpdate();

        dado = null;
    }
}
