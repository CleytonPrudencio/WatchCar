package com.system.watchCar.service;

import com.system.watchCar.enums.TipoTemplateEmail;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void enviarEmailComTemplate(String to, TipoTemplateEmail tipo, Map<String, Object> variables) {
        if (variables.containsKey("token")) {
            String token = (String) variables.get("token");
            String url = frontendUrl + "/redefinir-senha?token=" + token;
            variables.put("url", url);
        }
        enviarTemplate(to, tipo.getTitulo(), tipo.getTemplateNome(), variables);
    }

    public void enviarTemplate(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            Context context = new Context();
            context.setVariables(variables);

            String html = templateEngine.process(templateName, context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            //helper.setTo("watchcarsystem@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

