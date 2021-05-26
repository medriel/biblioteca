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

public class GuiMenu implements Initializable {

    
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
    private void BtnAutores_Action(ActionEvent event) {

        exibirTela("GuiAutor", "Cadastro de Autores");

    }
    @FXML
    private void BtnEditoras_Action(ActionEvent event) {

        exibirTela("GuiEditora", "Cadastro de Editoras");
    }
    @FXML
    private void BtnLivros_Action(ActionEvent event) {

        exibirTela("GuiLivro", "Cadastro de Livros");
    }
    @FXML
    private void BtnLeitores_Action(ActionEvent event) {

        exibirTela("GuiLeitor", "Cadastro de Leitores");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
}
