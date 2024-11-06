package br.com.alura.literalura.service;

import br.com.alura.literalura.model.DadosLivro;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroRequestAPI {
    @JsonAlias("results")
    List<DadosLivro> resultadoLivros;

    public List<DadosLivro> getResultadoLivros() {
        return resultadoLivros;
    }

    public void setResultadoLivros(List<DadosLivro> resultadoLivros) {
        this.resultadoLivros = resultadoLivros;
    }
}
