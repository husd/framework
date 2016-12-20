package com.husd.framework.util;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);

    private String sendFrom;
    private String smtpHost;
    private Integer smtpPort;
    private String userName;
    private String password;
    private Integer socketConnectionTimeout;
    private Integer socketTimeout;

    public void sendMail(String subject, String content, String sendTo) {

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(smtpHost);
            email.setSmtpPort(smtpPort);
            email.setCharset("utf-8");
            email.setSubject(subject);
            email.setFrom(sendFrom, userName);
            email.setSocketConnectionTimeout(socketConnectionTimeout);
            email.setSocketTimeout(socketTimeout);
            email.setAuthentication(userName, password);

            String[] mails = sendTo.split(",");
            for (String to : mails) {
                email.addTo(to);
            }

            email.setHtmlMsg(content);
            email.send();
            LOGGER.info("[email]邮件发送成功 subject is :{} sendTo is:{}", subject, sendTo);
        } catch (EmailException e) {
            LOGGER.error("[email]邮件发送失败 subject is :{} sendTo is:{}", subject, sendTo);
        }
    }

    public void sendMailAndCC(String subject, String content, String sendTo, String sendCC) {

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(smtpHost);
            email.setSmtpPort(smtpPort);
            email.setCharset("utf-8");
            email.setSubject(subject);
            email.setFrom(sendFrom, userName);
            email.setSocketConnectionTimeout(socketConnectionTimeout);
            email.setSocketTimeout(socketTimeout);
            email.setAuthentication(userName, password);

            String[] mails = sendTo.split(",");
            for (String to : mails) {
                email.addTo(to);
            }

            String[] ccMails = sendCC.split(",");
            for (String cc : ccMails) {
                email.addCc(cc);
            }

            email.setHtmlMsg(content);
            email.send();
            LOGGER.info("[email]邮件发送带附件成功 subject is :{} sendTo is:{}", subject, sendTo);
        } catch (EmailException e) {
            LOGGER.error("[email]邮件发送带附件失败 subject is :{} sendTo is:{}", subject, sendTo);
        }
    }

    public void sendMailWithAttachment(String subject, String content, String sendTo, String path) {

        HtmlEmail email = new HtmlEmail();

        try {

            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(path);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);

            email.setHostName(smtpHost);
            email.setSmtpPort(smtpPort);
            email.setCharset("utf-8");
            email.setSubject(subject);
            email.setFrom(sendFrom, userName);
            email.setSocketConnectionTimeout(socketConnectionTimeout);
            email.setSocketTimeout(socketTimeout);
            email.setAuthentication(userName, password);
            email.attach(attachment);

            String[] mails;
            if (sendTo.contains(",")) {
                mails = sendTo.split(",");
            } else if (sendTo.contains(";")) {
                mails = sendTo.split(";");
            } else {
                mails = new String[] {sendTo};
            }

            for (String to : mails) {
                email.addTo(to);
            }

            email.setHtmlMsg(content);
            email.send();
            LOGGER.info("[email]邮件发送带附件成功 subject is :{} sendTo is:{}", subject, sendTo);
        } catch (EmailException e) {
            LOGGER.error("[email]邮件发送带附件失败 subject is :{} sendTo is:{}", subject, sendTo);
        }
    }
}
