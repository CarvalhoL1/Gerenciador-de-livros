package ui;
import javafx.scene.control.Label;
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

public class TelaPrincipal {
    @FXML
    private Label mensagem;
    public void setUsuario(Contas.manipularDB.Usuario u) {
        mensagem.setText("Bem-vindo, " + u.getNome() + ", o que deseja fazer?");
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
