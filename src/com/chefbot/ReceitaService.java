package com.chefbot;

public class ReceitaService {

    public Receita recomendar(Usuario usuario) {

        String comida = Normalizador.normalizar(
                usuario.getComidaFavorita()
        );

        String alergia = Normalizador.normalizar(
                usuario.getAlergia()
        );

        String pais = Normalizador.normalizar(
                usuario.getPais()
        );

        /*
         * REGRA DE SEGURANÇA
         *
         * Restrições alimentares possuem prioridade
         * sobre preferências gastronômicas.
         */

        if (alergia.contains("camarao")
                || alergia.contains("frutos do mar")
                || alergia.contains("fruto do mar")
                || alergia.contains("marisco")) {

            return new Receita(
                    "Massa Mediterrânea de Tomate e Manjericão",

                    "Uma opção sem camarão e sem frutos do mar.",

                    "A sugestão foi escolhida para respeitar "
                            + "a restrição alimentar informada.",

                    """
                    - 200 g de massa
                    - 3 tomates
                    - 2 dentes de alho
                    - Manjericão
                    - Azeite
                    - Sal
                    """,

                    """
                    1. Cozinhe a massa.
                    2. Prepare o molho de tomate com alho e azeite.
                    3. Misture a massa ao molho.
                    4. Finalize com manjericão.
                    """
            );
        }

        /*
         * REGRA DE SEGURANÇA - GLÚTEN
         */

        if (alergia.contains("gluten")) {

            return new Receita(
                    "Risoto de Legumes",

                    "Uma opção naturalmente sem glúten, "
                            + "com legumes frescos.",

                    "A sugestão foi escolhida para evitar "
                            + "ingredientes com glúten.",

                    """
                    - 1 xícara de arroz arbóreo
                    - 1 cenoura
                    - 1 abobrinha
                    - 1 tomate
                    - Caldo de legumes
                    - Azeite
                    - Sal
                    """,

                    """
                    1. Refogue os legumes no azeite.
                    2. Adicione o arroz.
                    3. Acrescente o caldo aos poucos.
                    4. Cozinhe até o arroz ficar cremoso.
                    """
            );
        }

        /*
         * REGRA COMPOSTA
         *
         * Comida favorita + país de interesse
         */

        if (comida.contains("massa")
                && pais.contains("italia")) {

            return new Receita(
                    "Penne Italiano ao Molho de Tomate",

                    "Uma receita clássica italiana "
                            + "para quem aprecia massas.",

                    "Sua preferência por massas combinada "
                            + "com seu interesse pela Itália "
                            + "inspirou esta sugestão.",

                    """
                    - 200 g de penne
                    - 3 tomates maduros
                    - 2 dentes de alho
                    - Folhas de manjericão
                    - 2 colheres de sopa de azeite
                    - Sal a gosto
                    """,

                    """
                    1. Cozinhe o penne até ficar al dente.
                    2. Refogue o alho no azeite.
                    3. Adicione os tomates picados e cozinhe até formar o molho.
                    4. Misture a massa ao molho.
                    5. Finalize com folhas de manjericão.
                    """
            );
        }

        /*
         * REGRA COMPOSTA
         *
         * Carne + Argentina
         */

        if (comida.contains("carne")
                && pais.contains("argentina")) {

            return new Receita(
                    "Bife ao Estilo Argentino",

                    "Uma sugestão inspirada na culinária "
                            + "tradicional da Argentina.",

                    "Sua preferência por carne combinada "
                            + "com seu interesse pela Argentina "
                            + "inspirou esta sugestão.",

                    """
                    - 1 bife
                    - Sal grosso
                    - Pimenta-do-reino
                    - Azeite
                    - Ervas a gosto
                    """,

                    """
                    1. Tempere o bife com sal e pimenta.
                    2. Aqueça bem uma frigideira.
                    3. Grelhe o bife dos dois lados.
                    4. Finalize com ervas e sirva.
                    """
            );
        }

        /*
         * REGRA COMPOSTA
         *
         * Alimentação vegetariana + Itália
         */

        if ((comida.contains("vegetariano")
                || comida.contains("vegetariana")
                || comida.contains("legume"))
                && pais.contains("italia")) {

            return new Receita(
                    "Risoto Italiano de Legumes",

                    "Um risoto cremoso inspirado "
                            + "na culinária italiana.",

                    "Sua preferência por uma alimentação "
                            + "com legumes combinada com seu "
                            + "interesse pela Itália inspirou "
                            + "esta sugestão.",

                    """
                    - 1 xícara de arroz arbóreo
                    - 1 cenoura
                    - 1 abobrinha
                    - 1 tomate
                    - Caldo de legumes
                    - Azeite
                    - Sal
                    """,

                    """
                    1. Refogue os legumes no azeite.
                    2. Adicione o arroz arbóreo.
                    3. Acrescente o caldo aos poucos.
                    4. Mexa até obter uma textura cremosa.
                    """
            );
        }

        /*
         * REGRA COMPOSTA
         *
         * Arroz + Japão
         */

        if (comida.contains("arroz")
                && pais.contains("japao")) {

            return new Receita(
                    "Bowl Japonês de Arroz e Legumes",

                    "Uma combinação leve inspirada "
                            + "na culinária japonesa.",

                    "Sua preferência por arroz combinada "
                            + "com seu interesse pelo Japão "
                            + "inspirou esta sugestão.",

                    """
                    - 1 xícara de arroz
                    - Cenoura
                    - Pepino
                    - Cebolinha
                    - Molho shoyu
                    """,

                    """
                    1. Prepare o arroz.
                    2. Corte os legumes em tiras.
                    3. Monte o bowl com o arroz e os legumes.
                    4. Finalize com shoyu e cebolinha.
                    """
            );
        }

        /*
         * FALLBACK
         *
         * Quando nenhuma combinação específica
         * for encontrada.
         */

        return new Receita(
                "Penne Mediterrâneo",

                "Uma receita simples e versátil "
                        + "com massa, tomate e ervas.",

                "Como não encontrei uma combinação específica "
                        + "para seu perfil, escolhi uma opção "
                        + "mediterrânea simples e saborosa.",

                """
                - 200 g de penne
                - 3 tomates
                - 2 dentes de alho
                - Manjericão
                - Azeite
                - Sal
                """,

                """
                1. Cozinhe o penne.
                2. Prepare o molho com tomate e alho.
                3. Misture a massa ao molho.
                4. Finalize com manjericão.
                """
        );
    }
}