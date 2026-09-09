package com.chefbot;

import java.util.Scanner;

public class Chatbot {

    private final Scanner scanner;
    private final Usuario usuario;
    private final ReceitaService receitaService;

    private EstadoConversa estado;
    private boolean conversaAtiva;

    public Chatbot() {
        scanner = new Scanner(System.in);
        usuario = new Usuario();
        receitaService = new ReceitaService();

        estado = EstadoConversa.NORMAL;
        conversaAtiva = true;
    }

    public void iniciar() {

        exibirCabecalho();

        System.out.println(
                "ChefBot > Olá! Eu sou o ChefBot, seu assistente gastronômico."
        );
        System.out.println(
                "ChefBot > Posso ajudar com cardápio, reservas,"
        );
        System.out.println(
                "          recomendações, receitas e muito mais."
        );
        System.out.println();
        System.out.println(
                "ChefBot > Digite \"ajuda\" para conhecer minhas opções."
        );
        System.out.println(
                "ChefBot > Quando quiser sair, digite \"sair\"."
        );
        System.out.println();

        while (conversaAtiva) {

            System.out.print("Você > ");

            String mensagemOriginal = scanner.nextLine();

            String mensagem =
                    Normalizador.normalizar(mensagemOriginal);

            if (mensagem.isEmpty()) {

                System.out.println(
                        "ChefBot > Digite alguma mensagem para continuarmos."
                );
                System.out.println();

                continue;
            }

            processarMensagem(
                    mensagemOriginal,
                    mensagem
            );
        }

        scanner.close();
    }

    private void processarMensagem(
            String mensagemOriginal,
            String mensagem) {

        /*
         * O comando de encerramento possui prioridade.
         */
        if (mensagem.equals("sair")
                || mensagem.equals("tchau")
                || mensagem.equals("encerrar")) {

            encerrarConversa();
            return;
        }

        /*
         * O estado da conversa possui prioridade
         * sobre as regras gerais.
         */
        if (estado != EstadoConversa.NORMAL) {

            processarEstado(
                    mensagemOriginal,
                    mensagem
            );

            return;
        }

        identificarRegra(mensagem);
    }

    private void identificarRegra(String mensagem) {

        /*
         * REGRA 1 - Saudação
         */
        if (mensagem.contains("oi")
                || mensagem.contains("ola")
                || mensagem.contains("bom dia")
                || mensagem.contains("boa tarde")
                || mensagem.contains("boa noite")) {

            System.out.println(
                    "ChefBot > Olá! Seja muito bem-vindo(a) ao ChefBot."
            );
            System.out.println(
                    "ChefBot > Como posso deixar sua experiência mais saborosa?"
            );

            return;
        }

        /*
         * REGRA 2 - Horário
         */
        if (mensagem.contains("horario")
                || mensagem.contains("funciona")
                || mensagem.contains("abre")
                || mensagem.contains("fecha")) {

            System.out.println(
                    "ChefBot > Nosso restaurante funciona de terça a domingo,"
            );
            System.out.println(
                    "          das 11h às 23h."
            );

            return;
        }

        /*
         * REGRA 3 - Cardápio
         */
        if (mensagem.contains("cardapio")
                || mensagem.contains("menu")
                || mensagem.contains("pratos")) {

            System.out.println(
                    "ChefBot > Nosso cardápio possui massas, carnes,"
            );
            System.out.println(
                    "          pratos vegetarianos, sobremesas e bebidas."
            );

            return;
        }

        /*
         * REGRA COMPOSTA - Reserva de mesa
         *
         * Exige duas condições:
         * "reservar" + "mesa"
         */
        if (mensagem.contains("reservar")
                && mensagem.contains("mesa")) {

            System.out.println(
                    "ChefBot > Perfeito! Você quer reservar uma mesa."
            );
            System.out.println(
                    "ChefBot > Para esta atividade, a reserva é simulada."
            );

            return;
        }

        /*
         * REGRA 4 - Reserva
         */
        if (mensagem.contains("reserva")
                || mensagem.contains("reservar")
                || mensagem.contains("mesa")) {

            System.out.println(
                    "ChefBot > Claro! Trabalhamos com reservas de mesa."
            );
            System.out.println(
                    "ChefBot > Para esta atividade, a reserva é simulada."
            );

            return;
        }

        /*
         * REGRA 5 - Localização
         */
        if (mensagem.contains("endereco")
                || mensagem.contains("localizacao")
                || mensagem.contains("onde fica")
                || mensagem.contains("local")) {

            System.out.println(
                    "ChefBot > Estamos localizados na região central da cidade."
            );
            System.out.println(
                    "ChefBot > Como este é um restaurante fictício, o endereço"
            );
            System.out.println(
                    "          é utilizado apenas para demonstração."
            );

            return;
        }

        /*
         * REGRA 6 - Opções vegetarianas
         */
        if (mensagem.contains("vegetariano")
                || mensagem.contains("vegetariana")
                || mensagem.contains("sem carne")) {

            System.out.println(
                    "ChefBot > Temos opções vegetarianas, sim."
            );
            System.out.println(
                    "ChefBot > Posso sugerir massas, risotos e pratos com legumes."
            );

            return;
        }

        /*
         * REGRA 7 - Sobremesas
         */
        if (mensagem.contains("sobremesa")
                || mensagem.contains("doce")
                || mensagem.contains("bolo")) {

            System.out.println(
                    "ChefBot > Para a sobremesa, temos opções como"
            );
            System.out.println(
                    "          tiramisu, panna cotta e bolo de chocolate."
            );

            return;
        }

        /*
         * REGRA 8 - Bebidas
         */
        if (mensagem.contains("bebida")
                || mensagem.contains("suco")
                || mensagem.contains("refrigerante")) {

            System.out.println(
                    "ChefBot > Temos sucos naturais, refrigerantes"
            );
            System.out.println(
                    "          e outras opções de bebidas."
            );

            return;
        }

        /*
         * REGRA 9 - Alergias
         */
        if (mensagem.contains("alergia")
                || mensagem.contains("alergico")
                || mensagem.contains("alergica")
                || mensagem.contains("restricao")) {

            System.out.println(
                    "ChefBot > Podemos considerar alergias e restrições"
            );
            System.out.println(
                    "          na hora de escolher uma sugestão."
            );

            return;
        }

        /*
         * REGRA 10 - Agradecimento
         */
        if (mensagem.contains("obrigado")
                || mensagem.contains("obrigada")
                || mensagem.contains("valeu")) {

            System.out.println(
                    "ChefBot > Por nada! É sempre um prazer ajudar."
            );

            return;
        }

        /*
         * REGRA 11 - Ajuda
         */
        if (mensagem.contains("ajuda")
                || mensagem.contains("opcoes")
                || mensagem.contains("o que voce faz")) {

            exibirAjuda();

            return;
        }

        /*
         * REGRA 12 - Recomendação / receita personalizada
         */
        if (mensagem.contains("receita")
                || mensagem.contains("recomendacao")
                || mensagem.contains("recomendar")
                || mensagem.contains("sugestao")) {

            iniciarPerfilGastronomico();

            return;
        }

        /*
         * FALLBACK
         */
        exibirFallback();
    }

    private void processarEstado(
            String mensagemOriginal,
            String mensagem) {

        switch (estado) {

            case AGUARDANDO_NOME:

                /*
                 * Preserva o nome original informado pelo usuário.
                 */
                usuario.setNome(
                        mensagemOriginal.trim()
                );

                System.out.println(
                        "ChefBot > Prazer em conhecer você, "
                                + formatarNome(usuario.getNome())
                                + "!"
                );

                System.out.println(
                        "ChefBot > Agora uma curiosidade: qual é a sua idade?"
                );

                estado = EstadoConversa.AGUARDANDO_IDADE;

                break;

            case AGUARDANDO_IDADE:

                try {

                    int idade = Integer.parseInt(mensagem);

                    if (idade <= 0 || idade > 120) {

                        System.out.println(
                                "ChefBot > Essa idade parece inválida."
                        );
                        System.out.println(
                                "ChefBot > Pode informar novamente?"
                        );

                        return;
                    }

                    usuario.setIdade(idade);

                    System.out.println(
                            "ChefBot > Perfeito!"
                    );
                    System.out.println(
                            "ChefBot > Agora me conte: qual comida você"
                    );
                    System.out.println(
                            "          nunca recusaria?"
                    );

                    estado =
                            EstadoConversa.AGUARDANDO_COMIDA;

                } catch (NumberFormatException e) {

                    System.out.println(
                            "ChefBot > Preciso que você informe sua idade"
                    );
                    System.out.println(
                            "          usando apenas números. Exemplo: 22."
                    );
                }

                break;

            case AGUARDANDO_COMIDA:

                /*
                 * Preserva a forma original da comida informada.
                 */
                usuario.setComidaFavorita(
                        mensagemOriginal.trim()
                );

                System.out.println(
                        "ChefBot > Excelente escolha!"
                );
                System.out.println(
                        "ChefBot > Existe alguma alergia ou restrição"
                );
                System.out.println(
                        "          alimentar que eu deva considerar?"
                );

                estado =
                        EstadoConversa.AGUARDANDO_ALERGIA;

                break;

            case AGUARDANDO_ALERGIA:

                /*
                 * Preserva a resposta original.
                 */
                usuario.setAlergia(
                        mensagemOriginal.trim()
                );

                System.out.println(
                        "ChefBot > Anotado!"
                );
                System.out.println(
                        "ChefBot > Agora imagine que você ganhou uma"
                );
                System.out.println(
                        "          passagem para qualquer lugar do mundo."
                );
                System.out.println(
                        "ChefBot > Qual país você gostaria de conhecer?"
                );

                estado =
                        EstadoConversa.AGUARDANDO_PAIS;

                break;

            case AGUARDANDO_PAIS:

                /*
                 * Preserva o nome original do país.
                 */
                usuario.setPais(
                        mensagemOriginal.trim()
                );

                estado =
                        EstadoConversa.NORMAL;

                finalizarPerfilGastronomico();

                break;

            default:

                estado =
                        EstadoConversa.NORMAL;

                break;
        }
    }

    private void iniciarPerfilGastronomico() {

        System.out.println();
        System.out.println(
                "ChefBot > Vamos criar uma sugestão gastronômica personalizada."
        );
        System.out.println(
                "ChefBot > Vou fazer algumas perguntas rápidas."
        );
        System.out.println(
                "ChefBot > Primeiro: como posso chamar você?"
        );

        estado =
                EstadoConversa.AGUARDANDO_NOME;
    }

    private void finalizarPerfilGastronomico() {

        Receita receita =
                receitaService.recomendar(usuario);

        System.out.println();

        System.out.println(
                "+------------------------------------------------+"
        );
        System.out.println(
                "|              SUA RECOMENDAÇÃO                  |"
        );
        System.out.println(
                "+------------------------------------------------+"
        );

        System.out.println();

        System.out.println(
                "ChefBot > Perfil gastronômico registrado."
        );

        System.out.println();

        System.out.println(
                "ChefBot > Nome: "
                        + formatarNome(usuario.getNome())
        );

        System.out.println(
                "ChefBot > Idade: "
                        + usuario.getIdade()
        );

        System.out.println(
                "ChefBot > Comida favorita: "
                        + usuario.getComidaFavorita()
        );

        System.out.println(
                "ChefBot > Restrição: "
                        + usuario.getAlergia()
        );

        System.out.println(
                "ChefBot > País escolhido: "
                        + usuario.getPais()
        );

        System.out.println();

        System.out.println(
                "ChefBot > Minha sugestão para você:"
        );

        System.out.println();

        System.out.println(
                "          " + receita.getNome()
        );

        System.out.println();

        System.out.println(
                "          " + receita.getDescricao()
        );

        System.out.println();

        System.out.println(
                "ChefBot > Por que escolhi esta receita?"
        );

        System.out.println(
                "          " + receita.getJustificativa()
        );

        System.out.println();

        System.out.println(
                "ChefBot > Ingredientes:"
        );

        System.out.println(
                receita.getIngredientes()
        );

        System.out.println(
                "ChefBot > Modo de preparo:"
        );

        System.out.println(
                receita.getModoPreparo()
        );

        System.out.println();

        /*
         * Geração do PDF
         */
        try {

            ReceitaPdfService receitaPdfService =
                    new ReceitaPdfService();

            String caminhoPdf =
                    receitaPdfService.gerarPdf(
                            usuario,
                            receita
                    );

            System.out.println(
                    "ChefBot > PDF personalizado gerado com sucesso!"
            );

            System.out.println(
                    "ChefBot > Arquivo: " + caminhoPdf
            );

            /*
             * Envio automático do PDF por e-mail.
             */
            String destinatario =
                    System.getenv("CHEFBOT_DESTINO");

            if (destinatario == null
                    || destinatario.isBlank()) {

                System.out.println(
                        "ChefBot > E-mail não configurado."
                );

                System.out.println(
                        "ChefBot > O PDF foi salvo normalmente na pasta receitas."
                );

            } else {

                try {

                    EmailService emailService =
                            new EmailService();

                    emailService.enviarComAnexo(
                            destinatario,
                            usuario.getNome(),
                            caminhoPdf
                    );

                    System.out.println(
                            "ChefBot > PDF enviado por e-mail com sucesso!"
                    );

                    System.out.println(
                            "ChefBot > Destinatário: "
                                    + destinatario
                    );

                } catch (Exception e) {

                    /*
                     * Se o e-mail falhar, o PDF continua salvo.
                     */
                    System.out.println(
                            "ChefBot > Não foi possível enviar o e-mail."
                    );

                    System.out.println(
                            "ChefBot > O PDF continua salvo em: "
                                    + caminhoPdf
                    );

                    System.out.println(
                            "ChefBot > Motivo: "
                                    + e.getMessage()
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "ChefBot > A receita foi gerada, "
                            + "mas não consegui criar o PDF."
            );

            System.out.println(
                    "ChefBot > Detalhes: "
                            + e.getMessage()
            );
        }

        System.out.println();
    }

    private void exibirAjuda() {

        System.out.println();

        System.out.println(
                "ChefBot > Posso ajudar com:"
        );

        System.out.println(
                "          • horário de funcionamento"
        );

        System.out.println(
                "          • cardápio"
        );

        System.out.println(
                "          • reservas"
        );

        System.out.println(
                "          • localização"
        );

        System.out.println(
                "          • opções vegetarianas"
        );

        System.out.println(
                "          • sobremesas"
        );

        System.out.println(
                "          • bebidas"
        );

        System.out.println(
                "          • recomendações e receitas"
        );

        System.out.println();
    }

    private void exibirFallback() {

        System.out.println(
                "ChefBot > Ainda não tenho uma regra para essa solicitação."
        );

        System.out.println(
                "ChefBot > Posso ajudar com:"
        );

        System.out.println(
                "          • cardápio"
        );

        System.out.println(
                "          • horários"
        );

        System.out.println(
                "          • reservas"
        );

        System.out.println(
                "          • opções vegetarianas"
        );

        System.out.println(
                "          • receitas personalizadas"
        );

        System.out.println(
                "ChefBot > Digite \"ajuda\" para ver todas as opções."
        );
    }

    private void encerrarConversa() {

        conversaAtiva = false;

        System.out.println();

        System.out.println(
                "ChefBot > Obrigado pela visita!"
        );

        System.out.println(
                "ChefBot > Espero encontrar você novamente."
        );

        System.out.println(
                "ChefBot > Até a próxima!"
        );
    }

    private String formatarNome(String nome) {

        if (nome == null || nome.isBlank()) {
            return "";
        }

        String[] partes =
                nome.trim().split("\\s+");

        StringBuilder resultado =
                new StringBuilder();

        for (String parte : partes) {

            if (parte.isEmpty()) {
                continue;
            }

            String nomeFormatado =
                    parte.substring(0, 1).toUpperCase()
                            + parte.substring(1);

            if (resultado.length() > 0) {
                resultado.append(" ");
            }

            resultado.append(nomeFormatado);
        }

        return resultado.toString();
    }

    private void exibirCabecalho() {

        System.out.println();

        System.out.println(
                "+------------------------------------------------+"
        );

        System.out.println(
                "|                    CHEFBOT                     |"
        );

        System.out.println(
                "|             ASSISTENTE GASTRONÔMICO            |"
        );

        System.out.println(
                "+------------------------------------------------+"
        );

        System.out.println();
    }
}