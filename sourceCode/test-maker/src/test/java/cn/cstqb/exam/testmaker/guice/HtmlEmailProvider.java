package cn.cstqb.exam.testmaker.guice;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.throwingproviders.CheckedProvider;
import com.typesafe.config.Config;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/1
 * Time: 23:58
 */
public class HtmlEmailProvider implements Provider<HtmlEmail> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ApplicationConfigContext configContext;

    @Inject
    public HtmlEmailProvider(ApplicationConfigContext configContext) {
        this.configContext = configContext;
    }

    /**
     * Provides a fully-constructed and injected instance of {@code T}.
     *
     * @throws RuntimeException if the injector encounters an error while
     *                          providing an instance. For example, if an injectable member on
     *                          {@code T} throws an exception, the injector may wrap the exception
     *                          and throw it to the caller of {@code get()}. Callers should not try
     *                          to handle such exceptions as the behavior may vary across injector
     *                          implementations and even different configurations of the same injector.
     */
    @Override
    public HtmlEmail get() {
        logger.debug("HtmlEmailProvider.get: Creating instance of HtmlEmail..." );
        Config config=configContext.getConfig();
        HtmlEmail mail=new HtmlEmail();
        mail.setHostName(config.getString("mail.smtp.host"));
        mail.setSmtpPort(config.getInt("mail.smtp.port"));
        mail.setSSLOnConnect(config.getBoolean("mail.smtp.ssl"));
        mail.setCharset(config.getString("mail.charset"));
        if (config.getBoolean("mail.smtp.authenticate")) {
            mail.setAuthentication(config.getString("mail.smtp.user"), config.getString("mail.smtp.password"));
        }
        try {
            mail.setFrom(config.getString("mail.from.address"),
                    config.getString("mail.from.name"),
                    config.getString("mail.charset"));
        } catch (EmailException e) {
            e.printStackTrace();
            logger.error("HtmlEmailProvider.get: {}",e );
        }
        mail.setDebug(config.getBoolean("mail.debug"));

        return mail;
    }
}
