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
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
            Resource resource = resourceLoader.getResource("classpath:templates/email-welcome.html");
            String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            boolean isBeta = type.equalsIgnoreCase("BETA");
            LocalDate today = LocalDate.now();

            String subject = isBeta
                    ? "¡Bienvenido al programa Beta Tester!"
                    : "¡Bienvenido a Nodo Store!";

            String subjectTitle = isBeta
                    ? "¡Bienvenido, " + fullName + "!"
                    : "¡Bienvenido, " + fullName + "!";

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
                    .replace("{{storeUrl}}", "https://tienda.nodo.com")
                    .replace("{{supportUrl}}", "https://nodo.com/soporte")
                    .replace("{{termsUrl}}", "https://nodo.com/terminos")
                    .replace("{{privacyUrl}}", "https://nodo.com/privacidad")
                    .replace("{{unsubscribeUrl}}", "https://nodo.com/unsuscribe");

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("onboarding@resend.dev")
                    .to(toEmail)
                    .subject(subject)
                    .html(html)
                    .build();

            CreateEmailResponse data = resend.emails().send(params);
            System.out.println("Email sent to " + toEmail + " with id: " + data.getId());

        } catch (IOException e) {
            System.err.println("Error al cargar la plantilla de email: " + e.getMessage());
        } catch (ResendException e) {
            System.err.println("Error al enviar email de bienvenida a " + toEmail + ": " + e.getMessage());
        }
    }
}
