package cn.cstqb.exam.testmaker;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

public final class Release {
    private Properties props;
    private static Release release;

    /**
     * Private constructor prevents instantiation.
     */
    public Release() {
        props = new Properties();
        InputStream is = this.getClass().getResourceAsStream(VERSION_PROPERTY_FILE);
        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return
     */
    public String geProductName() {
        return props.getProperty(PROPERTY_APP_NAME);
    }

    public String getBuildNumber() {
        return props.getProperty(PROPERTY_BUILD_NUMBER);
    }

    public String getBuildDate() {
        return props.getProperty(PROPERTY_BUILD_DATE);
    }

    public String getRevision() {
        return props.getProperty(PROPERTY_SVN_REV);
    }

    public String getBuildId() {
        String buildId = String.format("%s\n\t%s\n\tr%s",
                props.get(PROPERTY_BUILD_NUMBER),
                props.get(PROPERTY_BUILD_DATE),
                props.get(PROPERTY_SVN_REV)
        );
        return buildId;
    }

    /**
     * @return
     */
    public String getVersion() {
        return props.getProperty(PROPERTY_VERSION);
    }

    public String getFullVersion() {
        return getVersion() + " (" + getBuildId() + ")";
    }

    public static String getVersionString() {
        if (release == null) release = new Release();
        return release.getVersion();
    }

    public static String getBuildString() {
        if (release == null) release = new Release();
        return release.getBuildId();
    }

    public static String getProductName() {
        if (release == null) release = new Release();
        return release.geProductName();
    }

    @Override
    public String toString() {
        return getFullVersion();
    }

    private static final String VERSION_PROPERTY_FILE = "about.properties";
    private static final String PROPERTY_APP_NAME = "app.name";
    private static final String PROPERTY_VERSION = "version.number";
    private static final String PROPERTY_BUILD_DATE = "version.build.date";
    private static final String PROPERTY_BUILD_NUMBER = "version.build.number";
    private static final String PROPERTY_SVN_REV = "version.scm.rev";
}
