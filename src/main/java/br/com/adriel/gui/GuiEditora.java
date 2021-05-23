package br.com.adriel.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.adriel.dao.EditoraDao;
import br.com.adriel.dao.Persistencia;
import br.com.adriel.model.Editora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GuiEditora implements Initializable {
    
    @FXML
    private ListView<Editora> LstEditoras;
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
    private TextField TxtId;
    @FXML 
    private TextField TxtNome;

    //Controla se é uma inclusão ou alteração
    private Boolean alteracao;

    //O autor que está sendo trabalhado
    private Editora editora;

    //Objeto de manipulação de dados
    private Persistencia<Editora> editoraDao = new EditoraDao();


    //Códigos da tela
    private void habilitarEdicao(boolean editar) {
        TxtId.setEditable(editar);
        TxtNome.setEditable(editar);

        BtnGravar.setDisable(!editar);
        BtnCancelar.setDisable(!editar);

        BtnNovo.setDisable(editar);
        BtnAlterar.setDisable(editar);
        BtnExcluir.setDisable(editar);

        LstEditoras.setDisable(editar);

        if (editar) {
            TxtNome.requestFocus();
        }
    }

    private void preencherLista() {
        List<Editora> editoras = new ArrayList<Editora>();
        try {
            editoras = editoraDao.getDados();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList<Editora> dados = FXCollections.observableArrayList(editoras);

        
        LstEditoras.setItems(dados);
    }

    private void getEditoraSelecionado() {
        editora = LstEditoras.getSelectionModel().getSelectedItem();
        TxtId.setText(editora.getCodigo().toString());
        TxtNome.setText(editora.getNome());
    }

    //Eventos de Tela
    @FXML
    private void LstAutores_KeyPressed(KeyEvent event) {
        getEditoraSelecionado();
    }

    @FXML
    private void LstAutores_MouseClicked(MouseEvent event) {
        getEditoraSelecionado();
    }

    @FXML
    private void BtnNovo_Action(ActionEvent event) {
        editora = new Editora();
        TxtId.setText("");
        TxtNome.setText("");
        alteracao = false;
        habilitarEdicao(true);
    }
    @FXML
    private void BtnAlterar_Action(ActionEvent event) {
        alteracao = true;
        habilitarEdicao(true);

    }
    @FXML
    private void BtnExcluir_Action(ActionEvent event) {
        try {
            editoraDao.excluir(editora);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        preencherLista();
        
    }
    @FXML
    private void BtnGravar_Action(ActionEvent event) {
        editora.setNome(TxtNome.getText());

        try {
            if (alteracao) {
                editoraDao.alterar(editora);
            } else {
                editoraDao.gravar(editora);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        habilitarEdicao(false);
        preencherLista();

    }
    @FXML
    private void BtnCancelar_Action(ActionEvent event) {
        habilitarEdicao(false);

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherLista();
    }    
}
