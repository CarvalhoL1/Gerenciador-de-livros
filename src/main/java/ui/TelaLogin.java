package ui;

import service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.SQLException;

public class TelaLogin {
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
    private void fazerLogin(ActionEvent event){
        String email = emailCampo.getText().trim();
        String senha = this.senhaCampo.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            alert("Preencha email e senha.");
            return;
        }

        try {
            Contas.manipularDB.Usuario u = Contas.manipularDB.login(email, senha);

            if (u != null) {
                alert("Bem-vindo, " + u.getNome() + "!");
                Sessao.usuarioLogado = u;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/tela-principal.fxml"));
                    Parent root = loader.load();

                    TelaPrincipal controller = loader.getController();
                    controller.setUsuario(Sessao.usuarioLogado);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert("Email ou senha incorretos.");
            }

        } catch (SQLException ex) {
            alert("Erro no banco: " + ex.getMessage());
        }
    }

    @FXML
    private void abrirCadastro(ActionEvent event){
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(getClass().getResource("/ui/tela-cadastro.fxml"));

            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
