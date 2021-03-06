package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.PessoaModel;
import dao.PessoaDAO;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import util.MaskFieldUtil;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class PessoaCadController {

    @FXML
    private TextField txtCPF, txtNome, txtSexo, txtEmail, txtCelular;

    @FXML
    private DatePicker txtDataNasc;

    @FXML
    private JFXButton btnInserir, btnCancelar;

    PessoaModel psm = new PessoaModel();
    PessoaDAO psd = new PessoaDAO();
    private Stage st;
    String flag;

    /*
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFieldUtil.foneField(this.txtCelular);
        MaskFieldUtil.cpfField(this.txtCPF);
    }
    */

    //Funcao chamada no ProdutoController
    public void exibirDados(PessoaModel psm){

        txtCPF.setText(String.valueOf(psm.getCpf()));
        txtNome.setText(String.valueOf(psm.getNome()));
        txtSexo.setText(String.valueOf(psm.getSexo()));
        txtDataNasc.setValue(LocalDate.parse(String.valueOf(psm.getData_nasc())));
        txtEmail.setText(String.valueOf(psm.getEmail()));
        txtCelular.setText(String.valueOf(psm.getCelular()));
    }

    public void cadastrarPessoa(ActionEvent e) throws IOException {

        if(flag.equals("insert")) {

            psm.setCpf(txtCPF.getText());
            psm.setNome(txtNome.getText());
            psm.setSexo(txtSexo.getText());
            psm.setData_nasc(txtDataNasc.getValue().toString());
            psm.setEmail(txtEmail.getText());
            psm.setCelular(txtCelular.getText());
            psd.createPessoa(psm);

            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Mensagem de confirmação: ");
            alert.setContentText("Cliente cadastrado com sucesso!!!");
            alert.showAndWait();

            Stage stageAtual = (Stage) btnCancelar.getScene().getWindow();
            stageAtual.close();
        }

        if(flag.equals("update")) {

            psm.setCpf(txtCPF.getText());
            psm.setNome(txtNome.getText());
            psm.setSexo(txtSexo.getText());
            psm.setData_nasc(txtDataNasc.getValue().toString());
            psm.setEmail(txtEmail.getText());
            psm.setCelular(txtCelular.getText());
            psd.updatePessoa(psm);

            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Mensagem de confirmação: ");
            alert.setContentText("Cliente atualizado com sucesso!!!");
            alert.showAndWait();

            Stage stageAtual = (Stage) btnCancelar.getScene().getWindow();
            stageAtual.close();

        }
    }


    public void setFlag(String flag){
        this.flag = flag;
    }

    public void disableTxtCPF(){
        txtCPF.setEditable(false);
        txtCPF.setDisable(true);
    }

    public void cancelar() {
        Stage stageAtual = (Stage) btnCancelar.getScene().getWindow();
        stageAtual.close();
    }

    public void limparCampos(){
        txtCPF.setText("");
        txtNome.setText("");
        txtSexo.setText("");
        txtDataNasc.setValue(LocalDate.now());
        txtEmail.setText("");
        txtCelular.setText("");
    }

    @FXML
    private void upperText (KeyEvent event) {

        txtNome.textProperty().addListener((ov, oldValue, newValue) -> {
            txtNome.setText(newValue.toUpperCase());
        });
        txtEmail.textProperty().addListener((ov, oldValue, newValue) -> {
            txtEmail.setText(newValue.toUpperCase());
        });
        txtSexo.textProperty().addListener((ov, oldValue, newValue) -> {
            txtSexo.setText(newValue.toUpperCase());
        });
    }

}
