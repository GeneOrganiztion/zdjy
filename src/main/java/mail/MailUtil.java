package mail;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Map;
import javax.mail.MessagingException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

public class MailUtil {

    private FreeMarkerConfigurer freeMarkerConfigurer = null;

    /**
     * 根据模板名称查找模板，加载模板内容后发送邮件
     *
     * @param receiver 收件人地址
     * @param subject 邮件主题
     * @param map 邮件内容与模板内容转换对象
     * @param templateName 模板文件名称
     * @return void
     * @Description:
     */
    public void sendMailByTemplate(String servername, Integer serverport, String username,
        String password, String sender, String receiver, String copyto, String subject,
        Map<String, String> map,
        String templateName, String nickName)
        throws IOException, TemplateException, MessagingException {
        String maiBody = "";
        MailSender mail = new MailSender(servername, serverport, true);
        mail.setNamePass(username, password, nickName);
        // maiBody = TemplateFactory.generateHtmlFromFtl(templateName, map);
        maiBody = getFreemarker(templateName, map);
        mail.setSubject(subject);
        mail.setBody(maiBody);
        mail.setReceiver(receiver);
        mail.setCopyTo(copyto);
        mail.setSender(sender);
        mail.sendout();
    }

    /**
     * 根据模板名称查找模板，加载模板内容后给多人发送邮件
     *
     * @param receivers 收件人地址
     * @param subject 邮件主题
     * @param map 邮件内容与模板内容转换对象
     * @param templateName 模板文件名称
     * @return void
     * @Description:
     */
    public void sendMailToReceivesByTemplate(String servername, Integer serverport, String username,
        String password, String sender, String[] receivers, String copyto, String subject,
        Map<String, Object> map,
        String templateName, String nickName)
        throws IOException, TemplateException, MessagingException {
        String maiBody = "";
        MailSender mail = new MailSender(servername, serverport, true);
        mail.setNamePass(username, password, nickName);
        // maiBody = TemplateFactory.generateHtmlFromFtl(templateName, map);
        //maiBody = getFreemarker(templateName, map);
        // 通过指定模板名获取FreeMarker模板实例
        Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        maiBody = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);

        mail.setSubject(subject);
        mail.setBody(maiBody);
        mail.addReceivers(receivers);
        mail.setCopyTo(copyto);
        mail.setSender(sender);
        mail.sendout();
    }

    /**
     * 根据模板名称查找模板，加载模板内容后发送邮件
     *
     * @param receiver 收件人地址
     * @param subject 邮件主题
     * @param map 邮件内容与模板内容转换对象
     * @param templateName 模板文件名称
     * @return void
     * @Description:
     */
    public void sendMailAndFileByTemplate(String servername, Integer serverport, String username,
        String password, String sender, String receiver, String copyto, String subject,
        String filePath,
        Map<String, String> map, String templateName, String nickName)
        throws IOException, TemplateException, MessagingException {
        String maiBody = "";
        MailSender mail = new MailSender(servername, serverport, true);
        mail.setNamePass(username, password, nickName);
        // maiBody = TemplateFactory.generateHtmlFromFtl(templateName, map);
        maiBody = getFreemarker(templateName, map);
        mail.setSubject(subject);
        mail.addFileAffix(filePath);
        mail.setBody(maiBody);
        mail.setReceiver(receiver);
        mail.setCopyTo(copyto);
        mail.setSender(sender);
        mail.sendout();
    }

    /**
     * 普通方式发送邮件内容
     *
     * @param receiver 收件人地址
     * @param subject 邮件主题
     * @param maiBody 邮件正文
     * @return void
     * @Description:
     */
    public void sendMail(String servername, Integer serverport, String username, String password,
        String sender, String receiver, String copyto, String subject, String maiBody,
        String nickName)
        throws IOException, MessagingException {
        MailSender mail = new MailSender(servername, serverport, true);
        mail.setNamePass(username, password, nickName);
        mail.setSubject(subject);
        mail.setBody(maiBody);
        mail.setReceiver(receiver);
        mail.setCopyTo(copyto);
        mail.setSender(sender);
        mail.sendout();
    }

    /**
     * 普通方式发送邮件内容，并且附带文件附件
     *
     * @param receiver 收件人地址
     * @param subject 邮件主题
     * @param filePath 文件的绝对路径
     * @param maiBody 邮件正文
     * @return void
     * @Description:
     */
    public void sendMailAndFile(String servername, Integer serverport, String username,
        String password, String sender, String receiver, String copyto, String subject,
        String maiBody, String filePath, String nickName)
        throws IOException, MessagingException {
        MailSender mail = new MailSender(servername, serverport, true);
        mail.setNamePass(username, password, nickName);
        mail.setSubject(subject);
        mail.setBody(maiBody);
        mail.addFileAffix(filePath);
        mail.setReceiver(receiver);
        mail.setCopyTo(copyto);
        mail.setSender(sender);
        mail.sendout();
    }

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    private String getFreemarker(String templateName, Map<String, String> root) {
        String htmlText = "";
        try {
            // 通过指定模板名获取FreeMarker模板实例
            Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlText;
    }
}
