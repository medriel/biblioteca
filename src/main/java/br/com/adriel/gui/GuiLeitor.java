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
    private ListView<Leitor> LstLeitores;
    @FXML
    private ComboBox<String> CboTipo;

    @FXML
    private HBox FrameMatricula;
    @FXML
    private HBox FrameDisciplina;

    @FXML
    private Button BtnNovo;
    @FXML
    private Button BtnAlterar;
    @FXML
    private Button BtnExcluir;
    @FXML
    private Button BtnGravar;
    @FXML
    private Button BtnCancelar;

    @FXML
    private TextField TxtCpf;
    @FXML
    private TextField TxtNome;
    @FXML
    private TextField TxtSubClasse;

    @FXML
    private Label LblSubClasse;

    // Controla se é uma inclusão ou alteração
    private Boolean alteracao;
    private LeitorDao leitorDao = new LeitorDao();
    private Leitor leitor;

    // O autor que está sendo trabalhado

    // Códigos da tela
    private void habilitarEdicao(boolean editar) {
        TxtCpf.setEditable(editar);
        TxtNome.setEditable(editar);
        TxtSubClasse.setEditable(editar);
        CboTipo.setDisable(!editar);

        BtnGravar.setDisable(!editar);
        BtnCancelar.setDisable(!editar);

        BtnNovo.setDisable(editar);
        BtnAlterar.setDisable(editar);
        BtnExcluir.setDisable(editar);

        LstLeitores.setDisable(editar);

    }

    private void preencherLista() {
        try {
            ObservableList<Leitor> leitores = FXCollections.observableArrayList(leitorDao.getDados());
            LstLeitores.setItems(leitores);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void preencherCombo() {
        List<String> opcoes = new ArrayList<String>();
        opcoes.add("Aluno");
        opcoes.add("Professor");
        ObservableList<String> tipos = FXCollections.observableArrayList(opcoes);
        CboTipo.setItems(tipos);
        CboTipo.getSelectionModel().select("Aluno");
    }

    private void mostrarSubClasse() {

        if (CboTipo.getSelectionModel().getSelectedItem() == null)
            return;
        if (CboTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
            LblSubClasse.setText("Matrícula");
        } else {
            LblSubClasse.setText("Disciplina");
        }
    }

    private void exibirDados() {
        leitor = LstLeitores.getSelectionModel().getSelectedItem();

        if (leitor instanceof Aluno) {
            Aluno aluno = (Aluno) leitor;
            TxtCpf.setText(aluno.getCpf());
            TxtNome.setText(aluno.getNome());
            CboTipo.getSelectionModel().select("Aluno");
            LblSubClasse.setText("Matrícula");
            TxtSubClasse.setText(aluno.getMatricula());
        } else {
            Professor professor = (Professor) leitor;
            TxtCpf.setText(professor.getCpf());
            TxtNome.setText(professor.getNome());
            CboTipo.getSelectionModel().select("Professor");
            LblSubClasse.setText("Disciplina");
            TxtSubClasse.setText(professor.getDisciplina());
        }

    }

    public void limparTela() {
        TxtNome.setText("");
        TxtCpf.setText("");
        TxtSubClasse.setText("");
    }

    // Eventos de Tela
    @FXML
    private void LstLeitores_KeyPressed(KeyEvent event) {
        exibirDados();
    }

    @FXML
    private void LstLeitores_MouseClicked(MouseEvent event) {
        exibirDados();
    }

    @FXML
    private void CboTipo_KeyPressed(KeyEvent event) {
        mostrarSubClasse();
    }

    @FXML
    private void CboTipo_MouseClicked(MouseEvent event) {
        mostrarSubClasse();
    }

    @FXML
    private void BtnNovo_Action(ActionEvent event) {
        alteracao = false;
        habilitarEdicao(true);
        limparTela();
    }

    @FXML
    private void BtnAlterar_Action(ActionEvent event) {
        alteracao = true;
        leitor = LstLeitores.getSelectionModel().getSelectedItem();
        habilitarEdicao(true);
        CboTipo.setDisable(true);
    }

    @FXML
    private void BtnExcluir_Action(ActionEvent event) {
        Leitor leitor = LstLeitores.getSelectionModel().getSelectedItem();
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
    private void BtnGravar_Action(ActionEvent event) {
        try {
            if (alteracao) {
                if (CboTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
                    Aluno aluno = (Aluno) leitor;
                    aluno.setMatricula(TxtSubClasse.getText());
                    aluno.setNome(TxtNome.getText());

                    new AlunoDao().alterar(aluno);
                } else {
                    Professor professor = (Professor) leitor;
                    professor.setDisciplina(TxtSubClasse.getText());
                    professor.setNome(TxtNome.getText());

                    new ProfessorDao().alterar(professor);
                }
            } else {
                if (CboTipo.getSelectionModel().getSelectedItem().equals("Aluno")) {
                    Aluno aluno = new Aluno();
                    aluno.setCpf(TxtCpf.getText());
                    aluno.setMatricula(TxtSubClasse.getText());
                    aluno.setNome(TxtNome.getText());
                    new AlunoDao().gravar(aluno);
                } else {
                    Professor professor = new Professor();
                    professor.setCpf(TxtCpf.getText());
                    professor.setDisciplina(TxtSubClasse.getText());
                    professor.setNome(TxtNome.getText());
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
    private void BtnCancelar_Action(ActionEvent event) {
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
        Stage stage = (Stage) BtnNovo.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
        preencherCombo();
    }
}
