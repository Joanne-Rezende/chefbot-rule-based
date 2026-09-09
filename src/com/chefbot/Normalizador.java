package com.chefbot;

import java.text.Normalizer;

public final class Normalizador {

    private Normalizador() {
        // Impede a criação de objetos dessa classe.
    }

    public static String normalizar(String mensagem) {

        if (mensagem == null) {
            return "";
        }

        String normalizada = mensagem
                .toLowerCase()
                .trim();

        // Remove acentos.
        normalizada = Normalizer
                .normalize(normalizada, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");

        // Remove pontuação e caracteres especiais.
        normalizada = normalizada
                .replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        return normalizada;
    }
}