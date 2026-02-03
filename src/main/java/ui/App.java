package ui;

import db.CriarDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        CriarDB.criarTabUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/tela-login.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("Sistema de Cadastro");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}