package br.com.adriel.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.adriel.dao.EditoraDao;
import br.com.adriel.dao.Persistencia;
import br.com.adriel.model.Editora;
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

public class GuiEditora implements Initializable {

    @FXML
    private ListView<Editora> lstEditoras;
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

    // Controla se é uma inclusão ou alteração
    private Boolean alteracao;

    // O autor que está sendo trabalhado
    private Editora editora;

    // Objeto de manipulação de dados
    private Persistencia<Editora> editoraDao = new EditoraDao();

    // Códigos da tela
    private void habilitarEdicao(boolean editar) {
        txtId.setEditable(editar);
        txtNome.setEditable(editar);

        btnGravar.setDisable(!editar);
        btnCancelar.setDisable(!editar);

        btnNovo.setDisable(editar);
        btnAlterar.setDisable(editar);
        btnExcluir.setDisable(editar);

        lstEditoras.setDisable(editar);

        if (editar) {
            txtNome.requestFocus();
        }
    }

    private void preencherLista() {
        List<Editora> editoras = new ArrayList<Editora>();
        try {
            editoras = editoraDao.getDados();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Editora> dados = FXCollections.observableArrayList(editoras);

        lstEditoras.setItems(dados);
    }

    private void getEditoraSelecionado() {
        editora = lstEditoras.getSelectionModel().getSelectedItem();
        txtId.setText(editora.getCodigo().toString());
        txtNome.setText(editora.getNome());
    }

    private void limparTela() {
        txtId.setText("");
        txtNome.setText("");
    }

    // Eventos de Tela
    @FXML
    private void lstAutoresKeyPressed(KeyEvent event) {
        getEditoraSelecionado();
    }

    @FXML
    private void lstAutoresMouseClicked(MouseEvent event) {
        getEditoraSelecionado();
    }

    @FXML
    private void btnNovoAction(ActionEvent event) {
        editora = new Editora();
        limparTela();
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
            editoraDao.excluir(editora);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        preencherLista();
        limparTela();
    }

    @FXML
    private void btnGravarAction(ActionEvent event) {
        editora.setNome(txtNome.getText());

        try {
            if (alteracao) {
                editoraDao.alterar(editora);
            } else {
                editoraDao.gravar(editora);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        habilitarEdicao(false);
        preencherLista();

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
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
    }
}
