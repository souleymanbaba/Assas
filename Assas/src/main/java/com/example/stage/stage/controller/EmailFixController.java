package com.example.stage.stage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email-fix")
@Slf4j
public class EmailFixController {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${app.email.from}")
    private String fromEmail;

    @GetMapping("/test-basic")
    public String testBasicEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo("souleymanbaba94@gmail.com");
            message.setSubject("🔧 Test ESASS - Configuration Email");
            message.setText("Si vous recevez cet email, la configuration Gmail fonctionne parfaitement ! 🎉\n\nTest effectué le: " + new java.util.Date());

            mailSender.send(message);

            log.info("✅ Email de test envoyé avec succès !");
            return "✅ Email de test envoyé avec succès vers votre Gmail !";
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'envoi: ", e);
            return "❌ ERREUR: " + e.getMessage() +
                    "\n\n🔑 Vérifiez que le mot de passe d'application Gmail est correct dans application.properties";
        }
    }

    @GetMapping("/check-config")
    public String checkConfig() {
        boolean passwordLooksValid = password != null && password.length() == 16 && !password.contains(" ");

        return String.format("""
                🔍 Vérification Configuration Email
                
                📧 Username: %s
                🔑 Password: %s**** (longueur: %d caractères)
                📨 From: %s
                ✅ Password valide: %s
                
                %s
                """,
                username,
                password != null && password.length() > 4 ? password.substring(0, 4) : "VIDE",
                password != null ? password.length() : 0,
                fromEmail,
                passwordLooksValid ? "OUI" : "NON - PROBLÈME DÉTECTÉ",
                passwordLooksValid ?
                        "🎉 Configuration semble correcte. Testez avec /test-basic" :
                        "🚨 PROBLÈME: Le mot de passe doit faire 16 caractères SANS ESPACES"
        );
    }
}