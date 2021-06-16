package br.com.adriel.gui;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.adriel.dao.UsuarioDao;
import br.com.adriel.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuiLogin implements Initializable {

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField passSen;

    @FXML
    private void btnEntrarAction(ActionEvent event) {

        try {
            String login = txtLogin.getText();
            String senha = passSen.getText();

            Usuario atual = new Usuario(login, senha);
            Usuario usuario = new UsuarioDao().autenticar(atual);
            Usuario.setLoginAtual(login);

            if (usuario.getCargo() != null) {
                if (usuario.getCargo().equals("Bibliotecario")) {
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/GuiBibliotecario.fxml"));

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add("/styles/Styles.css");

                    Stage stage = new Stage();
                    stage.setTitle("Bem Vindo(a) Bibliotecario(a)");
                    stage.setScene(scene);
                    stage.show();
                }

                if (usuario.getCargo().equals("Atendente")) {
                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/GuiAtendente.fxml"));

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add("/styles/Styles.css");

                    Stage stage = new Stage();
                    stage.setTitle("Bem Vindo(a) Atendente");
                    stage.setScene(scene);
                    stage.show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) txtLogin.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

}
