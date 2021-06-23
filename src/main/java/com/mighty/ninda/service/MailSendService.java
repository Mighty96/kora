package com.mighty.ninda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailSendService {

    private int size;

    @Autowired
    private JavaMailSenderImpl mailSender;

    private String getNumber() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while (buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }

    public String sendAuthMail(String email) {
        this.size = 10;

        String authKey = getNumber();

        try {
            MimeMessage mail = mailSender.createMimeMessage();
            String mailContent = "<h1>Ninda에 오신것을 환영합니다~!!</h1><br><p>아래 링크를 클릭하시면 이메일 인증이 완료되어 모든 서비스를 정상적으로 이용하실 수 있습니다.</p>"
                    + "<a href='http://localhost:8080/authConfirm?email="
                    + email + "&authKey=" + authKey + "' target='_blenk'>이메일 인증 확인</a>";

            mail.setSubject("회원가입 이메일 인증 ", "utf-8");
            mail.setText(mailContent, "utf-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return authKey;
    }

    public String sendNewPassword(String email) {
        this.size = 10;

        String newPassword = "ninda" + getNumber();

        try {
            MimeMessage mail = mailSender.createMimeMessage();
            String mailContent = "<h1>Ninda에서 임시 비밀번호를 보내드립니다.</h1><br><br><p>임시비밀번호: " + newPassword + "</p><br><br><p>꼭 로그인하신 후 비밀번호를 변경해주세요.</p>";

            mail.setSubject("Ninda에서 임시 비밀번호를 보내드립니다.", "utf-8");
            mail.setText(mailContent, "utf-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return newPassword;
    }
}
