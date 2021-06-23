package br.com.adriel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LeitorTest {

    @Test
    void isLeitorValido() {
        Leitor leitor = new Leitor();
        leitor.setCpf("243.739.051-00");
        leitor.setNome("nome");

        assertEquals(leitor.getCpf(), "243.739.051-00");
        assertEquals(leitor.getNome(), "nome");
    }

    @Test
    void isLeitorAlunoValido(){
        Aluno aluno = new Aluno();
        aluno.setNome("nome");
        aluno.setMatricula("matricula");
        aluno.setCpf("243.739.051-00");

        assertEquals(aluno.getCpf(), "243.739.051-00");
        assertEquals(aluno.getNome(), "nome");
        assertEquals(aluno.getMatricula(), "matricula");
    }

    @Test
    void isLeitorProfessorValido(){
        Professor professor = new Professor();
        professor.setCpf("243.739.051-00");
        professor.setNome("nome");
        professor.setDisciplina("disciplina");

        assertEquals(professor.getCpf(), "243.739.051-00");
        assertEquals(professor.getNome(), "nome");
        assertEquals(professor.getDisciplina(), "disciplina");
    }

}
