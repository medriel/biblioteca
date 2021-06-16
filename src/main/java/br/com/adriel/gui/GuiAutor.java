package br.com.adriel.gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;

import br.com.adriel.dao.AutorDao;
import br.com.adriel.dao.Persistencia;
import br.com.adriel.model.Autor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GuiAutor implements Initializable {

    @FXML
    private ListView<Autor> lstAutores;
    @FXML
    private Button btnNovo;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnGravar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtNacionalidade;

    // controla se é uma alteração o uinclusão
    private Boolean alteracao;

    // O autor que está sendo trabalhado
    private Autor autor;

    // Objeto de manipulação de dados
    private Persistencia<Autor> autorDao = new AutorDao();

    // Codigos da tela
    private void habilitarEdicao(boolean editar) {
        txtId.setEditable(editar);
        txtNome.setEditable(editar);
        txtNacionalidade.setEditable(editar);

        btnGravar.setDisable(!editar);
        btnCancelar.setDisable(!editar);

        btnNovo.setDisable(editar);
        btnAlterar.setDisable(editar);
        btnExcluir.setDisable(editar);

        lstAutores.setDisable(editar);

        if (editar) {
            txtNome.requestFocus();
        }
    }

    private void preencherLista() {
        List<Autor> autores = new ArrayList<Autor>();
        try {
            autores = autorDao.getDados();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Autor> dados = FXCollections.observableArrayList(autores);

        lstAutores.setItems(dados);
    }

    private void getAutorSelecionado() {
        autor = lstAutores.getSelectionModel().getSelectedItem();
        txtId.setText(autor.getCodigo().toString());
        txtNome.setText(autor.getNome());
        txtNacionalidade.setText(autor.getNacionalidade());
    }

    private void limparCampos() {
        txtId.setText("");
        txtNacionalidade.setText("");
        txtNome.setText("");
    }

    // Eventos de Tela
    @FXML
    private void lstAutores_KeyPressed(KeyEvent event) {
        getAutorSelecionado();
    }

    @FXML
    private void lstAutores_MouseClicked(MouseEvent event) {
        getAutorSelecionado();
    }

    @FXML
    private void btnNovoAction(ActionEvent event) {
        autor = new Autor();
        limparCampos();
        alteracao = false;
        habilitarEdicao(true);
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        alteracao = true;
        habilitarEdicao(true);
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        try {
            autorDao.excluir(autor);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        preencherLista();
        limparCampos();

    }

    @FXML
    private void btnGravarAction(ActionEvent event) {
        autor.setNacionalidade(txtNacionalidade.getText());
        autor.setNome(txtNome.getText());

        try {
            if (alteracao) {
                autorDao.alterar(autor);
            } else {
                autorDao.gravar(autor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        habilitarEdicao(false);
        preencherLista();
        limparCampos();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
    }
}
