package org.algotn.website.services.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService {

    @Autowired
    private lateinit var emailSender: JavaMailSender

    fun sendPasswordResetEmail(emailTo: String) {
        val subject = ""
        val text = ""

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