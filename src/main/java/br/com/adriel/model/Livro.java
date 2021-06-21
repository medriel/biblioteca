package br.com.adriel.model;

import java.util.ArrayList;
import java.util.List;

public class Livro {
    private String isbn;
    private String nome;
    private String ano;
    private Editora editora;
    private List<Autor> autores = new ArrayList<Autor>();
    private List<Exemplar> exemplares = new ArrayList<Exemplar>();
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Editora getEditora() {
        return editora;
    }
    public void setEditora(Editora editora) {
        this.editora = editora;
    }
    public List<Autor> getAutores() {
        return autores;
    }
    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
    
    public List<Exemplar> getExemplares() {
        return exemplares;
    }
    public void setExemplares(List<Exemplar> exemplares) {
        this.exemplares = exemplares;
    }

    public String getAno() {
        return ano;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }
    @Override
    public String toString() {
        return nome;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Livro other = (Livro) obj;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        return true;
    }
    
    public void adicionarAutor(Autor autor){
        autores.add(autor);
    }
    public void adicionarExemplar(Exemplar exemplar){
        exemplar.setLivro(this);
        exemplar.setStatus(Estado.disponivel);
        exemplares.add(exemplar);
    }
    public void removerAutor(Autor autor){
        autores.remove(autor);
    }
}
