package br.com.adriel.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.adriel.dao.AlunoDao;
import br.com.adriel.dao.LeitorDao;
import br.com.adriel.dao.ProfessorDao;
import br.com.adriel.model.Aluno;
import br.com.adriel.model.Leitor;
import br.com.adriel.model.Professor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GuiLeitor implements Initializable {

    @FXML
    private ListView<Leitor> lstLeitores;
    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private HBox FrameMatricula;
    @FXML
    private HBox FrameDisciplina;

    @FXML
    private Button btnNovo;
    @FXML
    private Button btnAlterar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnGravar;
    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtSubClasse;

    @FXML
    private Label lblSubClasse;

    // Controla se é uma inclusão ou alteração
    private Boolean alteracao;
    private LeitorDao leitorDao = new LeitorDao();
    private Leitor leitor;

    // O autor que está sendo trabalhado

    // Códigos da tela
    private void habilitarEdicao(boolean editar) {
        txtCpf.setEditable(editar);
        txtNome.setEditable(editar);
        txtSubClasse.setEditable(editar);
        cbTipo.setDisable(!editar);

        btnGravar.setDisable(!editar);
        btnCancelar.setDisable(!editar);

        btnNovo.setDisable(editar);
        btnAlterar.setDisable(editar);
        btnExcluir.setDisable(editar);

        lstLeitores.setDisable(editar);

    }

    private void preencherLista() {
        try {
            ObservableList<Leitor> leitores = FXCollections.observableArrayList(leitorDao.getDados());
            lstLeitores.setItems(leitores);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherCombo() {
        List<String> opcoes = new ArrayList<String>();
        opcoes.add("Aluno");
        opcoes.add("Professor");
        ObservableList<String> tipos = FXCollections.observableArrayList(opcoes);
        cbTipo.setItems(tipos);
        cbTipo.getSelectionModel().select("Aluno");
    }

    private void mostrarSubClasse() {

        if (cbTipo.getSelectionModel().getSelectedItem() == null)
            return;
        if (cbTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
            lblSubClasse.setText("Matrícula");
        } else {
            lblSubClasse.setText("Disciplina");
        }
    }

    private void exibirDados() {
        leitor = lstLeitores.getSelectionModel().getSelectedItem();

        if (leitor instanceof Aluno) {
            Aluno aluno = (Aluno) leitor;
            txtCpf.setText(aluno.getCpf());
            txtNome.setText(aluno.getNome());
            cbTipo.getSelectionModel().select("Aluno");
            lblSubClasse.setText("Matrícula");
            txtSubClasse.setText(aluno.getMatricula());
        } else {
            Professor professor = (Professor) leitor;
            txtCpf.setText(professor.getCpf());
            txtNome.setText(professor.getNome());
            cbTipo.getSelectionModel().select("Professor");
            lblSubClasse.setText("Disciplina");
            txtSubClasse.setText(professor.getDisciplina());
        }

    }

    public void limparTela() {
        txtNome.setText("");
        txtCpf.setText("");
        txtSubClasse.setText("");
        cbTipo.getSelectionModel().clearSelection();
    }

    // Eventos de Tela
    @FXML
    private void lstLeitoresKeyPressed(KeyEvent event) {
        exibirDados();
    }

    @FXML
    private void lstLeitoresMouseClicked(MouseEvent event) {
        exibirDados();
    }

    @FXML
    private void cbTipoKeyPressed(KeyEvent event) {
        mostrarSubClasse();
    }

    @FXML
    private void cbTipoMouseClicked(MouseEvent event) {
        mostrarSubClasse();
    }

    @FXML
    private void btnNovoAction(ActionEvent event) {
        limparTela();
        alteracao = false;
        habilitarEdicao(true);
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {
        alteracao = true;
        leitor = lstLeitores.getSelectionModel().getSelectedItem();
        habilitarEdicao(true);
        cbTipo.setDisable(true);
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        Leitor leitor = lstLeitores.getSelectionModel().getSelectedItem();
        try {
            if (leitor instanceof Professor) {
                new ProfessorDao().excluir((Professor) leitor);
            } else {
                new AlunoDao().excluir((Aluno) leitor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        preencherLista();
        limparTela();
    }

    @FXML
    private void btnGravarAction(ActionEvent event) {
        try {
            if (alteracao) {
                if (cbTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
                    Aluno aluno = (Aluno) leitor;
                    aluno.setMatricula(txtSubClasse.getText());
                    aluno.setNome(txtNome.getText());

                    new AlunoDao().alterar(aluno);
                } else {
                    Professor professor = (Professor) leitor;
                    professor.setDisciplina(txtSubClasse.getText());
                    professor.setNome(txtNome.getText());

                    new ProfessorDao().alterar(professor);
                }
            } else {
                if (cbTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
                    Aluno aluno = new Aluno();
                    aluno.setCpf(txtCpf.getText());
                    aluno.setMatricula(txtSubClasse.getText());
                    aluno.setNome(txtNome.getText());
                    new AlunoDao().gravar(aluno);
                } else {
                    Professor professor = new Professor();
                    professor.setCpf(txtCpf.getText());
                    professor.setDisciplina(txtSubClasse.getText());
                    professor.setNome(txtNome.getText());
                    new ProfessorDao().gravar(professor);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        preencherLista();
        habilitarEdicao(false);
        limparTela();
    }

    @FXML
    private void btnCancelarAction(ActionEvent event) {
        habilitarEdicao(false);
        limparTela();

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
        Stage stage = (Stage) btnNovo.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
        preencherCombo();
    }
}
