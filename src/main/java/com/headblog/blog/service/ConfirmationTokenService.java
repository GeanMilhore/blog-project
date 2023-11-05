package com.headblog.blog.service;

import com.headblog.blog.domain.email.StatusEmail;
import com.headblog.blog.domain.token.ConfirmationToken;
import com.headblog.blog.domain.user.User;
import com.headblog.blog.domain.user.UserStatus;
import com.headblog.blog.repository.ConfirmationTokenRepository;
import com.headblog.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${minutes-to-expire-token}")
    private Long expirationTime;

    @Value("${confirmation-mail-from}")
    private String mailFrom;

    @Transactional
    public ConfirmationToken sendUserRegistryToken(User user) {
        ConfirmationToken confirmationToken = createConfirmationToken(user);
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(confirmationToken.getUser().getEmail());
            message.setSubject("Click in the link bellow to confirm your email and start using your blog!");
            message.setText("<a>localhost:8080/user/validateUserRegistry?token=" + confirmationToken.getToken()+"</a>");
            emailSender.send(message);

            confirmationToken.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e){
            confirmationToken.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return confirmationTokenRepository.save(confirmationToken);
        }
    }

    private ConfirmationToken createConfirmationToken(User user){
        return new ConfirmationToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(expirationTime),
                user);
    }

    @Transactional
    public String validateUserRegistry(String token){
        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByToken(token).orElseThrow(() -> new BadCredentialsException("invalid token."));

        if(confirmationToken.getConfirmedAt() != null || !isValidDate(confirmationToken.getExpiresAt())){
            throw new BadCredentialsException("expired token.");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        User user = confirmationToken.getUser();
        user.setStatus(UserStatus.ACTIVE);
        return "confirmed registration!";
    }

    private Boolean isValidDate(LocalDateTime expired){
        return LocalDateTime.now().isBefore(expired);
    }
}