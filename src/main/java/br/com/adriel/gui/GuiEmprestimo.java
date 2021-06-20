package br.com.adriel.gui;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.adriel.model.Emprestimo;
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

public class GuiEmprestimo implements Initializable {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtTelefone;

    @FXML
    private ComboBox cbExemplar;

    @FXML
    private ComboBox cbLivro;

    @FXML
    private ListView<Emprestimo> lstEmprestimos;

    @FXML
    private void btnRetornarAction(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/GuiAtendente.fxml"));
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

    @FXML
    private void btnConfirmarAction(ActionEvent event) {

    }

    @FXML
    private void btnNovoAction(ActionEvent event) {

    }

    @FXML
    private void btnAtualizarAction(ActionEvent event) {

    }

    @FXML
    private void btnRenovarAction(ActionEvent event) {

    }

    @FXML
    private void btnDevolverAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
