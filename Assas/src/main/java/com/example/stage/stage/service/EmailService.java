package com.example.stage.stage.service;

import com.example.stage.stage.entity.User;
import com.example.stage.stage.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final JwtUtil jwtUtil;

    @Value("${app.base-url:http://localhost:8888}")
    private String baseUrl;

    @Value("${app.client.url:http://localhost:3000}")
    private String clientUrl;

    @Value("${app.email.from:souleymanbaba94@gmail.com}")
    private String fromEmail;

    @Value("${app.email.enabled:false}")
    private boolean emailEnabled;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy à HH:mm");

    public void sendVerificationEmail(User user) {
        if (!emailEnabled) {
            log.warn("⚠ Email désactivé. Email de vérification pour {} ignoré", user.getEmail());
            return;
        }

        try {
            String token = jwtUtil.generateToken(user);
            String verificationUrl = baseUrl + "/api/auth/verify-email?token=" + token;

            sendEmail(user.getEmail(), "🎉 ESASS - Vérification de votre compte",
                    buildVerificationEmailContent(user, verificationUrl));
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'envoi de l'email de vérification à {}: {}", user.getEmail(), e.getMessage(), e);
        }
    }

    public void sendPasswordResetEmail(User user) {
        if (!emailEnabled) {
            log.warn("⚠ Email désactivé. Email de réinitialisation pour {} ignoré", user.getEmail());
            return;
        }

        try {
            String token = jwtUtil.generateToken(user);
            String resetUrl = clientUrl + "/reset-password?token=" + token;

            sendEmail(user.getEmail(), "🔐 ESASS - Réinitialisation de votre mot de passe",
                    buildPasswordResetEmailContent(user, resetUrl));
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'envoi de l'email de réinitialisation à {}: {}", user.getEmail(), e.getMessage(), e);
        }
    }

    public void sendCertificationApprovalEmail(User user, String matricule) {
        if (!emailEnabled) {
            log.warn("⚠ Email désactivé. Email d'approbation pour {} ignoré", user.getEmail());
            return;
        }

        try {
            sendEmail(user.getEmail(), "🎉 ESASS - Certification approuvée",
                    buildCertificationApprovalContent(user, matricule));
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'envoi de l'email d'approbation à {}: {}", user.getEmail(), e.getMessage(), e);
        }
    }

    public void sendCertificationRejectionEmail(User user, String reason) {
        if (!emailEnabled) {
            log.warn("⚠ Email désactivé. Email de refus pour {} ignoré", user.getEmail());
            return;
        }

        try {
            sendEmail(user.getEmail(), "❌ ESASS - Certification refusée",
                    buildCertificationRejectionContent(user, reason));
        } catch (Exception e) {
            log.error("❌ Erreur lors de l'envoi de l'email de refus à {}: {}", user.getEmail(), e.getMessage(), e);
        }
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        log.info("✅ Email [{}] envoyé à: {} depuis: {}", subject, to, fromEmail);
    }

    private String buildVerificationEmailContent(User user, String verificationUrl) {
        String userName = Optional.ofNullable(user.getUsername()).orElse("cher utilisateur");
        return String.format("""
                🎉 Bienvenue sur ESASS !

                Bonjour %s,

                Merci de vous être inscrit(e) sur notre plateforme ESASS (École Supérieure d'Administration et de Sciences Sociales).

                ✅ Pour activer votre compte, veuillez cliquer sur le lien suivant :
                %s

                ⏰ Ce lien expire dans 24 heures pour votre sécurité.

                Si vous n'avez pas créé de compte sur ESASS, ignorez cet email.

                📧 Support : souleymanbaba94@gmail.com
                🌐 Site web : %s

                Cordialement,
                L'équipe ESASS

                ---
                Envoyé le %s
                """, userName, verificationUrl, clientUrl, now());
    }

    private String buildPasswordResetEmailContent(User user, String resetUrl) {
        String userName = Optional.ofNullable(user.getUsername()).orElse("cher utilisateur");
        return String.format("""
                🔐 Réinitialisation de mot de passe

                Bonjour %s,

                Vous avez demandé une réinitialisation de votre mot de passe ESASS.

                🔗 Cliquez sur le lien suivant pour réinitialiser votre mot de passe :
                %s

                ⏰ Ce lien expire dans 1 heure pour votre sécurité.

                Si vous n'avez pas demandé cette réinitialisation, ignorez cet email ou contactez-nous.

                📧 Support : souleymanbaba94@gmail.com

                Cordialement,
                L'équipe ESASS

                ---
                Envoyé le %s
                """, userName, resetUrl, now());
    }

    private String buildCertificationApprovalContent(User user, String matricule) {
        String userName = Optional.ofNullable(user.getUsername()).orElse("cher utilisateur");
        return String.format("""
                🎉 Félicitations ! Certification approuvée

                Bonjour %s,

                Excellente nouvelle ! Votre demande de certification ESASS a été approuvée avec succès.

                ✅ Votre matricule professionnel : %s
                🏆 Statut : Professionnel Certifié ESASS

                Vous bénéficiez maintenant du badge "Certifié" sur votre profil et de tous les avantages associés.

                📧 Support : souleymanbaba94@gmail.com
                🌐 Votre profil : %s/profile

                Félicitations pour ce succès !

                L'équipe ESASS

                ---
                Envoyé le %s
                """, userName, matricule, clientUrl, now());
    }

    private String buildCertificationRejectionContent(User user, String reason) {
        String userName = Optional.ofNullable(user.getUsername()).orElse("cher utilisateur");
        return String.format("""
                ❌ Demande de certification refusée

                Bonjour %s,

                Nous avons examiné votre demande de certification ESASS et malheureusement, nous ne pouvons pas l'approuver dans l'état actuel.

                📋 Raison du refus :
                %s

                💡 Que faire maintenant ?
                • Vérifiez les documents requis
                • Corrigez les informations manquantes ou incorrectes
                • Soumettez une nouvelle demande

                📧 Besoin d'aide ? Contactez-nous : souleymanbaba94@gmail.com
                🌐 Refaire une demande : %s/certification

                Nous restons à votre disposition pour vous accompagner.

                L'équipe ESASS

                ---
                Envoyé le %s
                """, userName,
                Optional.ofNullable(reason).orElse("Documents insuffisants ou invalides"),
                clientUrl, now());
    }

    private String now() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }
}
