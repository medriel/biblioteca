package br.com.adriel.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GuiBibliotecario implements Initializable {

    @FXML
    private Button btnLivros;

    private void exibirTela(String fxml, String titulo) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/" + fxml + ".fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fecharTela() {
        Stage stage = (Stage) btnLivros.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnAutoresAction(ActionEvent event) {

        exibirTela("GuiAutor", "Cadastro de Autores");
        fecharTela();
    }

    @FXML
    private void btnUsuarioAction(ActionEvent event) {

        exibirTela("GuiCadastroUsuario", "Cadastro de Usuarios");
        fecharTela();
    }

    @FXML
    private void btnLivrosAction(ActionEvent event) {

        exibirTela("GuiLivro", "Cadastro de Livros");
        fecharTela();
    }

    @FXML
    private void btnAtrasosAction(ActionEvent event) {

        // exibirTela("GuiAtrasos", "Consulta de Emprestimos Atrasados");
        // fecharTela();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}