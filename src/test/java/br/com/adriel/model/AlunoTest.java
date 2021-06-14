package br.com.adriel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AlunoTest {
    @Test
    void nomeAlunoTeste() {
        final Aluno aluno = new Aluno();
        aluno.setNome("Pedro");
        String esperado = "Pedro";
        assertEquals(esperado, aluno.getNome());
    }    
}
