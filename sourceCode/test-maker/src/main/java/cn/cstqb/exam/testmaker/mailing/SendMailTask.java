package cn.cstqb.exam.testmaker.mailing;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/2
 * Time: 16:11
 */
public class SendMailTask implements Runnable {
    private final HtmlEmail email;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public SendMailTask(HtmlEmail email) {
        this.email = email;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        logger.debug("SendMailTask.run: Starting to send email...." );
        try {
            logger.debug("SendMailTask.run: Sending emails to: {}", email.getToAddresses() );
            logger.debug("SendMailTask.run: Sending emails to (CC): {}", email.getCcAddresses());
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
            logger.error("SendMailTask.run: sending mail failed: {}", e );
        }

        logger.info("SendMailTask.run: mail successfully sent to: {}", email.getToAddresses() );
    }

    public HtmlEmail getEmail() {
        return email;
    }
}
