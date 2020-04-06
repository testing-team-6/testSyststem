package cn.cstqb.exam.testmaker.mailing;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class MailMessengerTest {
    private static final Logger logger = LoggerFactory.getLogger(MailMessengerTest.class);
    private MailMessenger mailer;


    @Before
    public void setUp() throws Exception {
        logger.debug("MailMessengerTest.setUp: starting up messenger");
        mailer = new MailMessenger();
    }

    @Test
    public void testPush() throws Exception {

/*        for (int i = 0; i < 50; i++) {
            final String task = String.format("Task %d", i);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mailer.send(task);
                }
            });
            thread.start();
            thread.join();
        }*/
/*        for (int i = 0; i < 50; i++) {
            final String task = String.format("Task %d", i);
            mailer.send(task);

        }*/

    }

    @Test
    public void testStart() throws Exception {

    }
}
