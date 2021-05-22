package br.com.adriel.tests;

import java.util.List;

import br.com.adriel.dao.AutorDao;
import br.com.adriel.dao.Persistencia;
import br.com.adriel.model.Autor;

public class AutorTest {
    
    private static Persistencia<Autor> dao = new AutorDao();

    public static void main(String[] args) throws Exception {
        
        Autor autor = new Autor();
        autor.setNome("Pietro");
        autor.setNacionalidade("Espanhola");

        try {
            dao.gravar(autor);
            System.out.println("O codigo gerado foi: "+autor.getCodigo().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        try {
            List<Autor> autores = dao.getDados();
            for(Autor a: autores){
                System.out.print(a.getCodigo());
                System.out.print("-");
                System.out.print(a.getNome());
                System.out.print("-");
                System.out.println(a.getNacionalidade());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
