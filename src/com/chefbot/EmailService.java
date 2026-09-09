package com.chefbot;

import java.io.File;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailService {

    public void enviarComAnexo(
            String destinatario,
            String nomeUsuario,
            String caminhoPdf) throws Exception {

        String remetente = System.getenv("CHEFBOT_EMAIL");
        String senha = System.getenv("CHEFBOT_EMAIL_PASSWORD");

        if (remetente == null || remetente.isBlank()) {
            throw new IllegalStateException(
                    "Variável CHEFBOT_EMAIL não configurada."
            );
        }

        if (senha == null || senha.isBlank()) {
            throw new IllegalStateException(
                    "Variável CHEFBOT_EMAIL_PASSWORD não configurada."
            );
        }

        File arquivoPdf = new File(caminhoPdf);

        if (!arquivoPdf.exists()) {
            throw new IllegalArgumentException(
                    "Arquivo PDF não encontrado: " + caminhoPdf
            );
        }

        Properties propriedades = new Properties();

        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.starttls.enable", "true");
        propriedades.put("mail.smtp.host", "smtp.gmail.com");
        propriedades.put("mail.smtp.port", "587");

        propriedades.put(
                "mail.smtp.connectiontimeout",
                "10000"
        );

        propriedades.put(
                "mail.smtp.timeout",
                "10000"
        );

        propriedades.put(
                "mail.smtp.writetimeout",
                "10000"
        );

        Session sessao = Session.getInstance(
                propriedades,
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication
                            getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                remetente,
                                senha
                        );
                    }
                }
        );

        MimeMessage mensagem =
                new MimeMessage(sessao);

        mensagem.setFrom(
                new InternetAddress(
                        remetente,
                        "ChefBot"
                )
        );

        mensagem.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(
                        destinatario
                )
        );

        mensagem.setSubject(
                "ChefBot | Sua receita personalizada",
                "UTF-8"
        );

        MimeBodyPart texto =
                new MimeBodyPart();

        texto.setText(
                "Olá, " + nomeUsuario + "!\n\n"
                + "Sua receita personalizada foi criada "
                + "pelo ChefBot.\n\n"
                + "O PDF com seu perfil gastronômico, "
                + "ingredientes e modo de preparo está "
                + "anexado a este e-mail.\n\n"
                + "Bom apetite!\n\n"
                + "ChefBot — Assistente Gastronômico",
                "UTF-8"
        );

        MimeBodyPart anexo =
                new MimeBodyPart();

        anexo.attachFile(
                arquivoPdf
        );

        anexo.setFileName(
                "receita-chefbot.pdf"
        );

        MimeMultipart multipart =
                new MimeMultipart();

        multipart.addBodyPart(texto);
        multipart.addBodyPart(anexo);

        mensagem.setContent(
                multipart
        );

        Transport.send(
                mensagem
        );
    }
}