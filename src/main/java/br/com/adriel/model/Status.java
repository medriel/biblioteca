package br.com.adriel.model;

public enum Status {
    emprestado("Emprestado"),
    disponivel("Disponivel");

    private final String valor;

    private Status(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return this.valor;
    }
}
