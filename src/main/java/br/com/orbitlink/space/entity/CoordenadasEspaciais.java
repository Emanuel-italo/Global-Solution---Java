package br.com.orbitlink.space.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable // Componente reutilizável dentro de uma Entidade
@Data
public class CoordenadasEspaciais {
    private Double latitude;
    private Double longitude;
    private Double altitudeEmKm;
}