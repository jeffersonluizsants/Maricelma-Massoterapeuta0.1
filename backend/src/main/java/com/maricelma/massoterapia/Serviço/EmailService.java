package com.maricelma.massoterapia.Serviço;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void enviarEmailRecuperacao(String para, String token){
        var mensagem = new SimpleMailMessage();
        mensagem.setTo(para);
        mensagem.setSubject("Recuperação de Senha - Maricelma Massoterapia");
        mensagem.setText("Você solicitou a redefinição de sua senha \n\n" +
                "Utilize o token a seguir para redefinir sua senha: " + token + "\n\n" +
                "Se você não solicitou esta alteração, entre em contato com o suporte");

        mailSender.send(mensagem);
    }
}
