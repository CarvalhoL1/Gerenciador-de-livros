package ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import service.Contas;
import service.Contas.*;
import service.Contas.manipularDB.Usuario;
import service.Sessao;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class TelaSenha {
    @FXML
    private PasswordField senhaAntigaCampo;
    @FXML
    private PasswordField senhaNovaCampo;

    private void alert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void mudarSenha(ActionEvent event) throws SQLException, IOException{
        String senhaAntiga = senhaAntigaCampo.getText().trim();
        String senhaNova = senhaNovaCampo.getText().trim();
        Usuario u = Sessao.usuarioLogado;
        if (Contas.manipularDB.autenticar(senhaAntiga, u.getId()) && !senhaNova.isEmpty()) {
            Contas.manipularDB.EditarSenha(u.getEmail(), senhaNova);
            alert("Senha alterada com sucesso, voltando a tela principal!");
            voltar(event);
        }
        else if (senhaNova.isEmpty()){
            alert("Digite a nova senha!");
        }
        else {
            alert("Senha antiga incorreta!");
        }
    }

    @FXML
    private void voltar(ActionEvent event) throws IOException {
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(getClass().getResource("/ui/tela-principal.fxml"));

            javafx.scene.Parent root = loader.load();
            TelaPrincipal controller = loader.getController();
            controller.setUsuario(Sessao.usuarioLogado);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}