package com.example.stage.stage.controller;

import com.example.stage.stage.entity.User;
import com.example.stage.stage.repository.UserRepository;
import com.example.stage.stage.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test-email")
@Slf4j
public class EmailTestController {

    private final EmailService emailService;
    private final UserRepository userRepository;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.enabled}")
    private boolean emailEnabled;

    @GetMapping("/send")
    public String sendTestEmail(@RequestParam String email) {
        log.info("🧪 Test d'envoi d'email vers: {}", email);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            log.warn("❌ Utilisateur non trouvé pour: {}", email);
            return "❌ Utilisateur non trouvé pour: " + email;
        }

        try {
            emailService.sendVerificationEmail(user);
            return String.format("✅ Email de vérification envoyé à: %s\n📧 Depuis: %s\n⚙️ Email activé: %s",
                    email, fromEmail, emailEnabled);
        } catch (Exception e) {
            log.error("❌ Erreur lors du test d'email: ", e);
            return "❌ Erreur: " + e.getMessage();
        }
    }

    @GetMapping("/verification")
    public String sendVerificationTest(@RequestParam String email) {
        return sendTestEmail(email);
    }

    @GetMapping("/password-reset")
    public String sendPasswordResetTest(@RequestParam String email) {
        log.info("🧪 Test d'email de réinitialisation vers: {}", email);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "❌ Utilisateur non trouvé pour: " + email;
        }

        try {
            emailService.sendPasswordResetEmail(user);
            return "✅ Email de réinitialisation envoyé à: " + email;
        } catch (Exception e) {
            return "❌ Erreur: " + e.getMessage();
        }
    }

    @GetMapping("/certification-approval")
    public String sendCertificationApprovalTest(@RequestParam String email,
                                                @RequestParam(defaultValue = "MAT123456") String matricule) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "❌ Utilisateur non trouvé pour: " + email;
        }

        try {
            emailService.sendCertificationApprovalEmail(user, matricule);
            return "✅ Email d'approbation de certification envoyé à: " + email;
        } catch (Exception e) {
            return "❌ Erreur: " + e.getMessage();
        }
    }

    @GetMapping("/status")
    public String getEmailStatus() {
        return String.format("""
                📧 Configuration Email ESASS
                
                ✅ Service: %s
                📨 From: %s
                🔧 SMTP: Gmail (smtp.gmail.com:587)
                
                🧪 Tests disponibles:
                • GET /api/test-email/verification?email=test@example.com
                • GET /api/test-email/password-reset?email=test@example.com
                • GET /api/test-email/certification-approval?email=test@example.com&matricule=MAT123
                
                💡 Utilisez l'email exact d'un utilisateur existant dans la base de données.
                """,
                emailEnabled ? "Actif" : "Désactivé",
                fromEmail
        );
    }
}