package br.com.orbitlink.space;

import br.com.orbitlink.space.entity.AlertaOrbital;
import br.com.orbitlink.space.entity.AtivoEspacial;
import br.com.orbitlink.space.entity.ManutencaoOrbital;
import br.com.orbitlink.space.entity.RegistroTelemetria;
import br.com.orbitlink.space.util.MotorReflectionOrbitLink;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication implements CommandLineRunner {

    private final MotorReflectionOrbitLink motorReflectionOrbitLink;

    public ProjectApplication(MotorReflectionOrbitLink motorReflectionOrbitLink) {
        this.motorReflectionOrbitLink = motorReflectionOrbitLink;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Override
    public void run(String... args) {
        motorReflectionOrbitLink.inspecionar(
                AtivoEspacial.class,
                ManutencaoOrbital.class,
                RegistroTelemetria.class,
                AlertaOrbital.class
        );
    }
}
