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
}
