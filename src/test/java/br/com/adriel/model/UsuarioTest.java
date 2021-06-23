package br.com.adriel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UsuarioTest {
    @Test
    void isUsuarioValido() {
        Usuario usuario = new Usuario("login", "111111", "Cargo");
        assertEquals(usuario.getLogin(), "login");
    }
}
