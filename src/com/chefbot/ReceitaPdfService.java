package com.chefbot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

public class ReceitaPdfService {

    private static final float MARGEM = 50;
    private static final float LARGURA = 495;

    public String gerarPdf(
            Usuario usuario,
            Receita receita) throws IOException {

        Path pastaReceitas = Path.of("receitas");

        Files.createDirectories(pastaReceitas);

        String nomeArquivo =
                criarNomeArquivo(usuario.getNome());

        Path caminhoPdf =
                pastaReceitas.resolve(nomeArquivo);

        try (PDDocument documento = new PDDocument()) {

            PDPage pagina =
                    new PDPage(PDRectangle.A4);

            documento.addPage(pagina);

            PDType0Font fonte =
                    PDType0Font.load(
                            documento,
                            new File(
                                    "C:\\Windows\\Fonts\\arial.ttf"
                            )
                    );

            PDType0Font fonteNegrito =
                    PDType0Font.load(
                            documento,
                            new File(
                                    "C:\\Windows\\Fonts\\arialbd.ttf"
                            )
                    );

            try (PDPageContentStream conteudo =
                         new PDPageContentStream(
                                 documento,
                                 pagina
                         )) {

                float y = 790;

                // =========================================
                // CABEÇALHO
                // =========================================

                y = desenharCabecalho(
                        conteudo,
                        fonte,
                        fonteNegrito,
                        y
                );

                y -= 30;

                // =========================================
                // PERFIL
                // =========================================

                y = desenharTituloSecao(
                        conteudo,
                        fonteNegrito,
                        "SEU PERFIL GASTRONÔMICO",
                        y
                );

                y = desenharCaixaPerfil(
                        conteudo,
                        fonte,
                        usuario,
                        y
                );

                y -= 28;

                // =========================================
                // RECEITA
                // =========================================

                y = desenharTituloSecao(
                        conteudo,
                        fonteNegrito,
                        "SUA RECEITA PERSONALIZADA",
                        y
                );

                String nomeReceita =
                        corrigirAcentuacao(
                                receita.getNome()
                        );

                y = desenharTexto(
                        conteudo,
                        fonteNegrito,
                        nomeReceita,
                        y,
                        13
                );

                y -= 4;

                String descricao =
                        corrigirAcentuacao(
                                receita.getDescricao()
                        );

                y = desenharParagrafo(
                        conteudo,
                        fonte,
                        descricao,
                        y,
                        10
                );

                y -= 18;

                // =========================================
                // JUSTIFICATIVA
                // =========================================

                y = desenharTituloSecao(
                        conteudo,
                        fonteNegrito,
                        "POR QUE O CHEFBOT ESCOLHEU ESTA RECEITA?",
                        y
                );

                String justificativa =
                        corrigirAcentuacao(
                                receita.getJustificativa()
                        );

                y = desenharParagrafo(
                        conteudo,
                        fonte,
                        justificativa,
                        y,
                        10
                );

                y -= 18;

                // =========================================
                // INGREDIENTES
                // =========================================

                y = desenharTituloSecao(
                        conteudo,
                        fonteNegrito,
                        "INGREDIENTES",
                        y
                );

                String ingredientes =
                        corrigirAcentuacao(
                                receita.getIngredientes()
                        );

                y = desenharLista(
                        conteudo,
                        fonte,
                        ingredientes,
                        y
                );

                y -= 18;

                // =========================================
                // MODO DE PREPARO
                // =========================================

                y = desenharTituloSecao(
                        conteudo,
                        fonteNegrito,
                        "MODO DE PREPARO",
                        y
                );

                String modoPreparo =
                        corrigirAcentuacao(
                                receita.getModoPreparo()
                        );

                y = desenharListaNumerada(
                        conteudo,
                        fonte,
                        modoPreparo,
                        y
                );

                // =========================================
                // RODAPÉ
                // =========================================

                desenharRodape(
                        conteudo,
                        fonte
                );
            }

            documento.save(
                    caminhoPdf.toFile()
            );
        }

        return caminhoPdf.toString();
    }

    // =====================================================
    // CABEÇALHO
    // =====================================================

    private float desenharCabecalho(
            PDPageContentStream conteudo,
            PDType0Font fonte,
            PDType0Font fonteNegrito,
            float y) throws IOException {

        conteudo.beginText();

        conteudo.setFont(
                fonteNegrito,
                24
        );

        conteudo.newLineAtOffset(
                MARGEM,
                y
        );

        conteudo.showText(
                "CHEFBOT"
        );

        conteudo.endText();

        y -= 22;

        conteudo.beginText();

        conteudo.setFont(
                fonte,
                10
        );

        conteudo.newLineAtOffset(
                MARGEM,
                y
        );

        conteudo.showText(
                "FICHA GASTRONÔMICA PERSONALIZADA"
        );

        conteudo.endText();

        y -= 8;

        conteudo.setLineWidth(1.2f);

        conteudo.moveTo(
                MARGEM,
                y
        );

        conteudo.lineTo(
                MARGEM + LARGURA,
                y
        );

        conteudo.stroke();

        return y;
    }

    // =====================================================
    // TÍTULO DE SEÇÃO
    // =====================================================

    private float desenharTituloSecao(
            PDPageContentStream conteudo,
            PDType0Font fonte,
            String texto,
            float y) throws IOException {

        conteudo.beginText();

        conteudo.setFont(
                fonte,
                11
        );

        conteudo.newLineAtOffset(
                MARGEM,
                y
        );

        conteudo.showText(
                texto
        );

        conteudo.endText();

        return y - 18;
    }

    // =====================================================
    // CAIXA DO PERFIL
    // =====================================================

    private float desenharCaixaPerfil(
            PDPageContentStream conteudo,
            PDType0Font fonte,
            Usuario usuario,
            float y) throws IOException {

        float altura = 92;

        conteudo.setLineWidth(0.8f);

        conteudo.addRect(
                MARGEM,
                y - altura + 8,
                LARGURA,
                altura
        );

        conteudo.stroke();

        float textoY =
                y - 12;

        textoY = desenharTextoInterno(
                conteudo,
                fonte,
                "Nome: "
                        + corrigirAcentuacao(
                                usuario.getNome()
                        ),
                textoY
        );

        textoY = desenharTextoInterno(
                conteudo,
                fonte,
                "Idade: "
                        + usuario.getIdade(),
                textoY
        );

        textoY = desenharTextoInterno(
                conteudo,
                fonte,
                "Comida favorita: "
                        + corrigirAcentuacao(
                                usuario.getComidaFavorita()
                        ),
                textoY
        );

        textoY = desenharTextoInterno(
                conteudo,
                fonte,
                "Restrição alimentar: "
                        + corrigirAcentuacao(
                                usuario.getAlergia()
                        ),
                textoY
        );

        desenharTextoInterno(
                conteudo,
                fonte,
                "País de interesse: "
                        + corrigirAcentuacao(
                                usuario.getPais()
                        ),
                textoY
        );

        return y - altura;
    }

    // =====================================================
    // TEXTO INTERNO DO PERFIL
    // =====================================================

    private float desenharTextoInterno(
            PDPageContentStream conteudo,
            PDType0Font fonte,
            String texto,
            float y) throws IOException {

        conteudo.beginText();

        conteudo.setFont(
                fonte,
                9
        );

        conteudo.newLineAtOffset(
                MARGEM + 12,
                y
        );

        conteudo.showText(
                texto
        );

        conteudo.endText();

        return y - 16;
    }

    // =====================================================
    // TEXTO
    // =====================================================

    private float desenharTexto(
            PDPageContentStream conteudo,
            PDType0Font fonte,
            String texto,
            float y,
            float tamanho) throws IOException {

        conteudo.beginText();

        conteudo.setFont(
                fonte,
                tamanho
        );

        conteudo.newLineAtOffset(
                MARGEM,
                y
        );

        conteudo.showText(
                texto
        );

        conteudo.endText();

        return y - 17;
    }

    // =====================================================
    // PARÁGRAFO
    // =====================================================

    private float desenharParagrafo(
            PDPageContentStream conteudo,
            PDType0Font fonte,
            String texto,
            float y,
            float tamanho) throws IOException {

        List<String> linhas =
                quebrarTexto(
                        texto,
                        fonte,
                        tamanho,
                        LARGURA
                );

        for (String linha : linhas) {

            conteudo.beginText();

            conteudo.setFont(
                    fonte,
                    tamanho
            );

            conteudo.newLineAtOffset(
                    MARGEM,
                    y
            );

            conteudo.showText(
                    linha
            );

            conteudo.endText();

            y -= 14;
        }

        return y;
    }

    // =====================================================
    // LISTA DE INGREDIENTES
    // =====================================================

    private float desenharLista(
            PDPageContentStream conteudo,
            PDType0Font fonte,
            String texto,
            float y) throws IOException {

        String[] linhas =
                texto.split("\\R");

        for (String linha : linhas) {

            String linhaLimpa =
                    linha.trim();

            if (linhaLimpa.isEmpty()) {
                continue;
            }

            /*
             * O ReceitaService já fornece os ingredientes
             * começando com "-".
             *
             * Removemos esse hífen antes de desenhar,
             * pois o PDF adicionará apenas um.
             */
            linhaLimpa =
                    linhaLimpa.replaceFirst(
                            "^-+\\s*",
                            ""
                    );

            List<String> linhasQuebradas =
                    quebrarTexto(
                            linhaLimpa,
                            fonte,
                            9,
                            LARGURA - 15
                    );

            for (int i = 0;
                 i < linhasQuebradas.size();
                 i++) {

                String textoLinha =
                        linhasQuebradas.get(i);

                if (i == 0) {

                    textoLinha =
                            "- " + textoLinha;

                } else {

                    textoLinha =
                            "  " + textoLinha;
                }

                conteudo.beginText();

                conteudo.setFont(
                        fonte,
                        9
                );

                conteudo.newLineAtOffset(
                        MARGEM,
                        y
                );

                conteudo.showText(
                        textoLinha
                );

                conteudo.endText();

                y -= 13;
            }
        }

        return y;
    }

    // =====================================================
    // MODO DE PREPARO
    // =====================================================

    private float desenharListaNumerada(
            PDPageContentStream conteudo,
            PDType0Font fonte,
            String texto,
            float y) throws IOException {

        String[] linhas =
                texto.split("\\R");

        int numero = 1;

        for (String linha : linhas) {

            String linhaLimpa =
                    linha.trim();

            if (linhaLimpa.isEmpty()) {
                continue;
            }

            linhaLimpa =
                    linhaLimpa.replaceFirst(
                            "^\\d+\\.\\s*",
                            ""
                    );

            List<String> linhasQuebradas =
                    quebrarTexto(
                            linhaLimpa,
                            fonte,
                            9,
                            LARGURA - 25
                    );

            for (int i = 0;
                 i < linhasQuebradas.size();
                 i++) {

                String textoLinha =
                        linhasQuebradas.get(i);

                if (i == 0) {

                    textoLinha =
                            numero
                                    + ". "
                                    + textoLinha;

                } else {

                    textoLinha =
                            "   "
                                    + textoLinha;
                }

                conteudo.beginText();

                conteudo.setFont(
                        fonte,
                        9
                );

                conteudo.newLineAtOffset(
                        MARGEM,
                        y
                );

                conteudo.showText(
                        textoLinha
                );

                conteudo.endText();

                y -= 13;
            }

            numero++;
        }

        return y;
    }

    // =====================================================
    // RODAPÉ
    // =====================================================

    private void desenharRodape(
            PDPageContentStream conteudo,
            PDType0Font fonte) throws IOException {

        conteudo.beginText();

        conteudo.setFont(
                fonte,
                8
        );

        conteudo.newLineAtOffset(
                MARGEM,
                28
        );

        String data =
                LocalDate.now().format(
                        DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy"
                        )
                );

        conteudo.showText(
                "Gerado pelo ChefBot em "
                        + data
        );

        conteudo.endText();
    }

    // =====================================================
    // QUEBRA DE TEXTO
    // =====================================================

    private List<String> quebrarTexto(
            String texto,
            PDType0Font fonte,
            float tamanho,
            float larguraMaxima)
            throws IOException {

        List<String> linhas =
                new ArrayList<>();

        String[] palavras =
                texto.split("\\s+");

        StringBuilder linhaAtual =
                new StringBuilder();

        for (String palavra : palavras) {

            String tentativa;

            if (linhaAtual.isEmpty()) {

                tentativa =
                        palavra;

            } else {

                tentativa =
                        linhaAtual
                                + " "
                                + palavra;
            }

            float largura =
                    fonte.getStringWidth(
                            tentativa
                    )
                            / 1000
                            * tamanho;

            if (largura > larguraMaxima) {

                if (!linhaAtual.isEmpty()) {

                    linhas.add(
                            linhaAtual.toString()
                    );
                }

                linhaAtual =
                        new StringBuilder(
                                palavra
                        );

            } else {

                linhaAtual =
                        new StringBuilder(
                                tentativa
                        );
            }
        }

        if (!linhaAtual.isEmpty()) {

            linhas.add(
                    linhaAtual.toString()
            );
        }

        return linhas;
    }

    // =====================================================
    // CORREÇÃO DE ACENTUAÇÃO
    // =====================================================

    private String corrigirAcentuacao(
            String texto) {

        if (texto == null) {
            return "";
        }

        String resultado = texto;

        /*
         * IMPORTANTE:
         * As substituições abaixo são feitas apenas
         * quando a palavra está isolada.
         *
         * Isso evita problemas como:
         *
         * Tomate -> Tomaté
         */

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Ii]talia(?!\\p{L})",
                "Itália"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Jj]apao(?!\\p{L})",
                "Japão"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Mm]editerranea(?!\\p{L})",
                "Mediterrânea"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Mm]anjericao(?!\\p{L})",
                "Manjericão"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Cc]amarao(?!\\p{L})",
                "camarão"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Cc]amaroes(?!\\p{L})",
                "camarões"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Oo]pcao(?!\\p{L})",
                "opção"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Oo]pcoes(?!\\p{L})",
                "opções"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Rr]estricao(?!\\p{L})",
                "restrição"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Rr]estricoes(?!\\p{L})",
                "restrições"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Pp]referencia(?!\\p{L})",
                "preferência"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Ss]ugestao(?!\\p{L})",
                "sugestão"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Ss]ugestoes(?!\\p{L})",
                "sugestões"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Cc]ombinacao(?!\\p{L})",
                "combinação"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Cc]ombinacoes(?!\\p{L})",
                "combinações"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Aa]limentacao(?!\\p{L})",
                "alimentação"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Cc]ulinaria(?!\\p{L})",
                "culinária"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Cc]lassica(?!\\p{L})",
                "clássica"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Aa]rboreo(?!\\p{L})",
                "arbóreo"
        );

        /*
         * "até" e "não" precisam ser tratados como palavras
         * isoladas para não alterar palavras como "Tomate".
         */

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Aa]te(?!\\p{L})",
                "até"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Nn]ao(?!\\p{L})",
                "não"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Vv]oce(?!\\p{L})",
                "você"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Tt]ambem(?!\\p{L})",
                "também"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Gg]luten(?!\\p{L})",
                "glúten"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Cc]lassico(?!\\p{L})",
                "clássico"
        );

        resultado = resultado.replaceAll(
                "(?<!\\p{L})[Oo]la(?!\\p{L})",
                "olá"
        );

        return resultado;
    }

    // =====================================================
    // NOME DO ARQUIVO
    // =====================================================

    private String criarNomeArquivo(
            String nome) {

        String nomeNormalizado =
                Normalizer.normalize(
                        nome,
                        Normalizer.Form.NFD
                )
                .replaceAll(
                        "\\p{M}",
                        ""
                );

        nomeNormalizado =
                nomeNormalizado
                        .replaceAll(
                                "[^a-zA-Z0-9]+",
                                "_"
                        )
                        .replaceAll(
                                "^_+|_+$",
                                ""
                        );

        if (nomeNormalizado.isBlank()) {

            nomeNormalizado =
                    "usuario";
        }

        return nomeNormalizado
                .toLowerCase()
                + "_receita.pdf";
    }
}