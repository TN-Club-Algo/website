package org.algotn.website.services.email

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service

@Service
class EmailService {

    private var emailSender: JavaMailSender

    init {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = "smtp.gmail.com"
        mailSender.port = 587

        mailSender.username = System.getenv("EMAIL_USERNAME")
        mailSender.password = System.getenv("EMAIL_PASSWORD")

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "false"

        this.emailSender = mailSender
    }

    fun sendPasswordResetEmail(emailTo: String, token: String) {
        val subject = "AlgoTN | Réinitialisation de votre mot de passe."
        val text =
            "Réinitialisez votre mot de passe à l'adresse suivante : https://algo.telecomnancy.net/password-reset/$token\n" +
                    "Ce lien est valide pendant 10 minutes."

        sendEmail(emailTo, subject, text)
    }

    private fun sendEmail(emailTo: String, subject: String, text: String) {
        val mailMessage = SimpleMailMessage()
        mailMessage.from = "club-algo@telecomnancy.net"
        mailMessage.setTo(emailTo)
        mailMessage.subject = subject
        mailMessage.text = text

        emailSender.send(mailMessage)
    }
}