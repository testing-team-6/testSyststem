package cn.cstqb.exam.testmaker.actions;

import cn.cstqb.exam.testmaker.entities.Project;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractFileUploadAction extends BaseAction{
    protected String caption;
	protected String title;
	protected String uploadFileName;
	protected String uploadContentType;
	protected File upload;
    protected String targetFilePath; // target file name with UUID

    /**
     * This is a replacement for validate() method in ActionSupport in that the posted json
     * data is not serialized yet in ActionSupport validate() method.
     *
     * @return The result string of the first error.
     * @see cn.cstqb.exam.testmaker.configuration.Constants
     */
    @Override
    public void validateInput() {
        if (upload == null) {
            addActionError(getText("error.upload.file.notReceived"));
            return;
        }

        if (upload.length() > 10000000) {
            addActionError(getText("struts.messages.upload.error.SizeLimitExceededException", Lists.newArrayList(FileUtils.byteCountToDisplaySize(10000000))));
            return;
        }
    }

    /**
     * Gets the path to the image file from the base directory.
     * <p>
     *     For example, if the base director is <em>/uploadFiles</em> and the full path is
     *     <em>/uploadFiles/TEST-FIRST-PROJECT/11/12/1429325173028@d01cd062-871e-4828-ab4b-470e590b008b.png</em>,
     *     this method should return <em>11/12/1429325173028@d01cd062-871e-4828-ab4b-470e590b008b.png</em>
     * </p>
     * @return The path below the base directory
     */
    protected abstract void initTargetFilePath();

    protected abstract Project getContainingProject();

    /**
     * Generate a UUID hashed image name
     * @return
     */
    protected String getTargetFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
        return String.format("%s@%s.%s",
                sdf.format(new Date()),
                UUID.randomUUID().toString(),
                getFileExtension());
    }

    protected abstract void postUpload();

    protected String getServerPath() {
        String serverPath= String.format("%s/%s/%s",
                configContext.getConfigValue("application.uploads.dir"),
                getContainingProject().getId(),
                targetFilePath
        );
        logger.info("AbstractFileUploadAction.getServerPath: Target file path is: [#0]", serverPath );
        return serverPath;
    }
    /**
     * The real action should be defined in this method. Do not use execute. otherwise you get nothing
     *
     * @return <b>null</b> or empty string if action is successful; otherwise return your error result
     * @throws Exception
     */
    @Override
    protected String executeImpl() throws Exception {
        logger.debug("AbstractFileUploadAction.executeImpl: Preparing to upload image #0", uploadFileName);
        initTargetFilePath();
        logger.debug("AbstractFileUploadAction.preUpload: calculated target file name: [#0]", targetFilePath);

        try {
            saveUploadFile();
        } catch (IOException e) {
            addActionError(getText("error.upload.failed", Lists.newArrayList(ExceptionUtils.getStackTrace(e))));
            return ERROR;
        }

        postUpload();
        logger.debug("AbstractFileUploadAction.executeImpl: Image [#0] uploaded.", uploadFileName);
        return null;
    }

    protected void saveUploadFile() throws IOException {

        _saveUploadFile(false);
        _saveUploadFile(true);
    }

    private void _saveUploadFile(boolean isBackUp) throws IOException {
        String realBaseDir = "";
        if (isBackUp) {
            realBaseDir = configContext.getUploadBackUpDir(getContainingProject());
        } else {
            realBaseDir = ServletActionContext.getServletContext().getRealPath(configContext.getConfigValue("application.uploads.dir"));
        }

        logger.debug("AbstractFileUploadAction._saveUploadFile: real base dir: [#0]",realBaseDir );

        Path outputFile = isBackUp?Paths.get(realBaseDir, targetFilePath):Paths.get(realBaseDir, getContainingProject().getId().toString(), targetFilePath);
        logger.debug("AbstractFileUploadAction._saveUploadFile: target file full path: [#0]", outputFile);
        Files.createDirectories(outputFile.getParent());
        Files.copy(new FileInputStream(upload), outputFile);
    }


    /**
     * parse the file type
     * from format "image/png" to "png"
     * @return the real file type string like "png" in windows OS
     */
    protected String getFileExtension() {
        return uploadContentType.substring(uploadContentType.indexOf('/')+1);
    }

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
