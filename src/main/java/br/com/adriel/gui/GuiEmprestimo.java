package br.com.adriel.gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.adriel.dao.EmprestimoDao;
import br.com.adriel.dao.LeitorDao;
import br.com.adriel.dao.LivroDao;
import br.com.adriel.model.Emprestimo;
import br.com.adriel.model.Exemplar;
import br.com.adriel.model.Leitor;
import br.com.adriel.model.Livro;
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

public class GuiEmprestimo implements Initializable {

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtTelefone;

    @FXML
    private ComboBox<Leitor> cbLeitor;

    @FXML
    private ComboBox<Exemplar> cbExemplar;

    @FXML
    private ComboBox<Livro> cbLivro;

    @FXML
    private ListView<Emprestimo> lstEmprestimos;

    LivroDao livroDao = new LivroDao();
    EmprestimoDao emprestimoDao = new EmprestimoDao();
    LeitorDao leitorDao = new LeitorDao();

    Emprestimo emprestimo = new Emprestimo();

    private void habilitarTela(){
        cbLeitor.setDisable(false);
        cbLivro.setDisable(false);
        cbExemplar.setDisable(false);
        lstEmprestimos.setDisable(true);
    }

    private void desabilitarTela(){
        cbLeitor.setDisable(true);
        cbLivro.setDisable(true);
        cbExemplar.setDisable(true);
    }

    private void preencherLivros(){
        try {
            cbLivro.getItems().clear();
            List<Livro> livros = livroDao.getDados();
            ObservableList<Livro> dados = FXCollections.observableArrayList(livros);
            cbLivro.setItems(dados);
        } catch (Exception e) {

            e.printStackTrace();
        } 
    }


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
        Stage stage = (Stage) cbLeitor.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnCancelarAction(ActionEvent event){

    }

    @FXML
    private void btnConfirmarAction(ActionEvent event) {

    }

    @FXML
    private void btnNovoAction(ActionEvent event) {

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
