package com.Devex.Sevice.ServiceImpl;

import com.Devex.DTO.MailInfo;
import com.Devex.Entity.Comment;
import com.Devex.Entity.Notifications;
import com.Devex.Sevice.MailerService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.NotificationsService;
import com.Devex.Sevice.SessionService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SessionScope
@Service("notiService")
public class NotiServiceImpl implements NotiService {
    @Autowired
    NotificationsService notificationsService;

    @Override
    public void send(String userFrom, String userTo, String link, String type) {
        try {
            Notifications noti = new Notifications();
            String content = "";

            switch (type) {
                case "comment": {
                    content = userFrom  + " Đã bình luận vào sản phẩm của bạn "+link;
                    noti.setContent(content);
                    noti.setUserTo(userTo);
                    noti.setUserFrom(userFrom);
                    noti.setLink(link);
                    notificationsService.save(noti);
                    break;
                }

                case "newproduct": {

                    break;
                }
                case "other": {

                    break;
                }
                default:
            }

        } catch (Exception e) {
        }
    }
}
