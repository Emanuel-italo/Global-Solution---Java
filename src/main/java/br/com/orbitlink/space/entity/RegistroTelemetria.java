package br.com.orbitlink.space.entity;

import br.com.orbitlink.space.core.annotations.ChavePrimaria;
import br.com.orbitlink.space.core.annotations.ColunaMapeada;
import br.com.orbitlink.space.core.annotations.TabelaMapeada;
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
@TabelaMapeada(nome = "TBL_REGISTRO_TELEMETRIA")
public class RegistroTelemetria {

    @Id
    @ChavePrimaria
    @SequenceGenerator(name = "seq_registro_telemetria", sequenceName = "SEQ_REGISTRO_TELEMETRIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_registro_telemetria")
    @ColunaMapeada(nome = "ID")
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ATIVO_ID", nullable = false)
    private AtivoEspacial ativoEspacial;

    @ColunaMapeada(nome = "DATA_REGISTRO")
    @Column(name = "DATA_REGISTRO", nullable = false)
    private LocalDateTime dataRegistro;

    @ColunaMapeada(nome = "CLIMA")
    @Column(name = "CLIMA", nullable = false, length = 120)
    private String clima;

    @ColunaMapeada(nome = "SINAL")
    @Column(name = "SINAL", nullable = false, length = 120)
    private String sinal;

    @ColunaMapeada(nome = "LATITUDE")
    @Column(name = "LATITUDE", nullable = false, precision = 12, scale = 8)
    private Double latitude;

    @ColunaMapeada(nome = "LONGITUDE")
    @Column(name = "LONGITUDE", nullable = false, precision = 12, scale = 8)
    private Double longitude;

    @ColunaMapeada(nome = "OBSERVACAO_GPS")
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

    public void setAtivoEspacial(AtivoEspacial ativoEspacial) {
        this.ativoEspacial = ativoEspacial;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getSinal() {
        return sinal;
    }

    public void setSinal(String sinal) {
        this.sinal = sinal;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getObservacaoGps() {
        return observacaoGps;
    }

    public void setObservacaoGps(String observacaoGps) {
        this.observacaoGps = observacaoGps;
    }
}
