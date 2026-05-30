package br.com.orbitlink.space.config;
import br.com.orbitlink.space.security.Usuario;
import br.com.orbitlink.space.security.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DadosIniciaisConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setLogin("admin@orbitlink.com");
            admin.setSenha(passwordEncoder.encode("123456"));
            usuarioRepository.save(admin);
        }
    }
}