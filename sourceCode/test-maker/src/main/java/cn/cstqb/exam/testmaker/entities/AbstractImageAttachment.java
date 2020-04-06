package cn.cstqb.exam.testmaker.entities;

import cn.cstqb.exam.testmaker.configuration.ApplicationConfigContext;
import com.google.common.base.Strings;
import org.apache.commons.io.FilenameUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/4/18
 * Time: 17:56
 */
@MappedSuperclass
public abstract class AbstractImageAttachment extends AbstractAttachment {

    protected transient ApplicationConfigContext configContext = ApplicationConfigContext.getInstance();
    @Column
    protected String caption;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return !Strings.isNullOrEmpty(path);
    }

    @PostLoad
    private void prependBaseDir() {
        String baseDir=configContext.getConfigValue("application.uploads.dir");
        if (!this.path.startsWith(baseDir)) {
            this.path = FilenameUtils.separatorsToUnix(String.format("%s\\%s", baseDir, this.path));
        }else{
            this.path = FilenameUtils.separatorsToUnix(this.path);
        }
    }
}
