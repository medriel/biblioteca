package br.com.adriel.gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.adriel.dao.EmprestimoDao;
import br.com.adriel.dao.LeitorDao;
import br.com.adriel.dao.LivroDao;
import br.com.adriel.model.Emprestimo;
import br.com.adriel.model.Status;
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

    private void habilitarTela() {
        cbLeitor.setDisable(false);
        cbLivro.setDisable(false);
        cbExemplar.setDisable(false);
        lstEmprestimos.setDisable(true);

        preencherLivros();
        preencherLeitor();
    }

    private void desabilitarTela() {
        cbLeitor.setDisable(true);
        cbLivro.setDisable(true);
        cbExemplar.setDisable(true);
    }

    private void preencherLivros() {
        try {
            cbLivro.getItems().clear();
            List<Livro> livros = livroDao.getDados();
            ObservableList<Livro> dados = FXCollections.observableArrayList(livros);
            cbLivro.setItems(dados);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void preencherLista() {
        try {
            ObservableList<Emprestimo> emprestimos = FXCollections.observableArrayList(emprestimoDao.getDisponiveis());
            lstEmprestimos.setItems(emprestimos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherLeitor() {
        try {
            cbLeitor.getItems().clear();
            List<Leitor> leitores = leitorDao.getDados();
            ObservableList<Leitor> dados = FXCollections.observableArrayList(leitores);
            cbLeitor.setItems(dados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherExemplar() {
        try {
            cbExemplar.getItems().clear();
            Livro livroSelecionado = cbLivro.getSelectionModel().getSelectedItem();
            List<Exemplar> exemplaresDisponiveis = new ArrayList<Exemplar>();

            for (Exemplar exemplar : livroSelecionado.getExemplares()) {
                if (exemplar.getStatus().equals(Status.disponivel)) {
                    exemplaresDisponiveis.add(exemplar);
                }
            }

            ObservableList<Exemplar> dado = FXCollections.observableArrayList(exemplaresDisponiveis);
            cbExemplar.setItems(dado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cbLivroAction(ActionEvent event) {
        preencherExemplar();
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
    private void btnConfirmarAction(ActionEvent event) {
        try {
            Leitor leitor = cbLeitor.getSelectionModel().getSelectedItem();
            Exemplar exemplar = cbExemplar.getSelectionModel().getSelectedItem();

            exemplar.setStatus(Status.emprestado);

            emprestimo.setLeitor(leitor);
            emprestimo.setDataEmprestimo(LocalDate.now());
            emprestimo.setExemplar(exemplar);
            emprestimo.setCodigo(exemplar.getCodigo());
            emprestimo.setEndereco(txtEndereco.getText());
            emprestimo.setTelefone(txtTelefone.getText());

            emprestimoDao.gravar(emprestimo);

            desabilitarTela();
            lstEmprestimos.setDisable(false);
            preencherLista();

            txtEndereco.setText("");
            txtTelefone.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnNovoAction(ActionEvent event) {
        habilitarTela();
    }

    @FXML
    private void btnRemoverAction(ActionEvent event) {
        try {
            Emprestimo emprestimoSelecionado = lstEmprestimos.getSelectionModel().getSelectedItem();
            emprestimoDao.excluir(emprestimoSelecionado);
            preencherLista();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnDevolverAction(ActionEvent event) {
        try {
            Emprestimo emprestimoSelecionado = lstEmprestimos.getSelectionModel().getSelectedItem();
            emprestimoSelecionado.setDataDevolucao(LocalDate.now());
            emprestimoDao.alterar(emprestimoSelecionado);
            preencherLista();
            lstEmprestimos.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        desabilitarTela();
        preencherLista();
    }

}
