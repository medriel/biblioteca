package br.com.adriel.model;

public enum Estado {
    emprestado("Emprestado"),
    disponivel("Disponivel");

    private final String valor;

    private Estado(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return this.valor;
    }
}
