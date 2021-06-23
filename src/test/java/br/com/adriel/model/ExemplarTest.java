package br.com.adriel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ExemplarTest {
    @Test
    void isExemplarDisponivel() {
        Editora editora = new Editora();
        editora.setNome("editora");

        Autor autor = new Autor();
        autor.setNome("autor");
        autor.setNacionalidade("nacionalidade");

        Livro livro = new Livro();
        livro.setIsbn("isbn");
        livro.setNome("nome");
        livro.setAno("ano");
        livro.setEditora(editora);
        livro.adicionarAutor(autor);

        Exemplar exemplar = new Exemplar();
        exemplar.setLivro(livro);
        exemplar.setStatus(Status.disponivel);

        assertEquals(exemplar.getStatus(), Status.disponivel);
    }

    @Test
    void isExemplarEmprestado() {
        Editora editora = new Editora();
        editora.setNome("editora");

        Autor autor = new Autor();
        autor.setNome("autor");
        autor.setNacionalidade("nacionalidade");

        Livro livro = new Livro();
        livro.setIsbn("isbn");
        livro.setNome("nome");
        livro.setAno("ano");
        livro.setEditora(editora);
        livro.adicionarAutor(autor);

        Exemplar exemplar = new Exemplar();
        exemplar.setLivro(livro);
        exemplar.setStatus(Status.emprestado);

        assertEquals(exemplar.getStatus(), Status.emprestado);
    }
}
