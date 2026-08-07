package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ContasService;

import java.sql.SQLException;

public class TelaCadastro {
    @FXML
    private TextField nomeCampo;
    @FXML
    private TextField emailCampo;
    @FXML
    private PasswordField senhaCampo;

    private void alert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    @FXML
    private void fazerCadastro(ActionEvent event){
        String nome = nomeCampo.getText().trim();
        String email = emailCampo.getText().trim();
        String senha = this.senhaCampo.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            alert("Preencha email e senha.");
            return;
        }
        if (ContasService.manipularDB.verificarContaExiste(email)){
            alert("Essa conta já existe, use outro email.");
            return;
        }
        try{
            ContasService.manipularDB.add_usuario(nome, email, senha);
            alert("Conta criada com sucesso! voltando a tela de login");
            abrirLogin(event);
        }
        catch (SQLException ex) {
            alert("Erro no banco: " + ex.getMessage());
        }
    }
    @FXML
    private void abrirLogin(ActionEvent event){
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(getClass().getResource("/ui/tela-login.fxml"));

            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}