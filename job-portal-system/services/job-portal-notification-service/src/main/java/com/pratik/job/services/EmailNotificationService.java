package com.pratik.job.services;

import com.pratik.job.domain.ApplicationStatus;
import com.pratik.job.event.ApplicationStatusChangedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendStatusChangedEmail(ApplicationStatusChangedEvent event) throws Exception {
        try {
            String statusLabel = STATUS_LABELS.getOrDefault(event.getNewStatus(), event.getNewStatus().name());
            String statusColor = STATUS_COLORS.getOrDefault(event.getNewStatus(), "#6b7280");
            String subject = "Application update: " + event.getJobTitle() + " at" + event.getCandidateName();
            String body = buildStatusChangeHtml(event, statusColor, statusLabel);
            String candidateEmail = event.getCandidateEmail();


            sendEmail(candidateEmail, subject, body);

        } catch (Exception e) {
            System.out.println("Email not sent -------- "+ e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


    private void sendEmail(String candidateEmail, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                true, "UTF-8");

        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(candidateEmail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);

        mailSender.send(mimeMessage);
    }

    private String buildStatusChangeHtml(ApplicationStatusChangedEvent event,
                                         String statusColor,
                                         String statusLabel) {


        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Application Status Update</title>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"font-family: Arial, sans-serif; background: #f3f4f6; margin: 0; padding: 20px;\">\n" +
                "\n" +
                "    <div style=\"\n" +
                "        max-width: 600px;\n" +
                "        margin: 0 auto;\n" +
                "        background: #ffffff;\n" +
                "        border-radius: 8px;\n" +
                "        overflow: hidden;\n" +
                "    \">\n" +
                "\n" +
                "        <!-- Header -->\n" +
                "        <div style=\"\n" +
                "            background: " + statusColor + ";\n" +
                "            padding: 24px;\n" +
                "            text-align: center;\n" +
                "        \">\n" +
                "            <h1 style=\"\n" +
                "                color: #ffffff;\n" +
                "                margin: 0;\n" +
                "                font-size: 22px;\n" +
                "            \">\n" +
                "                Application Status Update\n" +
                "            </h1>\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Content -->\n" +
                "        <div style=\"padding: 32px;\">\n" +
                "\n" +
                "            <p style=\"color: #374151; font-size: 16px;\">\n" +
                "                Hi <strong>" + escapeHtml(event.getCandidateName()) + "</strong>,\n" +
                "            </p>\n" +
                "\n" +
                "            <p style=\"color: #6b7280; font-size: 16px;\">\n" +
                "                Your application for\n" +
                "                <strong>" + escapeHtml(event.getJobTitle()) + "</strong>\n" +
                "                at\n" +
                "                <strong> " + escapeHtml(event.getCompanyName()) + "</strong>\n" +
                "                has been updated.\n" +
                "            </p>\n" +
                "\n" +
                "            <!-- Status Card -->\n" +
                "            <div style=\"\n" +
                "                background: #f9fafb;\n" +
                "                border: 1px solid #e5e7eb;\n" +
                "                border-radius: 8px;\n" +
                "                padding: 20px;\n" +
                "                margin: 24px 0;\n" +
                "                text-align: center;\n" +
                "            \">\n" +
                "\n" +
                "                <p style=\"\n" +
                "                    margin: 0 0 8px;\n" +
                "                    color: #9ca3af;\n" +
                "                    font-size: 13px;\n" +
                "                    text-transform: uppercase;\n" +
                "                    letter-spacing: 1px;\n" +
                "                \">\n" +
                "                    Application Status\n" +
                "                </p>\n" +
                "\n" +
                "                <span style=\"\n" +
                "                    display: inline-block;\n" +
                "                    background: " + statusColor + ";\n" +
                "                    color: #ffffff;\n" +
                "                    padding: 8px 20px;\n" +
                "                    border-radius: 20px;\n" +
                "                    font-weight: bold;\n" +
                "                    font-size: 16px;\n" +
                "                \">\n" + escapeHtml(statusLabel) +
                "                </span>\n" +
                "\n" +
                "            </div>\n" +
                "\n" +
                "            <!-- Shortlisted Message -->\n" +
                "            <p style=\"\n" +
                "                color: #374151;\n" +
                "                font-size: 16px;\n" +
                "                line-height: 1.6;\n" +
                "            \">\n" +
                "                Congratulations! Your application has been shortlisted.\n" +
                "            </p>\n" +
                "\n" +
                "            <p style=\"\n" +
                "                color: #9ca3af;\n" +
                "                font-size: 13px;\n" +
                "                margin-top: 24px;\n" +
                "            \">\n" +
                "                You can log in to the Job Portal to view more details about your application.\n" +
                "            </p>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "        <!-- Footer -->\n" +
                "        <div style=\"\n" +
                "            background: #f9fafb;\n" +
                "            padding: 16px;\n" +
                "            text-align: center;\n" +
                "            border-top: 1px solid #e5e7eb;\n" +
                "        \">\n" +
                "\n" +
                "            <p style=\"\n" +
                "                margin: 0;\n" +
                "                color: #9ca3af;\n" +
                "                font-size: 12px;\n" +
                "            \">\n" +
                "                Job Portal &mdash; You are receiving this because you applied for a job through our platform.\n" +
                "            </p>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }
    private String escapeHtml(String text) {
        if(text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static final Map<ApplicationStatus, String> STATUS_LABELS = Map.of(
            ApplicationStatus.PENDING, "Pending Review",
            ApplicationStatus.REVIEWING, "Under Review",
            ApplicationStatus.SHORTLISTED, "Shortlisted",
            ApplicationStatus.INTERVIEW_SCHEDULED, "Interview Scheduled",
            ApplicationStatus.REJECTED, "Not Selected",
            ApplicationStatus.HIRED, "Hired!",
            ApplicationStatus.WITHDRAWN, "Withdrawn"
    );

    private static final Map<ApplicationStatus, String> STATUS_COLORS = Map.of(
            ApplicationStatus.PENDING, "#f59e0b",
            ApplicationStatus.REVIEWING, "#3b82f6",
            ApplicationStatus.SHORTLISTED, "#8b5cf6",
            ApplicationStatus.INTERVIEW_SCHEDULED, "#06b6d4",
            ApplicationStatus.REJECTED, "#ef4444",
            ApplicationStatus.HIRED, "#22c55e",
            ApplicationStatus.WITHDRAWN, "#6b7280"
    );
}
