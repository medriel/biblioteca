package br.com.adriel.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.adriel.dao.AutorDao;
import br.com.adriel.dao.EditoraDao;
import br.com.adriel.dao.LivroDao;
import br.com.adriel.dao.Persistencia;
import br.com.adriel.model.Autor;
import br.com.adriel.model.Editora;
import br.com.adriel.model.Exemplar;
import br.com.adriel.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GuiLivro implements Initializable {
    // Objetos do programa
    private Livro livro;

    private Persistencia<Livro> livroDao = new LivroDao();
    private Persistencia<Editora> editoraDao = new EditoraDao();
    private Persistencia<Autor> autorDao = new AutorDao();

    private Boolean alterando;

    // Objetos de tela
    @FXML
    private ListView<Livro> LstLivros;
    @FXML
    private ListView<Autor> LstAutores;
    @FXML
    private ListView<Exemplar> LstExemplares;

    @FXML
    private Button BtnNovo;
    @FXML
    private Button BtnAlterar;
    @FXML
    private Button BtnExcluir;
    @FXML
    private Button BtnAdicionarAutor;
    @FXML
    private Button BtnRemoverAutor;
    @FXML
    private Button BtnAdicionarExemplar;
    @FXML
    private Button BtnGravar;
    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtIsbn;
    @FXML
    private TextField TxtNome;

    @FXML
    private ComboBox<Editora> CboEditora;
    @FXML
    private ComboBox<Autor> CboAutor;

    // Funcionalidades
    private void habilitarEdicao(boolean entrar) {
        BtnNovo.setDisable(entrar);
        BtnAlterar.setDisable(entrar);
        BtnExcluir.setDisable(entrar);
        TxtIsbn.setEditable(entrar);
        TxtNome.setEditable(entrar);
        CboEditora.setDisable(!entrar);
        CboAutor.setDisable(!entrar);
        BtnAdicionarAutor.setDisable(!entrar);
        BtnRemoverAutor.setDisable(!entrar);
        LstAutores.setDisable(!entrar);
        BtnAdicionarExemplar.setDisable(!entrar);
        LstExemplares.setDisable(!entrar);
        BtnGravar.setDisable(!entrar);
        BtnCancelar.setDisable(!entrar);
    }

    private void preencherLivros() {
        try {
            List<Livro> livros = livroDao.getDados();
            ObservableList<Livro> dados = FXCollections.observableArrayList(livros);
            LstLivros.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherAutores() {
        try {
            List<Autor> autores = autorDao.getDados();
            ObservableList<Autor> dados = FXCollections.observableArrayList(autores);
            CboAutor.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherEditoras() {
        try {
            List<Editora> editoras = editoraDao.getDados();
            ObservableList<Editora> dados = FXCollections.observableArrayList(editoras);
            CboEditora.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherExemplaresLivro() {
        if (livro == null)
            return;
        try {
            ObservableList<Exemplar> dados = FXCollections.observableArrayList(livro.getExemplares());
            LstExemplares.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherAutoresLivro() {
        if (livro == null)
            return;
        try {
            ObservableList<Autor> dados = FXCollections.observableArrayList(livro.getAutores());
            LstAutores.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void exibirLivro() {
        if (livro == null)
            return;

        TxtIsbn.setText(livro.getIsbn());
        TxtNome.setText(livro.getNome());
        CboEditora.getSelectionModel().select(livro.getEditora());
        preencherAutoresLivro();
        preencherExemplaresLivro();
    }

    // Eventos de Tela
    @FXML
    private void BtnNovo_Action(ActionEvent event) {
        livro = new Livro();
        alterando = false;
        habilitarEdicao(true);
        TxtIsbn.setText("");
        TxtNome.setText("");
        CboEditora.getSelectionModel().clearSelection();
        CboAutor.getSelectionModel().clearSelection();
        TxtIsbn.requestFocus();
    }

    @FXML
    private void BtnAlterar_Action(ActionEvent event) {
        livro = LstLivros.getSelectionModel().getSelectedItem();
        exibirLivro();
        alterando = true;
        habilitarEdicao(true);
        TxtIsbn.setEditable(false);
        TxtNome.requestFocus();
    }

    @FXML
    private void BtnExcluir_Action(ActionEvent event) {

    }

    @FXML
    private void BtnAdicionarAutor_Action(ActionEvent event) {
        Autor autor = CboAutor.getSelectionModel().getSelectedItem();
        livro.adicionarAutor(autor);
        preencherAutoresLivro();

    }

    @FXML
    private void BtnRemoverAutor_Action(ActionEvent event) {
        Autor autor = LstAutores.getSelectionModel().getSelectedItem();
        livro.removerAutor(autor);
        preencherAutoresLivro();

    }

    @FXML
    private void BtnAdicionarExemplar_Action(ActionEvent event) {
        livro.adicionarExemplar(new Exemplar());
        preencherExemplaresLivro();
    }

    @FXML
    private void BtnGravar_Action(ActionEvent event) {
        try {
            livro.setEditora(CboEditora.getSelectionModel().getSelectedItem());
            livro.setNome(TxtNome.getText());
            if (alterando) {
                livroDao.alterar(livro);
            } else {
                livro.setIsbn(TxtIsbn.getText());
                livroDao.gravar(livro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        preencherLivros();
        habilitarEdicao(false);
    }

    @FXML
    private void BtnCancelar_Action(ActionEvent event) {
        habilitarEdicao(false);
    }

    @FXML
    private void btnRetornarAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/GuiBibliotecario.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            Stage stage = new Stage();
            stage.setTitle("Bem Vindo(a) Bibliotecario(a)");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) BtnNovo.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void LstLivros_KeyPressed(KeyEvent event) {
        livro = LstLivros.getSelectionModel().getSelectedItem();
        exibirLivro();

    }

    @FXML
    private void LstLivros_MouseClicked(MouseEvent event) {
        livro = LstLivros.getSelectionModel().getSelectedItem();
        exibirLivro();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherLivros();
        preencherAutores();
        preencherEditoras();
    }

}
