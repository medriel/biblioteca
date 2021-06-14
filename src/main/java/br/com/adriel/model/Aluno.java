package br.com.adriel.model;

public class Aluno extends Leitor{
    private String matricula;
    private static final Integer LIMITEDEVOLUCAO=15;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
