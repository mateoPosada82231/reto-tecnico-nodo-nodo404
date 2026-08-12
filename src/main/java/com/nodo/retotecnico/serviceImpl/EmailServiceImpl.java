package com.nodo.retotecnico.serviceImpl;

import com.nodo.retotecnico.services.EmailService;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

    private final Resend resend;
    private final ResourceLoader resourceLoader;

    public EmailServiceImpl(@Value("${resend.api-key}") String apiKey, ResourceLoader resourceLoader) {
        this.resend = new Resend(apiKey);
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void sendWelcomeEmail(String toEmail, String fullName, String type) {
        try {
            String template = loadTemplate("email-welcome.html");

            boolean isBeta = type.equalsIgnoreCase("BETA");
            LocalDate today = LocalDate.now();

            String subject = isBeta ? "¡Bienvenido al programa Beta Tester!" : "¡Bienvenido a Nodo Store!";
            String subjectTitle = "¡Bienvenido, " + fullName + "!";

            String subjectSubtitle = isBeta
                    ? "Tu cuenta beta tester ha sido creada con éxito."
                    : "Tu cuenta ha sido creada con éxito.";

            String mainMessage = isBeta
                    ? "Estamos encantados de tenerte en <strong style=\"color: #f8fafc;\">Nodo Store</strong> como beta tester. Ahora formas parte de nuestro programa exclusivo donde podrás probar las últimas funcionalidades antes que nadie."
                    : "Estamos encantados de tenerte en <strong style=\"color: #f8fafc;\">Nodo Store</strong>. Ahora puedes explorar todos los productos y servicios disponibles en nuestra plataforma.";

            String secondaryMessage = isBeta
                    ? "Como beta tester, tus comentarios son fundamentales para mejorar la plataforma. Haz clic en el botón de abajo para empezar a explorar."
                    : "Haz clic en el botón de abajo para empezar a descubrir todo lo que tenemos para ti.";

            String html = template
                    .replace("{{subjectTitle}}", subjectTitle)
                    .replace("{{subjectSubtitle}}", subjectSubtitle)
                    .replace("{{fullName}}", fullName)
                    .replace("{{email}}", toEmail)
                    .replace("{{registrationDate}}", today.format(DATE_FORMATTER))
                    .replace("{{mainMessage}}", mainMessage)
                    .replace("{{secondaryMessage}}", secondaryMessage)
                    .replace("{{storeUrl}}", "https://nodo404.vercel.app")
                    .replace("{{supportUrl}}", "https://nodo404.vercel.app")
                    .replace("{{termsUrl}}", "https://nodo404.vercel.app")
                    .replace("{{privacyUrl}}", "https://nodo404.vercel.app")
                    .replace("{{unsubscribeUrl}}", "https://nodo404.vercel.app");

            send(toEmail, subject, html);

        } catch (IOException e) {
            System.err.println("Error al cargar la plantilla de email: " + e.getMessage());
        }
    }

    @Override
    public void sendPasswordChangedEmail(String toEmail, String fullName) {
        try {
            String template = loadTemplate("email-password-changed.html");
            LocalDate today = LocalDate.now();

            String html = template
                    .replace("{{fullName}}", fullName != null ? fullName : toEmail)
                    .replace("{{email}}", toEmail)
                    .replace("{{changeDate}}", today.format(DATE_FORMATTER))
                    .replace("{{storeUrl}}", "https://nodo404.vercel.app")
                    .replace("{{supportUrl}}", "https://nodo404.vercel.app")
                    .replace("{{termsUrl}}", "https://nodo404.vercel.app")
                    .replace("{{privacyUrl}}", "https://nodo404.vercel.app")
                    .replace("{{unsubscribeUrl}}", "https://nodo404.vercel.app");

            send(toEmail, "Tu contraseña ha sido cambiada", html);

        } catch (IOException e) {
            System.err.println("Error al cargar la plantilla de email: " + e.getMessage());
        }
    }

    @Override
    public void sendPurchaseEmail(String toEmail, String fullName, List<String> extensionNames, BigDecimal totalPrice) {
        try {
            String template = loadTemplate("email-purchase.html");
            LocalDate today = LocalDate.now();

            String itemsListHtml = extensionNames.stream()
                    .map(name -> "<li style=\"padding: 4px 0; color: #cbd5e1;\">" + name + "</li>")
                    .collect(Collectors.joining());

            String html = template
                    .replace("{{fullName}}", fullName != null ? fullName : toEmail)
                    .replace("{{email}}", toEmail)
                    .replace("{{purchaseDate}}", today.format(DATE_FORMATTER))
                    .replace("{{itemsListHtml}}", itemsListHtml)
                    .replace("{{totalPrice}}", "$" + totalPrice.toPlainString())
                    .replace("{{storeUrl}}", "https://nodo404.vercel.app")
                    .replace("{{supportUrl}}", "https://nodo404.vercel.app")
                    .replace("{{termsUrl}}", "https://nodo404.vercel.app")
                    .replace("{{privacyUrl}}", "https://nodo404.vercel.app")
                    .replace("{{unsubscribeUrl}}", "https://nodo404.vercel.app");

            send(toEmail, "Tu paquete de expansión ya está disponible", html);

        } catch (IOException e) {
            System.err.println("Error al cargar la plantilla de email: " + e.getMessage());
        }
    }

    private String loadTemplate(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:templates/" + fileName);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    private void send(String toEmail, String subject, String html) {
        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("onboarding@resend.dev")
                    .to(toEmail)
                    .subject(subject)
                    .html(html)
                    .build();

            CreateEmailResponse data = resend.emails().send(params);
            System.out.println("Email sent to " + toEmail + " with id: " + data.getId());
        } catch (ResendException e) {
            System.err.println("Error al enviar email a " + toEmail + ": " + e.getMessage());
        }
    }

    @Override
    public void sendPasswordResetEmail(String toEmail, String fullName, String resetLink) {
        try {
            String template = loadTemplate("email-password-reset.html");   // 1. abre el HTML como texto

            String html = template
                    .replace("{{fullName}}", fullName != null ? fullName : toEmail)  // 2. rellena los huecos
                    .replace("{{email}}", toEmail)
                    .replace("{{resetLink}}", resetLink)
                    .replace("{{supportUrl}}", "https://nodo404.vercel.app")
                    .replace("{{termsUrl}}", "https://nodo404.vercel.app")
                    .replace("{{privacyUrl}}", "https://nodo404.vercel.app")
                    .replace("{{unsubscribeUrl}}", "https://nodo404.vercel.app");

            send(toEmail, "Recupera tu contraseña", html);   // 3. manda el correo ya armado

        } catch (IOException e) {
            System.err.println("Error al cargar la plantilla de email: " + e.getMessage());
        }
    }

    @Override
    public void sendBroadcastEmail(String toEmail, String subject, String body) {
        try {
            String template = loadTemplate("email-broadcast.html");

            String safeSubject = subject != null && !subject.trim().isEmpty() ? subject : "Actualización de Nodo Store";
            String safeBody = body == null ? "" : body
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;")
                    .replace("\n", "<br/>");

            String html = template
                    .replace("{{subjectTitle}}", safeSubject)
                    .replace("{{body}}", safeBody)
                    .replace("{{email}}", toEmail)
                    .replace("{{storeUrl}}", "https://nodo404.vercel.app")
                    .replace("{{supportUrl}}", "https://nodo404.vercel.app")
                    .replace("{{termsUrl}}", "https://nodo404.vercel.app")
                    .replace("{{privacyUrl}}", "https://nodo404.vercel.app")
                    .replace("{{unsubscribeUrl}}", "https://nodo404.vercel.app");

            send(toEmail, safeSubject, html);
        } catch (IOException e) {
            System.err.println("Error al cargar la plantilla de email broadcast: " + e.getMessage());
        }
    }


}