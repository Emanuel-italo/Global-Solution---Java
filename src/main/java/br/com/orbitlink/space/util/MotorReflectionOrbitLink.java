package br.com.orbitlink.space.util;

import br.com.orbitlink.space.core.annotations.ChavePrimaria;
import br.com.orbitlink.space.core.annotations.ColunaMapeada;
import br.com.orbitlink.space.core.annotations.TabelaMapeada;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MotorReflectionOrbitLink {

    public void inspecionar(Class<?>... entidades) {
        System.out.println("==================================================");
        System.out.println("MOTOR DE REFLECTION ORBITLINK - INICIALIZAÇÃO");
        System.out.println("==================================================");

        List<Class<?>> listaEntidades = Arrays.asList(entidades);

        for (Class<?> entidade : listaEntidades) {
            System.out.println("\nEntidade: " + entidade.getSimpleName());

            TabelaMapeada tabelaMapeada = entidade.getAnnotation(TabelaMapeada.class);
            if (tabelaMapeada != null) {
                System.out.println("  Tabela mapeada: " + tabelaMapeada.nome());
            } else {
                System.out.println("  [AVISO] Entidade sem @TabelaMapeada");
            }

            boolean possuiChave = false;

            for (Field field : entidade.getDeclaredFields()) {
                if (field.isAnnotationPresent(ChavePrimaria.class)) {
                    possuiChave = true;
                    System.out.println("  Chave primária: " + field.getName());
                }

                ColunaMapeada colunaMapeada = field.getAnnotation(ColunaMapeada.class);
                if (colunaMapeada != null) {
                    System.out.println("  Coluna: " + field.getName() + " -> " + colunaMapeada.nome());
                }
            }

            if (!possuiChave) {
                System.out.println("  [AVISO] Nenhum campo anotado com @ChavePrimaria");
            }
        }

        System.out.println("\n==================================================");
        System.out.println("REFLECTION CONCLUÍDA COM SUCESSO");
        System.out.println("==================================================");
    }
}
