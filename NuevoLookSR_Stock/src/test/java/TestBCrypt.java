
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCrypt {
    public static void main(String[] args) {
        String plainPassword = "abcd"; // Aquí puedes poner cualquier contraseña que quieras encriptar
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(plainPassword);

        System.out.println("Contraseña encriptada: " + encryptedPassword);
    }
}
