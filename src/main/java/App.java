import db.CriarDB;



public class App {
    public static void main(String[] args) {
        try {
            CriarDB.criarTabUser();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
