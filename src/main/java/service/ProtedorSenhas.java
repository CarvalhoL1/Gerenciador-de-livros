package service;
import org.mindrot.jbcrypt.BCrypt;

public class ProtedorSenhas {
    public static String hashPassword(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public static boolean checkPassword(String senha, String hashed) {
        return BCrypt.checkpw(senha, hashed);
    }
}
