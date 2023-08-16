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

        mailSender.username = "duchesnealexandre51200@gmail.com"
        mailSender.password = "bxjdcyeucmmcmwdz"

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "false"

        this.emailSender = mailSender
    }

    fun sendPasswordResetEmail(emailTo: String, token: String) {
        val subject = "AlgoTN | Réinitialisation de votre mot de passe."
        val text = "Réinitialisez votre mot de passe à l'adresse suivante : http://localhost:8080/password-reset/$token\n" +
                "Ce lien est valide pendant 10 minutes."

        sendEmail(emailTo, EmailType.NO_REPLY, subject, text)
    }

    private fun sendEmail(emailTo: String, emailType: EmailType, subject: String, text: String) {
        val mailMessage = SimpleMailMessage()
        mailMessage.from = emailType.email
        mailMessage.setTo(emailTo)
        mailMessage.subject = subject
        mailMessage.text = text

        emailSender.send(mailMessage)
    }

    enum class EmailType(val email: String) {
        NO_REPLY("noreply@limpou.fr")
    }
}