package br.com.adriel.model;

public class Professor extends Leitor {
    private String disciplina;
    private static final Integer LIMITEDEVOLUCAO = 30;

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public static Integer getLimitedevolucao() {
        return LIMITEDEVOLUCAO;
    }

}
