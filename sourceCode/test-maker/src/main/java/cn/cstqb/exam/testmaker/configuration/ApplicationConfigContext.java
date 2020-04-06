package cn.cstqb.exam.testmaker.configuration;

import cn.cstqb.exam.testmaker.entities.Project;
import cn.cstqb.exam.testmaker.entities.User;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.typesafe.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2014/12/16
 * Time: 16:14
 */
public class ApplicationConfigContext {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Config config;
    private final ConfigParseOptions parseOptions;
    private ConfigResolveOptions resolveOptions;
    private static ApplicationConfigContext instance;

    ApplicationConfigContext() {
        parseOptions = ConfigParseOptions.defaults().setSyntax(ConfigSyntax.CONF);
        resolveOptions=ConfigResolveOptions.defaults();
        //loads default config only
        Config defaultConfig = ConfigFactory.load();

        /*
         * If application-test.conf is found in the classpath, the custom file
         * set in entry <em>config-file.path</em>in default conf will be ignored
         */
        Config customConfig = null;
        String testConf = "application-test";
        URL url=this.getClass().getClassLoader().getResource(String.format("%s.conf", testConf));
        if (url!=null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Loading testing config: {}", url);
            }
            customConfig = ConfigFactory.load(testConf, parseOptions, resolveOptions);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Custom configuration origin: {}", customConfig!=null?customConfig.origin():null);
        }

        if (customConfig != null) {
            this.config = ConfigFactory.load(customConfig).withFallback(defaultConfig);
        } else {
            this.config = defaultConfig;
        }

    }

    /**
     * This is the entry point for getting application configuration
     * @return
     */
    public static ApplicationConfigContext getInstance() {
        if (instance == null) {
            instance=new ApplicationConfigContext();
        }
        return instance;
    }

    public Config getConfig() {
        return config;
    }

    public Properties getJPAProperties() {
        Properties properties = new Properties();
        Config _conf = this.config.getConfig("jpa");

        for (Map.Entry<String, ConfigValue> entry : _conf.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getValue().unwrapped().toString());
        }
        return properties;
    }

    /**
     * Get question difficulty levels from application configuration
     * @return A map of difficulty levels. The key is integer which is saved into database while the value is
     * the label used for display.
     */
    public Map<Integer, String> getQuestionDifficultyLevels() {
        Map<Integer, String> levels = new HashMap<>();
        Config _conf = this.config.getConfig("cstqb.difficulty");
        for (Map.Entry<String, ConfigValue> entry : _conf.entrySet()) {
            if (logger.isDebugEnabled()) {
                logger.debug("difficulty level: [{}]",entry.getKey());
            }
            String key = entry.getKey().replaceAll("\"\\s*", "");
            levels.put(Integer.parseInt(key), entry.getValue().unwrapped().toString());
        }
        return levels;
    }

    public String getDefaultDateFormat() {
        return this.config.getString("application.date-format");
    }

    public String getUploadBaseDir() {
        String baseDir = this.config.getString("application.uploads.dir");
        logger.debug("ApplicationConfigContext.getUploadBaseDir: {}",baseDir );
        return baseDir;
    }

    public String getUploadBackUpDir(Project project) {
    	String backupDir = String.format("%s/%d/%s",
                this.getRepositoryBaseDir(),
                project.getId(),
                this.getConfigValue("application.uploads.dir")
                );
    	logger.debug("ApplicationConfigContext.getUploadBackUpDir: {}", backupDir);
    	return backupDir;
    }

    public String getExportDir(Project project) {
        return String.format("%s/%s/%s",
                getRepositoryBaseDir(),
                project.getId(),
                config.getString("application.export.dir-name")
                );
    }

    public String getRepositoryBaseDir() {
        return this.config.getString("application.repository.dir");
    }
    /**
     *
     * @param key
     * @return
     */
    public String getConfigValue(String key) {
        checkArgument(!Strings.isNullOrEmpty(key));
        return this.config.getString(key);
    }

    /**
     * Gets the threshold value for warning of expiring tasks
     * @return
     */
    public int getWarningThreshold() {
        try {
            return this.config.getInt("monitoring.expiring.warning-threshold");
        } catch (Exception e) {
            return 5;
        }
    }

    public User getBuiltInUser() {
        User user = new User();
        Config builtInUser = config.getConfig("application.built-in.user");
        user.setUsername(builtInUser.getString("username"));
        user.setFullName(builtInUser.getString("fullName"));
        user.setPassword(builtInUser.getString("password"));
        user.setEmail(builtInUser.getString("email"));
        user.setPhone(builtInUser.getString("phone"));
        user.setAdmin(true);
        return user;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("config", config)
                .toString();
    }
}
