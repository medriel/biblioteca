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
import javafx.stage.Stage;

public class GuiAtendente implements Initializable {

    
    private void exibirTela(String fxml, String titulo) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/" + fxml +  ".fxml"));
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

    @FXML
    private void btnEmprestimoAction(ActionEvent event) {

        //exibirTela("GuiEmprestimo", "Empréstimo de Livros");

    }
    @FXML
    private void btnLeitoresAction(ActionEvent event) {

        exibirTela("GuiLeitor", "Cadastro de Leitores");
    }
    @FXML
    private void btnDevolverAction(ActionEvent event) {

        //exibirTela("GuiDevolucao", "Devolução de Livros");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
}
