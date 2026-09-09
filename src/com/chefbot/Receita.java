package com.chefbot;

public class Receita {

    private final String nome;
    private final String descricao;
    private final String ingredientes;
    private final String modoPreparo;
    private final String justificativa;

    public Receita(
            String nome,
            String descricao,
            String justificativa,
            String ingredientes,
            String modoPreparo) {

        this.nome = nome;
        this.descricao = descricao;
        this.ingredientes = ingredientes;
        this.modoPreparo = modoPreparo;
        this.justificativa = justificativa;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public String getJustificativa() {
        return justificativa;
    }
}