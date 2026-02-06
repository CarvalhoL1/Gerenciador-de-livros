package ui;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.HBox;
import service.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.Optional;

public class TelaPrincipal {
    @FXML
    private Label mensagem;
    public void setUsuario(Contas.manipularDB.Usuario u) {
        mensagem.setText("Bem-vindo, " + u.getNome() + ", o que deseja fazer?");
    }
    private void alert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensagem);
        alert.showAndWait();
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
    @FXML
    private void abrirAddLivro(ActionEvent event){
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(getClass().getResource("/ui/tela-addlivro.fxml"));

            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void apagarConta(ActionEvent event){
        Contas.manipularDB.Usuario u = Sessao.usuarioLogado;
        Alert aviso = new Alert(Alert.AlertType.CONFIRMATION);
        aviso.setContentText("Tem certeza? isso apagara sua conta para sempre");
        Optional<ButtonType> confirmar = aviso.showAndWait();
        if (confirmar.get() == ButtonType.OK){
            try {
                Contas.manipularDB.deletar_conta(u.getEmail());
                alert("Usuario deletado com sucesso! voltando a tela de login");
                abrirLogin(event);
            }catch (SQLException ex){
                alert("Falha ao deletar usuario " + ex.getMessage());
            }
        }
    }
    @FXML private TableView<Livros.manipularDB.livro> tabela;
    @FXML private TableColumn<Livros.manipularDB.livro, String> colTitulo;
    @FXML private TableColumn<Livros.manipularDB.livro, String> colDescricao;
    @FXML private TableColumn<Livros.manipularDB.livro, Integer> colPaginas;
    @FXML private TableColumn<Livros.manipularDB.livro, String> colStatus;
    @FXML private TableColumn<Livros.manipularDB.livro, Integer> colPaginaAtual;
    @FXML private TableColumn<Livros.manipularDB.livro, Void> acoes;
    @FXML
    private void initialize(){
        configurarColunaAcoes();
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colPaginas.setCellValueFactory(new PropertyValueFactory<>("total_pag"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPaginaAtual.setCellValueFactory(new PropertyValueFactory<>("paginaAtual"));
        carregarTab();

    }

    public void configurarColunaAcoes(){
        acoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnApagar = new Button("Apagar");
            private final HBox caixa = new HBox(10, btnEditar, btnApagar);
            @Override protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                if (empty){
                    setGraphic(null);
                    setText(null);
                }
                else {
                    setGraphic(caixa);
                    setText(null);
                }
                btnApagar.setOnAction(event -> {
                    Livros.manipularDB.livro livro_apagar = getTableView().getItems().get(getIndex());
                    int id = livro_apagar.getId();
                    try {
                        Livros.manipularDB.deletar_livro(id);
                        getTableView().getItems().remove(livro_apagar);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    private void carregarTab(){

        try {
            tabela.setItems(FXCollections.observableList(Livros.listarMeusLivros()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
