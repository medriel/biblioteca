package br.com.adriel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Dao {
    private final String ENDERECO = "localhost";
    private final String BANCODEDADOS = "bdbiblio";
    private final String PORTA = "5432";
    private final String USUARIO = "postgres";
    private final String SENHA = "postgres";

    //private Connection conexao;

    //conexao com o banco de dados ------------------------------------------------------------------------------
    protected Connection getConexao() throws SQLException {
        String url = "jdbc:postgresql://" + ENDERECO + ":" + PORTA + "/" + BANCODEDADOS;
        Connection conn = DriverManager.getConnection(url, USUARIO, SENHA);
        return conn;
    }
    // -----------------------------------------------------------------------------------------------------------
    protected PreparedStatement getPreparedStatement(boolean chavePrimaria, String sql) throws Exception {
        PreparedStatement ps = null;
        if (chavePrimaria) {
            ps = getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } else {
            ps = getConexao().prepareStatement(sql);
        }

        return ps;
    }
    /*protected void fecharConexao() throws SQLException {
        conexao.close();
      }*/
}
