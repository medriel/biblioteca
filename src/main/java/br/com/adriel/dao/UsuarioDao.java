package br.com.adriel.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.model.Usuario;

public class UsuarioDao extends Dao implements Persistencia<Usuario> {

    @Override
    public void gravar(Usuario dado) throws Exception {
        String sql = "insert into usuario (login,senha,cargo) values (?,?,?)";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getLogin());
        ps.setString(2, dado.getSenha());
        ps.setString(3, dado.getCargo());
        ps.executeUpdate();

    }

    @Override
    public List<Usuario> getDados() throws Exception {
        String sql = "select usuario.login, usuario.senha, usuario.cargo from usuario order by usuario.cargo";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ResultSet rs = ps.executeQuery();
        List<Usuario> usuarios = new ArrayList<Usuario>();
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setCargo(rs.getString("cargo"));
            usuarios.add(usuario);
        }
        return usuarios;
    }

    @Override
    public void alterar(Usuario dado) throws Exception {

    }

    @Override
    public void excluir(Usuario dado) throws Exception {
        String sql = "delete from usuario where login =?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, dado.getLogin());
        ps.executeUpdate();
    }

    public Usuario autenticar(Usuario usuario) throws Exception {

        String sql = "select usuario.login, usuario.senha, usuario.cargo from usuario where usuario.login=? and usuario.senha=?";
        PreparedStatement ps = getPreparedStatement(false, sql);
        ps.setString(1, usuario.getLogin());
        ps.setString(2, usuario.getSenha());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Usuario usuarioOk = new Usuario();
            usuarioOk.setLogin(rs.getString("login"));
            usuarioOk.setCargo(rs.getString("cargo"));

            return usuarioOk;

        } else {
            return null;
        }
    }
}
