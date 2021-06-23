package br.com.adriel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AutorTest {
    @Test
    void isAutorValido() {
        Autor autor = new Autor();
        autor.setNome("nome");
        autor.setNacionalidade("nacionalidade");

        assertEquals(autor.getNome(), "nome");
        assertEquals(autor.getNacionalidade(), "nacionalidade");
    }
}
