package br.com.adriel.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class EmprestimoTest {
    @Test
    void isEmprestimoValido() {

        try {
            Emprestimo emprestimo = new Emprestimo();
            LocalDate now = LocalDate.now();

            Leitor leitor = new Leitor();
            leitor.setCpf("243.739.051-00");
            leitor.setNome("leitor");

            Editora editora = new Editora();
            editora.setNome("editora");

            Autor autor = new Autor();
            autor.setNome("autor");
            autor.setNacionalidade("nacionalidade");

            Livro livro = new Livro();
            livro.setIsbn("isbn");
            livro.setNome("nome");
            livro.setAno("ano");
            livro.setEditora(editora);
            livro.adicionarAutor(autor);

            Exemplar exemplar = new Exemplar();
            exemplar.setLivro(livro);
            exemplar.setStatus(Status.disponivel);

            emprestimo.setDataEmprestimo(now);
            emprestimo.setEndereco("endereco");
            emprestimo.setTelefone("telefone");
            emprestimo.setLeitor(leitor);
            emprestimo.setExemplar(exemplar);

            assertEquals(emprestimo.getDataEmprestimo(), now);
            assertEquals(emprestimo.getEndereco(), "endereco");
            assertEquals(emprestimo.getTelefone(), "telefone");
            assertEquals(emprestimo.getLeitor().getCpf(), leitor.getCpf());
            assertEquals(emprestimo.getExemplar().getLivro().getIsbn(), exemplar.getLivro().getIsbn());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
