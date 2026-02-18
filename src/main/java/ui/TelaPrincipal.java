package ui;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private void abrirAlterarSenha(ActionEvent event){
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(getClass().getResource("/ui/tela-senha.fxml"));

            javafx.scene.Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void abrirAlterarNome(ActionEvent event){
        try {
            javafx.fxml.FXMLLoader loader =
                    new javafx.fxml.FXMLLoader(getClass().getResource("/ui/tela-nome.fxml"));

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
        TextInputDialog confirmarSenha = new TextInputDialog();
        confirmarSenha.setTitle("Confirme sua senha");
        confirmarSenha.setHeaderText("Digite a senha aqui:");
        confirmarSenha.setContentText("Senha:");
        if (confirmar.get() == ButtonType.OK){
            Optional<String> senhaCampo =confirmarSenha.showAndWait();
            String senha = senhaCampo.get();
            if (Contas.manipularDB.autenticar(senha, u.getId())) {
                try {
                    Contas.manipularDB.deletar_conta(u.getEmail());
                    alert("Usuario deletado com sucesso! voltando a tela de login");
                    abrirLogin(event);
                } catch (SQLException ex) {
                    alert("Falha ao deletar usuario " + ex.getMessage());
                }
            }
            else {
                alert("Senha incorreta!");
            }
        }
    }
    @FXML private TableView<Livros.manipularDB.livro> tabela;
    @FXML private TableColumn<Livros.manipularDB.livro, String> colTitulo;
    @FXML private TableColumn<Livros.manipularDB.livro, String> colDescricao;
    @FXML private TableColumn<Livros.manipularDB.livro, Integer> colPaginas;
    @FXML private TableColumn<Livros.manipularDB.livro, String> colStatus;
    @FXML private TableColumn<Livros.manipularDB.livro, Integer> colPaginaAtual;
    @FXML private TableColumn<Livros.manipularDB.livro, Integer> colProgresso;
    @FXML private TableColumn<Livros.manipularDB.livro, Void> acoes;
    @FXML
    private void initialize(){
        configurarColunaAcoes();
        configurarColunaTitulo();
        configurarColunaDescricao();
        configurarColunaPagTotal();
        configurarColunaStatus();
        configurarColunaProgresso();
        configurarColunaPagAtual();
        carregarTab();
        colTitulo.prefWidthProperty().bind(tabela.widthProperty().multiply(0.16));
        colDescricao.prefWidthProperty().bind(tabela.widthProperty().multiply(0.26));
        colPaginas.prefWidthProperty().bind(tabela.widthProperty().multiply(0.10));
        colPaginaAtual.prefWidthProperty().bind(tabela.widthProperty().multiply(0.10));
        colProgresso.prefWidthProperty().bind(tabela.widthProperty().multiply(0.18));
        colStatus.prefWidthProperty().bind(tabela.widthProperty().multiply(0.10));
        acoes.prefWidthProperty().bind(tabela.widthProperty().multiply(0.09));
    }
    public void configurarColunaTitulo() {
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colTitulo.setCellFactory(column -> new TableCell<Livros.manipularDB.livro, String>() {
            private final TextField campoTitulo = new TextField();
            {
                campoTitulo.setOnAction(e -> salvar());
                campoTitulo.focusedProperty().addListener((obs, oldV, newV) -> {
                    if (!newV && isEditing()) salvar();
                });
            }

            private void salvar() {
                Livros.manipularDB.livro livro = getTableRow().getItem();
                if (livro == null) { cancelEdit(); return; }

                String novo = campoTitulo.getText();

                try {
                    Livros.manipularDB.editarTitulo(livro.getId(), novo);
                    livro.setTitulo(novo);
                    commitEdit(novo);
                } catch (SQLException ex) {
                    alert("Erro ao salvar título: " + ex.getMessage());
                    cancelEdit();
                }
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    if (!isEditing()) {
                        setGraphic(null);
                        setText(item);
                    }
                    else{
                        setGraphic(campoTitulo);
                        setText(null);
                    }
                }
            }
            @Override
            public void startEdit() {
                super.startEdit();
                campoTitulo.setText(getItem());
                setText(null);
                setGraphic(campoTitulo);
                campoTitulo.requestFocus();
                campoTitulo.selectAll();
                campoTitulo.setText(getItem());
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setGraphic(null);
                setText(getItem());
            }
        });
    }
    public void configurarColunaDescricao() {
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colDescricao.setCellFactory(column -> new TableCell<Livros.manipularDB.livro, String>() {
            private final TextField campoDesc = new TextField();
            {
                campoDesc.setOnAction(e -> salvar());
                campoDesc.focusedProperty().addListener((obs, oldV, newV) -> {
                    if (!newV && isEditing()) salvar();
                });
            }

            private void salvar() {
                Livros.manipularDB.livro livro = getTableRow().getItem();
                if (livro == null) { cancelEdit(); return; }

                String novo = campoDesc.getText();

                try {
                    Livros.manipularDB.editarDesricao(livro.getId(), novo);
                    livro.setDescricao(novo);
                    commitEdit(novo);
                } catch (SQLException ex) {
                    alert("Erro ao salvar descrição: " + ex.getMessage());
                    cancelEdit();
                }
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    if (!isEditing()) {
                        setGraphic(null);
                        setText(item);
                    }
                    else{
                        setGraphic(campoDesc);
                        setText(null);
                    }
                }
            }
            @Override
            public void startEdit() {
                super.startEdit();
                campoDesc.setText(getItem());
                setText(null);
                setGraphic(campoDesc);
                campoDesc.requestFocus();
                campoDesc.selectAll();
                campoDesc.setText(getItem());
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setGraphic(null);
                setText(getItem());
            }
        });
    }
    public void configurarColunaPagTotal() {
        colPaginas.setCellValueFactory(new PropertyValueFactory<>("total_pag"));
        colPaginas.setCellFactory(column -> new TableCell<Livros.manipularDB.livro, Integer>() {
            private final TextField campoPaginas = new TextField();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    if (!isEditing()) {
                        setGraphic(null);
                        setText(item == null ? "" : item.toString());
                    }
                    else{
                        setGraphic(campoPaginas);
                        setText(null);
                    }
                }
            }
            @Override
            public void startEdit() {
                Integer valor = getItem();
                super.startEdit();
                campoPaginas.setText(valor == null ? "" : valor.toString());
                setText(null);
                setGraphic(campoPaginas);
                campoPaginas.requestFocus();
                campoPaginas.selectAll();
                campoPaginas.setText(valor == null ? "" : valor.toString());
                campoPaginas.setOnAction(event -> {
                    Livros.manipularDB.livro livro_alterar = getTableRow().getItem();
                    int id = livro_alterar.getId();
                    try {
                        String pg_str = campoPaginas.getText();
                        int pg_total;
                        if(pg_str.isEmpty()){
                            pg_total = 0;
                        }
                        else if (!(Livros.manipularDB.eNumero(pg_str))){
                            alert("Total de paginas invalido!");
                            return;
                        }
                        else {
                            pg_total = Integer.parseInt(pg_str);
                        }

                        Livros.manipularDB.editarPagTotal(id, pg_total);
                        livro_alterar.setTotal_pag(pg_total);
                        commitEdit(pg_total);
                        tabela.refresh();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            public void cancelEdit() {
                Integer valor = getItem();
                super.cancelEdit();
                setGraphic(null);
                setText(valor == null ? "" : valor.toString());
            }
        });
    }
    public void configurarColunaPagAtual() {
        colPaginaAtual.setCellValueFactory(new PropertyValueFactory<>("paginaAtual"));
        colPaginaAtual.setCellFactory(column -> new TableCell<Livros.manipularDB.livro, Integer>() {
            private final TextField campoPaginaAtual = new TextField();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    if (!isEditing()) {
                        setGraphic(null);
                        setText(item == null ? "" : item.toString());
                    }
                    else{
                        setGraphic(campoPaginaAtual);
                        setText(null);
                    }
                }
            }
            @Override
            public void startEdit() {
                Integer valor = getItem();
                super.startEdit();
                campoPaginaAtual.setText(valor == null ? "" : valor.toString());
                setText(null);
                setGraphic(campoPaginaAtual);
                campoPaginaAtual.requestFocus();
                campoPaginaAtual.selectAll();
                campoPaginaAtual.setText(valor == null ? "" : valor.toString());
                campoPaginaAtual.setOnAction(event -> {
                    Livros.manipularDB.livro livro_alterar = getTableRow().getItem();
                    int id = livro_alterar.getId();
                    try {
                        String pg_str = campoPaginaAtual.getText();
                        int pg_atual;
                        if(pg_str.isEmpty()){
                            pg_atual = 0;
                        }
                        else if (!(Livros.manipularDB.eNumero(pg_str))){
                            alert("Total de paginas invalido!");
                            return;
                        }
                        else {
                            pg_atual = Integer.parseInt(pg_str);
                        }

                        Livros.manipularDB.editarPagAtual(id, pg_atual);
                        livro_alterar.setPaginaAtual(pg_atual);

                        commitEdit(pg_atual);
                        tabela.refresh();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            public void cancelEdit() {
                Integer valor = getItem();
                super.cancelEdit();
                setGraphic(null);
                setText(valor == null ? "" : valor.toString());
            }
        });
    }
    public void configurarColunaProgresso(){
        colProgresso.setCellValueFactory(cd -> new ReadOnlyObjectWrapper<>(0));
        colProgresso.setCellFactory(col -> new TableCell<Livros.manipularDB.livro, Integer>() {
            private final Label prog = new Label();
            private final ProgressBar barraProg = new ProgressBar();
            private final HBox caixa = new HBox(10, barraProg, prog);
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }
                Livros.manipularDB.livro livro_progresso = getTableRow().getItem();
                int id = livro_progresso.getId();

                try {
                    double numeroProgresso = Livros.manipularDB.calcularProgresso(id);
                    if (numeroProgresso != 0) {
                        prog.setText(String.format("%.2f%%", numeroProgresso));
                        barraProg.setProgress(numeroProgresso/100);
                    } else {
                        prog.setText("indisponivel");
                        barraProg.setProgress(0);
                    }
                } catch (java.sql.SQLException e) {
                    System.out.print("Erro ");
                    e.printStackTrace();
                }
                setGraphic(caixa);
            }
        });
    }
    public void configurarColunaStatus() {
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setCellFactory(column -> new TableCell<Livros.manipularDB.livro, String>() {
            ObservableList<String> items = FXCollections.observableArrayList("Quero ler", "Lendo", "Pausado", "Lido", "Abandonado");
            private final ComboBox<String> campoStatus = new ComboBox<>(items);

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    if (!isEditing()) {
                        setGraphic(null);
                        String statusTela = exibirStatusNaTela(item);
                        setText(statusTela);
                    }
                    else{
                        setGraphic(campoStatus);
                        setText(null);
                    }
                }
            }

            @Override
            public void startEdit() {
                super.startEdit();
                campoStatus.setValue(getItem());
                setText(null);
                setGraphic(campoStatus);
                campoStatus.requestFocus();
                campoStatus.setValue(getItem());
                campoStatus.setOnAction(event -> {
                    Livros.manipularDB.livro livro_alterar = getTableView().getItems().get(getIndex());
                    int id = livro_alterar.getId();
                    try {
                        String status = campoStatus.getValue();
                        if (status.equals("Quero ler")) Livros.manipularDB.editarStatus(id, "quero_ler");
                        else if (status.equals("Lendo")) Livros.manipularDB.editarStatus(id, "lendo");
                        else if (status.equals("Pausado")) Livros.manipularDB.editarStatus(id, "pausado");
                        else if (status.equals("Lido")) Livros.manipularDB.editarStatus(id, "lido");
                        else if (status.equals("Abandonado")) Livros.manipularDB.editarStatus(id, "abandonado");
                        commitEdit(status);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setGraphic(null);
                setText(getItem());
            }
        });
    }
    public String exibirStatusNaTela (String status){
        return switch (status){
            case "quero_ler" -> "Quero ler";
            case "lendo" -> "Lendo";
            case "pausado" -> "Pausado";
            case "lido" -> "Lido";
            case "abandonado" -> "Abandonado";
            default -> status;
        };
    }
    public void configurarColunaAcoes(){
        acoes.setCellFactory(col -> new TableCell<>() {
            private final Button btnApagar = new Button("Apagar");

            private final HBox caixa = new HBox(10, btnApagar);
            @Override protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                btnApagar.getStyleClass().add("botao-apagar");
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
            tabela.setItems(FXCollections.observableList(Livros.manipularDB.listarMeusLivros()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
