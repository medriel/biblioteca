package br.com.adriel.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.adriel.dao.UsuarioDao;
import br.com.adriel.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuiCadastroUsuario implements Initializable {

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;

    @FXML
    private ComboBox<String> cbCargo;

    @FXML
    private ListView<Usuario> lstUsuarios;

    public void preencherCombo() {
        cbCargo.getItems().removeAll(cbCargo.getItems());
        cbCargo.getItems().addAll("", "Bibliotecario", "Atendente");
        cbCargo.getSelectionModel().select("");
    }

    public void limparTela() {
        txtLogin.setText("");
        txtSenha.setText("");
        cbCargo.getSelectionModel().clearSelection();
    }

    private void preencherLista() {
        List<Usuario> usuarios;
        try {
            usuarios = new UsuarioDao().getDados();
            ObservableList<Usuario> data = FXCollections.observableArrayList(usuarios);
            lstUsuarios.setItems(data);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void btnCadastrarAction(ActionEvent event) {
        Usuario user = new Usuario(txtLogin.getText(), txtSenha.getText(),
                (String) cbCargo.getSelectionModel().getSelectedItem());

        try {
            new UsuarioDao().gravar(user);
            limparTela();
            preencherLista();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Stage stage = (Stage) txtLogin.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        preencherLista();
        preencherCombo();
    }

}
