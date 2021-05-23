package br.com.adriel.tests;

import java.util.List;

import br.com.adriel.dao.AutorDao;
import br.com.adriel.dao.EditoraDao;
import br.com.adriel.dao.LivroDao;
import br.com.adriel.dao.Persistencia;
import br.com.adriel.model.Autor;
import br.com.adriel.model.Estado;
import br.com.adriel.model.Exemplar;
import br.com.adriel.model.Livro;

public class LivroTeste {
    private static Persistencia<Livro> dao = new LivroDao();

    public static void main(String[] args) throws Exception {

        try {
            Livro livro = new Livro();
            livro.setIsbn("123123");
            livro.setNome("Livro de teste 2");

            livro.setEditora(new EditoraDao().getDados().get(0));

            List<Autor> autores = new AutorDao().getDados();

            for (int i = 0; i < 2; i++) {
                livro.adicionarAutor(autores.get(i));
            }

            for (int i = 0; i < 4; i++) {
                Exemplar exemplar = new Exemplar();
                exemplar.setStatus(Estado.disponivel);
                livro.adicionarExemplar(exemplar);
            }

            dao.gravar(livro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
