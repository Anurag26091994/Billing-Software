package com.custom.Billing.Software.util;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${app.mail.from-name}")
    private String fromName;

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to);
            helper.setFrom(String.format("%s <%s>", fromName, emailFrom));
            helper.setSubject(subject);
            helper.setText(body, true);

            emailSender.send(mimeMessage);
            FormLogger.info("Email sent successfully to ", to);
        } catch (MessagingException e) {
            FormLogger.error("Error sending email: ", e.getMessage());
        }
    }

    public void sendEmailWithAttachment(String to, String subject, String body, String filename, byte[] attachment) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.addAttachment(filename, new ByteArrayDataSource(attachment, "application/pdf"));
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendWelcomeWithOtpEmail(String to, String name, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Shakti Bazaar - OTP Verification");
        message.setFrom(String.format("%s <%s>", fromName, emailFrom));
        message.setText(
                "Dear " + name + ",\n\n" +
                        "Welcome to Shakti Bazaar! We're excited to have you on board.\n\n" +
                        "To complete your registration, please verify your email address using the OTP below:\n\n" +
                        "🔐 Your OTP: " + otp + "\n\n" +
                        "This OTP is valid for 10 minutes.\n\n" +
                        "If you did not sign up, please ignore this email.\n\n" +
                        "Best Regards,\n" +
                        "The Shakti Bazaar Team"
        );
        emailSender.send(message);
        FormLogger.info("Welcome email with OTP sent to: ", message);
    }

    public void sendEmailConfirmation(String email, String customerName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(String.format("%s <%s>", fromName, emailFrom));
        message.setSubject("Email Verified - Welcome to Shakti Bazaar");

        String body = "Dear " + customerName + ",\n\n"
                + "✅ Your email has been successfully verified!\n\n"
                + "Your Shakti Bazaar account is now active and ready to use.\n\n"
                + "You can now log in and start buying and selling your products and more.\n\n"
                + "If you have any questions or need support, feel free to contact us.\n\n"
                + "Thanks for verifying your email, and welcome aboard!\n\n"
                + "Warm regards,\n"
                + "The FinReport Builder Team";

        message.setText(body);
        emailSender.send(message);
        FormLogger.info("Welcome email with OTP sent to: ", message);
    }

    public void sendDeactivationEmail(String email, String customerName) {
        String subject = "Your Account Has Been Deleted - Shakti Bazaar";
        String message = "Dear " + customerName + ",\n\n" +
                "We wanted to inform you that your account associated with this email (" + email + ") has been successfully deleted from our system.\n\n" +
                "If this was a mistake or you wish to reactivate your account, please contact our support team.\n\n" +
                "Thank you for using Shakti Bazaar.\n\n" +
                "Regards,\n" +
                "The Shakti Bazaar Team";

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom(String.format("%s <%s>", fromName, emailFrom));

            emailSender.send(mailMessage);
            FormLogger.info("Deactivation email sent to: {}", email);
        } catch (Exception e) {
            FormLogger.error("Failed to send deactivation email to ", email, e.getMessage());
        }
    }

    public void sendPasswordResetEmail(String email, String customerName, String otp) {
        String subject = "Password Reset Request - Shakti Bazaar";
        String message = "Dear " + customerName + ",\n\n" +
                "We received a request to reset your password for your Shakti Bazaar account associated with this email (" + email + ").\n\n" +
                "Your OTP for password reset is: " + otp + "\n\n" +
                "Please use this OTP to reset your password. If you did not request this, please ignore this email.\n\n" +
                "Regards,\n" +
                "The Shakti Bazaar Team";

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom(String.format("%s <%s>", fromName, emailFrom));

            emailSender.send(mailMessage);
            FormLogger.info("Password reset email sent to: ", email);
        } catch (Exception e) {
            FormLogger.error("Failed to send password reset email to ", email, e.getMessage());
        }
    }

    public void sendEmailOTPReverification(String email, String customerName, String otp) {
        String subject = "Email Re-verification - Report Builder";
        String message = "Dear " + customerName + ",\n\n" +
                "We noticed that you recently updated your email address in your Shakti Bazaar account.\n\n" +
                "To complete the re-verification process, please use the following OTP: " + otp + "\n\n" +
                "If you did not request this change, please contact our support team immediately.\n\n" +
                "Regards,\n" +
                "The Shakti Bazaar Team";

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom(String.format("%s <%s>", fromName, emailFrom));

            emailSender.send(mailMessage);
            FormLogger.info("Email re-verification sent to: ", email);
        } catch (Exception e) {
            FormLogger.error("Failed to send email re-verification to ", email, e.getMessage());
        }
    }

    public void sendPasswordResetConfirmationEmail(String email, String customerName) {
        String subject = "Password Reset Successful - Report Builder";
        String message = "Dear " + customerName + ",\n\n" +
                "Your password has been successfully reset for your Shakti Bazaar account associated with this email (" + email + ").\n\n" +
                "You can now log in with your new password.\n\n" +
                "If you did not request this change, please contact our support team immediately.\n\n" +
                "Regards,\n" +
                "The Shakti Bazaar Team";

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom(String.format("%s <%s>", fromName, emailFrom));

            emailSender.send(mailMessage);
            FormLogger.info("Password reset confirmation email sent to: ", email);
        } catch (Exception e) {
            FormLogger.error("Failed to send password reset confirmation email to ", email, e.getMessage());
        }
    }
}
