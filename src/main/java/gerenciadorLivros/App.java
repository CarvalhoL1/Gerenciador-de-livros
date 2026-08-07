package gerenciadorLivros;

import gerenciadorLivros.db.Conectar;
import gerenciadorLivros.db.CriarDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        try {
            Conectar.getConnection();
            CriarDB.criarTabLivros();
            CriarDB.criarTabUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(App.class, args);
    }
}