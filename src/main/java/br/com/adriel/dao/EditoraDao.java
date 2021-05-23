package br.com.adriel.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.model.Editora;

public class EditoraDao extends Dao implements Persistencia<Editora>{

    @Override
    public void gravar(Editora dado) throws Exception {
        String sql = "insert into editora(nome) values(?)";
        PreparedStatement ps=getPreparedStatement(true,sql);
        ps.setString(1, dado.getNome());


        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        
        if(rs.next()){
            dado.setCodigo(rs.getLong("codigo"));
        }
    }

    @Override
    public List<Editora> getDados() throws Exception {
        String sql = "select * from editora";

        PreparedStatement ps = getPreparedStatement(false,sql);
        ResultSet rs = ps.executeQuery();

        List<Editora> editoras = new ArrayList<Editora>();
        while(rs.next()) {
            Editora editora = new Editora();
            editora.setCodigo(rs.getLong("codigo"));
            editora.setNome(rs.getString("nome"));
            editoras.add(editora);
        }
        return editoras;
    }

    @Override
    public void alterar(Editora dado) throws Exception {
        String sql = "update editora set nome =? where codigo=?";
        PreparedStatement ps=getPreparedStatement(true,sql);
        ps.setString(1, dado.getNome());
        ps.setLong(2,dado.getCodigo());

        ps.executeUpdate();
    }

    @Override
    public void excluir(Editora dado) throws Exception {
        String sql = "delete from editora where codigo=?";
        PreparedStatement ps=getPreparedStatement(true,sql);
        ps.setLong(1,dado.getCodigo());

        ps.executeUpdate();
        
        dado=null;
    }
    
}
