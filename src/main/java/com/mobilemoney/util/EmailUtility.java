package com.mobilemoney.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtility {

    // EMAIL GMAIL
    private static final String FROM_EMAIL =
    "andriatoavimanana@gmail.com";

    // MOT DE PASSE APPLICATION
    private static final String PASSWORD =
    "cwdq anip bzkr qsdg";

    // =========================
    // ENVOYER EMAIL
    // =========================
    public static void sendEmail(
            String toEmail,
            String subject,
            String messageText) {

        try {

            // CONFIG SMTP
            Properties props =
            new Properties();

            props.put(
            "mail.smtp.host",
            "smtp.gmail.com");

            props.put(
            "mail.smtp.port",
            "587");

            props.put(
            "mail.smtp.auth",
            "true");

            props.put(
            "mail.smtp.starttls.enable",
            "true");

            // SESSION
            Session session =
            Session.getInstance(
            props,

            new Authenticator() {

                @Override
                protected PasswordAuthentication
                getPasswordAuthentication() {

                    return new PasswordAuthentication(
                    FROM_EMAIL,
                    PASSWORD);
                }
            });

            // MESSAGE
            Message message =
            new MimeMessage(session);

            message.setFrom(
            new InternetAddress(FROM_EMAIL));

            message.setRecipients(
            Message.RecipientType.TO,

            InternetAddress.parse(toEmail));

            message.setSubject(subject);

            message.setText(messageText);

            // ENVOI
            Transport.send(message);

            System.out.println(
            "Email envoyé à : "
            + toEmail);

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}