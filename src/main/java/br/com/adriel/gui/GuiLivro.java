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
    private ListView<Livro> lstLivros;
    @FXML
    private ListView<Autor> lstAutores;
    @FXML
    private ListView<Exemplar> lstExemplares;

    @FXML
    private Button btnNovo;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnAdicionarAutor;
    @FXML
    private Button btnRemoverAutor;
    @FXML
    private Button btnAdicionarExemplar;
    @FXML
    private Button btnGravar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtIsbn;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtAno;

    @FXML
    private ComboBox<Editora> cbEditora;
    @FXML
    private ComboBox<Autor> cbAutor;

    // Funcionalidades
    private void habilitarEdicao(boolean entrar) {
        btnNovo.setDisable(entrar);
        btnAlterar.setDisable(entrar);
        btnExcluir.setDisable(entrar);
        txtIsbn.setEditable(entrar);
        txtNome.setEditable(entrar);
        txtAno.setEditable(entrar);
        cbEditora.setDisable(!entrar);
        cbAutor.setDisable(!entrar);
        btnAdicionarAutor.setDisable(!entrar);
        btnRemoverAutor.setDisable(!entrar);
        lstAutores.setDisable(!entrar);
        btnAdicionarExemplar.setDisable(!entrar);
        lstExemplares.setDisable(!entrar);
        btnGravar.setDisable(!entrar);
        btnCancelar.setDisable(!entrar);
    }

    private void preencherLivros() {
        try {
            List<Livro> livros = livroDao.getDados();
            ObservableList<Livro> dados = FXCollections.observableArrayList(livros);
            lstLivros.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherAutores() {
        try {
            List<Autor> autores = autorDao.getDados();
            ObservableList<Autor> dados = FXCollections.observableArrayList(autores);
            cbAutor.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherEditoras() {
        try {
            List<Editora> editoras = editoraDao.getDados();
            ObservableList<Editora> dados = FXCollections.observableArrayList(editoras);
            cbEditora.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherExemplaresLivro() {
        if (livro == null)
            return;
        try {
            ObservableList<Exemplar> dados = FXCollections.observableArrayList(livro.getExemplares());
            lstExemplares.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherAutoresLivro() {
        if (livro == null)
            return;
        try {
            ObservableList<Autor> dados = FXCollections.observableArrayList(livro.getAutores());
            lstAutores.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void exibirLivro() {
        if (livro == null)
            return;

        txtIsbn.setText(livro.getIsbn());
        txtNome.setText(livro.getNome());
        txtAno.setText(livro.getAno());
        cbEditora.getSelectionModel().select(livro.getEditora());
        preencherAutoresLivro();
        preencherExemplaresLivro();
    }

    private void limparTela() {
        txtIsbn.setText("");
        txtNome.setText("");
        txtAno.setText("");
        cbEditora.getSelectionModel().clearSelection();
        cbAutor.getSelectionModel().clearSelection();
        lstAutores.getItems().clear();
        lstExemplares.getItems().clear();
    }

    @FXML
    private void btnNovoAction(ActionEvent event) {
        livro = new Livro();
        alterando = false;
        habilitarEdicao(true);
        limparTela();
        txtIsbn.requestFocus();
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        livro = lstLivros.getSelectionModel().getSelectedItem();
        exibirLivro();
        alterando = true;
        habilitarEdicao(true);
        txtIsbn.setEditable(false);
        txtNome.requestFocus();
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        try {
            livro = lstLivros.getSelectionModel().getSelectedItem();
            new LivroDao().excluir(livro);
            ;
            preencherLivros();
        } catch (Exception e) {
            e.printStackTrace();
        }
        limparTela();
    }

    @FXML
    private void btnAdicionarAutorAction(ActionEvent event) {
        Autor autor = cbAutor.getSelectionModel().getSelectedItem();
        livro.adicionarAutor(autor);
        preencherAutoresLivro();

    }

    @FXML
    private void btnRemoverAutorAction(ActionEvent event) {
        Autor autor = lstAutores.getSelectionModel().getSelectedItem();
        livro.removerAutor(autor);
        preencherAutoresLivro();

    }

    @FXML
    private void btnAdicionarExemplarAction(ActionEvent event) {
        livro.adicionarExemplar(new Exemplar());
        preencherExemplaresLivro();
    }

    @FXML
    private void btnGravarAction(ActionEvent event) {
        try {
            livro.setEditora(cbEditora.getSelectionModel().getSelectedItem());
            livro.setNome(txtNome.getText());
            livro.setAno(txtAno.getText());
            if (alterando) {
                livroDao.alterar(livro);
            } else {
                livro.setIsbn(txtIsbn.getText());
                livroDao.gravar(livro);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        preencherLivros();
        habilitarEdicao(false);
        limparTela();
    }

    @FXML
    private void btnCancelarAction(ActionEvent event) {
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
        Stage stage = (Stage) btnNovo.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void lstLivrosKeyPressed(KeyEvent event) {
        livro = lstLivros.getSelectionModel().getSelectedItem();
        exibirLivro();

    }

    @FXML
    private void lstLivrosMouseClicked(MouseEvent event) {
        livro = lstLivros.getSelectionModel().getSelectedItem();
        exibirLivro();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherLivros();
        preencherAutores();
        preencherEditoras();
    }

}
