package br.com.orbitlink.space.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_REGISTRO_TELEMETRIA")
public class RegistroTelemetria {
    @Id
    @SequenceGenerator(name = "seq_registro_telemetria", sequenceName = "SEQ_REGISTRO_TELEMETRIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_registro_telemetria")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATIVO_ID", nullable = false)
    private AtivoEspacial ativoEspacial;

    @Column(name = "DATA_REGISTRO", nullable = false)
    private LocalDateTime dataRegistro;

    @Column(name = "CLIMA", nullable = false, length = 120)
    private String clima;

    @Column(name = "SINAL", nullable = false, length = 120)
    private String sinal;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "OBSERVACAO_GPS", length = 1000)
    private String observacaoGps;

    public RegistroTelemetria() {
    }

    public RegistroTelemetria(Long id, AtivoEspacial ativoEspacial, LocalDateTime dataRegistro, String clima, String sinal, Double latitude, Double longitude, String observacaoGps) {
        this.id = id;
        this.ativoEspacial = ativoEspacial;
        this.dataRegistro = dataRegistro;
        this.clima = clima;
        this.sinal = sinal;
        this.latitude = latitude;
        this.longitude = longitude;
        this.observacaoGps = observacaoGps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AtivoEspacial getAtivoEspacial() {
        return ativoEspacial;
    }


}