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
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class GuiCadastroUsuario implements Initializable{

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;

    @FXML
    private ComboBox cbCargo;

    @FXML
    private ListView<Usuario> lstUsuarios;

    @FXML
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
        Usuario user = new Usuario(txtLogin.getText(), txtSenha.getText(), (String) cbCargo.getSelectionModel().getSelectedItem());

        try {
            new UsuarioDao().gravar(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnRetornarAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        preencherLista();
        cbCargo.getItems().removeAll(cbCargo.getItems());
        cbCargo.getItems().addAll("Bibliotecário", "Atendente");
        cbCargo.getSelectionModel().select("Bibliotecário");
    }


    
}
