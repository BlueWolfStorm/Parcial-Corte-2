package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(UserAccount user) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("test@test.com");
        msg.setTo(user.getEmail());
        msg.setSubject("Notificación de Ingreso");
        msg.setText("Hola " + user.getName() + ",\n\nHas ingresado a la plataforma exitosamente.");
        javaMailSender.send(msg);
    }


}
