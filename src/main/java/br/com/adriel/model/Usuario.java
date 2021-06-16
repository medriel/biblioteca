package br.com.adriel.model;

public class Usuario {
    
    private String login;
    private String senha;
    private String cargo;
    private static String loginAtual;

    public Usuario(){
        
    }

    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }
    public Usuario(String login, String senha, String cargo) {
        this.login = login;
        this.senha = senha;
        this.cargo = cargo;
    }

    public static String getLoginAtual() {
        return loginAtual;
    }

    public static void setLoginAtual(String loginAtual) {
        Usuario.loginAtual = loginAtual;
    }

    public Usuario(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return login;
    }
}
