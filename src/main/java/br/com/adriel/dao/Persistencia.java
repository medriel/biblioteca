package br.com.adriel.dao;

import java.util.List;

public interface Persistencia<T> {
    public void gravar(T dado) throws Exception;

    public List<T> getDados() throws Exception;

    public void alterar(T dado) throws Exception;
    
    public void excluir(T dado) throws Exception;
}
