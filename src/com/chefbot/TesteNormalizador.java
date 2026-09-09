package com.chefbot;

public class TesteNormalizador {

    public static void main(String[] args) {

        String[] entradas = {
                "   OLA!!!   ",
                "qual o HORÁRIO?",
                "Qual a LOCALIZAÇÃO?",
                "Quero uma RECOMENDAÇÃO!",
                "  CARDÁPIO  "
        };

        for (String entrada : entradas) {

            String resultado = Normalizador.normalizar(entrada);

            System.out.println("Entrada : [" + entrada + "]");
            System.out.println("Saída   : [" + resultado + "]");
            System.out.println("--------------------------------");
        }
    }
}