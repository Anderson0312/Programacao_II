package br.edu.prog2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "numero_aleatorio")
public class NumeroAleatorio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_maximo", nullable = false)
    private Integer valorMaximo;

    @Column(name = "numero_gerado", nullable = false)
    private Integer numeroGerado;

    @Column(name = "data_geracao", nullable = false)
    private LocalDateTime dataGeracao;

    public NumeroAleatorio() {
    }

    public NumeroAleatorio(Integer valorMaximo, Integer numeroGerado, LocalDateTime dataGeracao) {
        this.valorMaximo = valorMaximo;
        this.numeroGerado = numeroGerado;
        this.dataGeracao = dataGeracao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(Integer valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public Integer getNumeroGerado() {
        return numeroGerado;
    }

    public void setNumeroGerado(Integer numeroGerado) {
        this.numeroGerado = numeroGerado;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }
}
