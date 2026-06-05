package br.edu.prog2.bean;

import br.edu.prog2.entity.NumeroAleatorio;
import br.edu.prog2.util.JpaUtil;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;

@Named("numeroAleatorioBean")
@SessionScoped
public class NumeroAleatorioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer valorMaximo;
    private Integer numeroGerado;

    public String gerar() {
        if (valorMaximo == null || valorMaximo < 0) {
            throw new IllegalArgumentException("Informe um valor numerico maior ou igual a zero.");
        }

        numeroGerado = (int) (Math.random() * (valorMaximo + 1));

        NumeroAleatorio registro = new NumeroAleatorio(valorMaximo, numeroGerado, LocalDateTime.now());
        JpaUtil.getEntityManager().persist(registro);

        return "resultado?faces-redirect=true";
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
}
