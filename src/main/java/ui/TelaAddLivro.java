package ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.Livros;
import service.Livros.manipularDB.livro;
import service.Sessao;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;



public class TelaAddLivro {
    @FXML
    private TextField tituloCampo;
    @FXML
    private TextField descCampo;
    @FXML
    private TextField pagCampo;

    private void alert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    @FXML
    private void fazerCadastro(ActionEvent event){
        String titulo = tituloCampo.getText().trim();
        String descricao = descCampo.getText().trim();
        String pag_str = pagCampo.getText();
        int paginas;

        if(pag_str.isEmpty()){
            paginas = 0;
        }
        else if (!(Livros.manipularDB.eNumero(pag_str))){
            alert("Total de paginas invalido!");
            return;
        }
        else {
            paginas = Integer.parseInt(pag_str);
        }
        if (titulo.isEmpty()) {
            alert("Preencha o titulo.");
            return;
        }
        try{
            if(descricao.isEmpty()) {
                    service.Livros.manipularDB.add_livro(titulo, "", paginas);

            }
            else{
                service.Livros.manipularDB.add_livro(titulo, descricao, paginas);
            }
            alert("Livro adicionado com sucesso!");
        }
        catch (SQLException ex) {
            alert("Erro no banco: " + ex.getMessage());
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