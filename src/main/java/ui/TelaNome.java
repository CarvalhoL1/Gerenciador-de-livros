package ui;


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
import service.Contas;
import service.Contas.*;
import service.Contas.manipularDB.Usuario;
import service.ProtedorSenhas;
import service.Sessao;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class TelaNome {

    @FXML
    private TextField nomeCampo;

    private void alert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void mudarNome(ActionEvent event) throws SQLException, IOException{
        String nome = nomeCampo.getText().trim();
        Usuario u = Sessao.usuarioLogado;
        if (!nome.isEmpty()) {
            Contas.manipularDB.EditarNome(u.getEmail(), nome);
            alert("Nome alterado com sucesso, voltando a tela principal!");
            voltar(event);
        }
        else {
            alert("Preencha o nome!");
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