package br.com.adriel.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.adriel.dao.EmprestimoDao;
import br.com.adriel.model.Emprestimo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class GuiAtrasos implements Initializable {
    @FXML
    private ListView<Emprestimo> lstAtrasos;

    private void preencherLista() {
        try {
            List<Emprestimo> emprestimosAtrasados = new EmprestimoDao().getPendentes();
            ObservableList<Emprestimo> emprestimos = FXCollections.observableArrayList(emprestimosAtrasados);
            lstAtrasos.setItems(emprestimos);
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
        Stage stage = (Stage) lstAtrasos.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherLista();
    }
}
