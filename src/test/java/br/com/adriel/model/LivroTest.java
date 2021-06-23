package br.com.adriel.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class LivroTest {
    @Test
    void isLivroValido(){
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

        livro.adicionarExemplar(exemplar);

        List<Autor> autores = new ArrayList<Autor>();
        autores.add(autor);

        List<Exemplar> exemplares = new ArrayList<Exemplar>();
        exemplares.add(exemplar);

        assertEquals(livro.getIsbn(),"isbn");
        assertEquals(livro.getNome(), "nome");
        assertEquals(livro.getAno(),"ano");
        assertEquals(livro.getEditora().getNome(), "editora");
        assertEquals(livro.getAutores(), autores);
        assertEquals(livro.getExemplares(), exemplares);

    }
}
