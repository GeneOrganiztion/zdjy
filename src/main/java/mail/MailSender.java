package mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MailSender {

    private MimeMessage mimeMsg; // MIME邮件对象
    private Session session; // 邮件会话对象
    private Properties props; // 系统属性
    private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成
    private String username;// 发件人的用户名
    private String password;// 发件人的密码
    private String nickname;// 发件人的昵称
    private static Log log = LogFactory.getLog(MailSender.class);


    /**
     * 有参构造器
     */
    public MailSender(String smtp, Integer port, boolean auth) {
        setSmtpHost(smtp);
        setSmtpPort(port);
        setSmtpAuth(auth);
        createMimeMessage();
    }

    /**
     * 设置邮件发送的SMTP主机端口
     *
     * @param port SMTP 发送主机端口
     * @return void
     * @Date:2014年4月26日 上午10:20:34
     * @author
     * @Description:
     */
    public void setSmtpPort(Integer port) {
        if (props == null) {
            props = System.getProperties();
        }
        props.setProperty("mail.smtp.port", port == null ? "" : port.toString());
        //props.put("mail.smtp.port", port);
        log.debug("set system properties success ：mail.smtp.port= " + port);
    }

    /**
     * 设置权限鉴定配置
     *
     * @param auth 是否需要权限
     * @return void
     * @Date:2014年4月26日 上午10:36:34
     * @author
     * @Description:
     */
    public void setSmtpAuth(boolean auth) {
        if (props == null) {
            props = System.getProperties();
        }
        props.put("mail.smtp.auth", auth);
        log.debug("set smtp auth success：mail.smtp.auth= " + auth);

    }

    /**
     * 设置邮件发送的SMTP主机
     *
     * @param hostName SMTP 发送主机
     * @return void
     * @Date:2014年4月26日 上午10:20:34
     * @author
     * @Description:
     */
    public void setSmtpHost(String hostName) {
        if (props == null) {
            props = System.getProperties();
        }
        props.put("mail.smtp.host", hostName);
        log.debug("set system properties success ：mail.smtp.host= " + hostName);

    }

    /**
     * 创建邮件对象
     *
     * @return boolean
     * @Date:2014年4月26日 上午10:26:34
     * @author
     * @Description:
     */
    public void createMimeMessage() {
        // 获得邮件会话对象
        session = Session.getDefaultInstance(props, null);
        // 创建MIME邮件对象
        mimeMsg = new MimeMessage(session);
        mp = new MimeMultipart();
        log.debug(" create session and mimeMessage success");
    }


    /**
     * 设置发送邮件的主题
     *
     * @param subject 邮件的主题
     * @return void
     * @Date:2014年4月26日 上午10:26:34
     * @author
     * @Description:
     */
    public void setSubject(String subject) throws UnsupportedEncodingException, MessagingException {
        mimeMsg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
        log.debug("set mail subject success, subject= " + subject);

    }

    /**
     * @param mailBody 邮件的正文内容
     * @return void
     * @Date:2014年4月26日 上午10:28:34
     * @author
     * @Description:
     */
    public void setBody(String mailBody) throws MessagingException {
        BodyPart bp = new MimeBodyPart();
        bp.setContent("" + mailBody, "text/html;charset=GBK");
        mp.addBodyPart(bp);
        log.debug("set mail body content success,mailBody= " + mailBody);
    }

    /**
     * 添加邮件附件
     *
     * @param filePath 文件绝对路径
     * @return void
     * @Date:2014年4月26日 上午10:30:40
     * @author
     * @Description:
     */
    public void addFileAffix(String filePaths) throws MessagingException {
        if (StringUtils.isEmpty(filePaths)) {
            return;
        }
        String[] filePaths_ = filePaths.split(",");
        for (String filePath : filePaths_) {
            BodyPart bp = new MimeBodyPart();
            FileDataSource fileds = new FileDataSource(filePath);
            bp.setDataHandler(new DataHandler(fileds));
            bp.setFileName(fileds.getName());
            mp.addBodyPart(bp);
            log.debug("mail add file success,filename= " + filePath);
        }
    }

    /**
     * 设置发件人邮箱地址
     *
     * @param sender 发件人邮箱地址
     * @return void
     * @Date:2014年4月26日 上午10:35:54
     * @author
     * @Description:
     */
    public void setSender(String sender)
        throws UnsupportedEncodingException, AddressException, MessagingException {
        if (StringUtils.isEmpty(nickname)) {
            mimeMsg.setFrom(new InternetAddress(sender));
        } else {
            nickname = MimeUtility.encodeText(nickname, "utf-8", "B");
            mimeMsg.setFrom(new InternetAddress(nickname + " <" + sender + ">"));
        }
        log.debug(
            " set mail sender and nickname success , sender= " + sender + ",nickname=" + nickname);
    }

    /**
     * 设置收件人邮箱地址
     *
     * @param receiver 收件人邮箱地址
     * @return void
     * @Date:2014年4月26日 上午10:41:06
     * @author
     * @Description:
     */
    public void setReceiver(String receiver) throws AddressException, MessagingException {
        mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        log.debug("set mail receiver success,receiver = " + receiver);
    }

    /**
     * 添加收件人邮箱地址
     *
     * @return void
     * @Date:2014年4月26日 上午10:41:06
     * @author
     * @Description:
     */
    public void addReceivers(String[] receivers) throws AddressException, MessagingException {
        for (String receiver : receivers
            ) {
            mimeMsg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        }

        log.debug("add mail receiver success,receiver = " + receivers);
    }

    /**
     * 设置抄送人的邮箱地址
     *
     * @param copyto 抄送人邮箱地址
     * @return void
     * @Date:2014年4月26日 上午10:42:14
     * @author
     * @Description:
     */
    public void setCopyTo(String copyto) throws AddressException, MessagingException {
        if (StringUtils.isEmpty(copyto)) {
            return;
        }
        mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copyto));
        log.debug("set mail copyto receiver success,copyto = " + copyto);
    }

    /**
     * 设置发件人用户名密码进行发送邮件操作
     *
     * @return void
     * @Date:2014年4月26日 上午10:44:01
     * @author
     * @Description:
     */
    public void sendout() throws MessagingException {
        mimeMsg.setContent(mp);
        mimeMsg.saveChanges();
        Session mailSession = Session.getInstance(props, null);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect((String) props.get("mail.smtp.host"), username, password);
        transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
        transport.close();
        System.out.println(" send mail success");
        log.debug(" send mail success");
    }

    /**
     * 注入发件人用户名 ，密码 ，昵称
     *
     * @param username 发件人邮箱登录用户名
     * @param password 发件人邮箱密码
     * @param nickname 发件人显示的昵称
     * @return void
     * @Date:2014年4月26日 上午10:50:12
     * @author
     * @Description:
     */
    public void setNamePass(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;

    }
}