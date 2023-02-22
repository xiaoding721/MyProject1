package com.example.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Properties;

@Getter
@Setter
public class MailUtil {

    private String content;         //内容
    private String type;            //
    private String emailHost;       //发送邮件的主机
    private String transportType;   //邮件发送的协议
    private String fromUser;        //发件人名称
    private String fromEmail;       //发件人邮箱
    private String authEmail;       //发件人邮箱
    private String authCode;        //发件人邮箱授权码
    private String toEmail;         //收件人邮箱
    private String subject;         //主题信息
    //设置发件人第二种方式：发件人信息拼接显示：蚂蚁小哥 <antladdie@163.com>
    private String formName;

    //设置抄送人(两个)可有可无抄送人： new InternetAddress(toEmail)
    private InternetAddress[] addressesArr;

    //设置密送人 可有可无密送人： new InternetAddress(toEmail)
    private InternetAddress[] toBCCs;

    private boolean debug = false;

    private Properties props;

    {
        defaultInitialize();
        //初始化默认参数
        props = new Properties();
        props.setProperty("mail.transport.protocol", transportType);
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.user", fromUser);
        props.setProperty("mail.from", fromEmail);
    }


    @SneakyThrows
    public void defaultInitialize() {
        content = "<h1>有内鬼！取消交易！</h1>";
        type = "text/html;charset=UTF-8";
        emailHost = "smtp.88.com";
        transportType = "smtp";
        fromUser = "有内鬼！取消交易！";
        fromEmail = "luotianyi096@88.com";
        authEmail = "luotianyi096@88.com";
        authCode = "QjiM75bg7JWznMfG";
//        toEmail = "8l5-8oe75nu2f@dingtalk.com";
        toEmail = "8l5-8oe75nu2f@dingtalk.com";
        subject = "有内鬼！取消交易！";
        formName = MimeUtility.encodeWord("有内鬼！取消交易！") + " <" + fromEmail + ">";
        debug = true;
    }

    public void send() throws MessagingException {

        //获取Session对象
        Session session = Session.getInstance(props, null);
        //开启后有调试信息
        session.setDebug(debug);

        //通过MimeMessage来创建Message接口的子类
        MimeMessage message = new MimeMessage(session);
        //下面是对邮件的基本设置
        //设置发件人：
        //设置发件人第一种方式：直接显示：antladdie <antladdie@163.com>
        //InternetAddress from = new InternetAddress(sender_username);
        if (formName != null) {
            InternetAddress from = new InternetAddress(formName);
            message.setFrom(from);
        } else {
            throw new RuntimeException("未设置发件人");
        }

        if(toEmail != null) {
            //设置收件人：
            InternetAddress to = new InternetAddress(toEmail);
            message.setRecipient(Message.RecipientType.TO, to);
        }else {
            throw new RuntimeException("未设置收件人");
        }
        // 设置抄送人
        if (addressesArr != null) {
            message.setRecipients(Message.RecipientType.CC, addressesArr);
        }
        if (toBCCs != null) {
            for (Address address : toBCCs) {
                message.setRecipient(Message.RecipientType.BCC, address);
            }
        }
        //设置邮件主题
        message.setSubject(subject);

        //设置邮件内容,这里我使用html格式，其实也可以使用纯文本；纯文本"text/plain"
        message.setContent(content, type);

        //保存上面设置的邮件内容
        message.saveChanges();

        //获取Transport对象
        Transport transport = session.getTransport();

        //smtp验证，就是你用来发邮件的邮箱用户名密码（若在之前的properties中指定默认值，这里可以不用再次设置）
        transport.connect(emailHost, authEmail, authCode);

        //发送邮件
        transport.sendMessage(message, message.getAllRecipients()); // 发送
    }
}
