package cn.cstqb.exam.testmaker.mailing;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/2
 * Time: 16:31
 */
public class MailMessenger {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MailMessenger() {
    }

    public void send(SendMailTask mailTask) {
        Preconditions.checkNotNull(mailTask);
        logger.debug("MailMessenger.send: {}: {}", mailTask.getClass().getName(), mailTask);
        Thread mailer = new Thread(mailTask, "Messenger");
        mailer.start();
    }

}
