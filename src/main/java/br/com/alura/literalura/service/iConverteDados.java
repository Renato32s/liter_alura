package br.com.alura.literalura.service;

public interface iConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
