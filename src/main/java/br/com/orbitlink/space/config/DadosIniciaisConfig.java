package br.com.orbitlink.space.config;

import br.com.orbitlink.space.entity.AlocacaoAtivoMissao;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.entity.MissaoEspacial;
import br.com.orbitlink.space.enums.TipoAtivoEnum;
import br.com.orbitlink.space.repository.AlocacaoAtivoMissaoRepository;
import br.com.orbitlink.space.repository.AtivoEspacialRepository;
import br.com.orbitlink.space.repository.MissaoEspacialRepository;
import br.com.orbitlink.space.security.Usuario;
import br.com.orbitlink.space.security.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DadosIniciaisConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AtivoEspacialRepository ativoRepository;

    @Autowired
    private MissaoEspacialRepository missaoRepository;

    @Autowired
    private AlocacaoAtivoMissaoRepository alocacaoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setLogin("admin@orbitlink.com");
            admin.setSenha(passwordEncoder.encode("123456"));
            usuarioRepository.save(admin);
        }

        if (alocacaoRepository.count() == 0) {
            AtivoEspacial ativo = new AtivoEspacial();
            ativo.setNome("Satélite Aurora-1");
            ativo.setTipo(TipoAtivoEnum.SATELITE);
            ativo.setNomeProprietario("Agência OrbitLink");
            ativo.setNumeroDeSerie("SN-DEMO-1");
            ativo = ativoRepository.save(ativo);

            MissaoEspacial missao = new MissaoEspacial();
            missao.setNome("Monitoramento Climático");
            missao.setObjetivo("Coletar dados climáticos para previsão de desastres na Terra");
            missao = missaoRepository.save(missao);

            AlocacaoAtivoMissao alocacao = new AlocacaoAtivoMissao();
            alocacao.setAtivo(ativo);        // o id composto é derivado via @MapsId
            alocacao.setMissao(missao);
            alocacao.setDataAlocacao(LocalDate.now());
            alocacao.setPapel("Sensor primário");
            alocacaoRepository.save(alocacao);
        }
    }
}