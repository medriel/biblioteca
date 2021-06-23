package br.com.adriel.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditoraTest {
    @Test
    void isEditoraValido() {
        Editora editora = new Editora();
        editora.setNome("nome");

        assertEquals(editora.getNome(), "nome");
    }
}
