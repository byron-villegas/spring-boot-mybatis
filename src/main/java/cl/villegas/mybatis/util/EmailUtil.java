package cl.villegas.mybatis.util;

import cl.villegas.mybatis.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

public class EmailUtil {
    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static void sendPlainText(String toEmail, String subject, String body) {
        try {
            MimeMessage mimeMessage = generateMessage();
            setMimeMessageGenericProperties(toEmail, subject, mimeMessage);
            mimeMessage.setText(body, Constants.MimeMessage.CHARSET);
            Transport.send(mimeMessage);
        } catch (MessagingException ex) {
            logger.error(ex.getMessage());
        }
    }

    public static void sendHtml(String toEmail, String subject, String html) {
        try {
            MimeMessage mimeMessage = generateMessage();
            setMimeMessageGenericProperties(toEmail, subject, mimeMessage);
            mimeMessage.setText(html, Constants.MimeMessage.CHARSET, Constants.MimeMessage.SUBTYPE);
            Transport.send(mimeMessage);
        } catch (MessagingException ex) {
            logger.error(ex.getMessage());
        }
    }

    public static void sendFile(String toEmail, String subject, String body, String fileName, String contentType, byte[] file) {
        try {
            MimeMessage mimeMessage = generateMessage();
            setMimeMessageGenericProperties(toEmail, subject, mimeMessage);

            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);

            bodyPart = new MimeBodyPart();
            ByteArrayDataSource dataSource = new ByteArrayDataSource(file, contentType);
            bodyPart.setDataHandler(new DataHandler(dataSource));
            bodyPart.setFileName(fileName);
            multipart.addBodyPart(bodyPart);
            mimeMessage.setContent(multipart);
            Transport.send(mimeMessage);
        } catch (MessagingException  e) {
            logger.error(e.getMessage());
        }
    }

    public static void sendReport(String toEmail, String subject, String body, String fileName, String type, byte[] file) {
        try {
            MimeMessage mimeMessage = generateMessage();
            setMimeMessageGenericProperties(toEmail, subject, mimeMessage);

            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);

            String content = "";
            switch (type.toUpperCase()) {
                case Constants.FileType.XLSX:
                    content = Constants.ContentType.APPLICATION_EXCEL;
                    break;
                case Constants.FileType.PDF:
                    content = Constants.ContentType.APPLICATION_PDF;
                    break;
                case Constants.FileType.CSV:
                    content = Constants.ContentType.TEXT_CSV;
                    break;
                case Constants.FileType.HTML:
                    content = Constants.ContentType.TEXT_HTML;
                    break;
            }

            bodyPart = new MimeBodyPart();
            ByteArrayDataSource dataSource = new ByteArrayDataSource(file, content);
            bodyPart.setDataHandler(new DataHandler(dataSource));
            bodyPart.setFileName(fileName + "." + type.toLowerCase());
            multipart.addBodyPart(bodyPart);
            mimeMessage.setContent(multipart);
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    private static Properties generateProperties() {
        Properties properties = new Properties();
        properties.put(Constants.Properties.MAIL_SMTP_HOST_PROPERTY, Constants.Properties.MAIL_SMTP_HOST_VALUE);
        return properties;
    }

    private static Session generateSession() {
        return Session.getInstance(generateProperties(), Constants.Session.AUTHENTICATOR);
    }

    private static MimeMessage generateMessage() {
        return new MimeMessage(generateSession());
    }

    private static void setMimeMessageGenericProperties(String toEmail, String subject, MimeMessage mimeMessage) {
        try {
            mimeMessage.addHeader(Constants.MimeMessage.CONTENT_TYPE_HEADER_PROPERTY, Constants.MimeMessage.CONTENT_TYPE_HEADER_VALUE);
            mimeMessage.addHeader(Constants.MimeMessage.FORMAT_HEADER_PROPERTY, Constants.MimeMessage.FORMAT_HEADER_VALUE);
            mimeMessage.addHeader(Constants.MimeMessage.CONTENT_TRANSFER_ENCODING_HEADER_PROPERTY, Constants.MimeMessage.CONTENT_TRANSFER_ENCODING_HEADER_VALUE);
            mimeMessage.setFrom(new InternetAddress(Constants.MimeMessage.FROM_EMAIL, Constants.MimeMessage.FROM_EMAIL_NAME));
            mimeMessage.setReplyTo(InternetAddress.parse(Constants.MimeMessage.FROM_EMAIL, Constants.MimeMessage.STRICT));
            mimeMessage.setSubject(subject, Constants.MimeMessage.CHARSET);
            mimeMessage.setSentDate(new Date());
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, Constants.MimeMessage.STRICT));
        }
        catch (MessagingException | UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }
}