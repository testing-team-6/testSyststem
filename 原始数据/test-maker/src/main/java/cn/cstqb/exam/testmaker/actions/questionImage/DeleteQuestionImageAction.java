package cn.cstqb.exam.testmaker.actions.questionImage;

import cn.cstqb.exam.testmaker.entities.QuestionImage;
import cn.cstqb.exam.testmaker.services.IQuestionImageService;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class DeleteQuestionImageAction extends BaseQuestionImageAction {
    @Inject private IQuestionImageService questionImageService;
    private int imageId;
    private QuestionImage image;

    public DeleteQuestionImageAction() {
        injector.injectMembers(this);
    }

    /**
     *
     */
    @Override
    public void validateInput() {
        if (imageId < 1) {
            addActionError(getText("error.entity.invalid"));
        }
        image = questionImageService.findQuestionImage(imageId);
        if (image == null) {
            addActionError(getText("error.entity.id.notFound", Lists.newArrayList(imageId, getText("label.entity.question.image"))));
            return;
        }
    }

    @Override
    protected String executeImpl() throws Exception {
        logger.debug("DeleteQuestionImageAction.executeImpl: Trying to delete choice image: #0", Objects.toString(imageId) );

        /*
         * Deleting file from disk
         */
        try {
            deleteImageFile();
        } catch (IOException e) {
            logger.error("DeleteQuestionImageAction.executeImpl: Error when deleting files", e );
            return ERROR;
        }
        logger.debug("DeleteQuestionImageAction.executeImpl: deleting file from DB." );
        questionImageService.delete(image);
        logger.debug("DeleteQuestionImageAction.executeImpl: Choice image deleted successfully!" );
        return null;
    }

    private void deleteImageFile() throws IOException {
        String path = image.getPath();

        //delete from working dir
        String realPath = ServletActionContext.getServletContext().getRealPath(path);
        logger.debug("DeleteQuestionChoiceImageAction.deleteImageFile: Deleting file [#0]...", realPath);
        Files.deleteIfExists(Paths.get(realPath));

        //delete from backup dir
        String relativePath = path.replace(configContext.getUploadBaseDir(),"");
        Path pathInBackupDir = Paths.get(configContext.getUploadBackUpDir(image.getQuestion().getProject()), relativePath);

        logger.debug("DeleteQuestionChoiceImageAction.deleteImageFile: Deleting files from backup dir: #0", pathInBackupDir );
        Files.deleteIfExists(pathInBackupDir);
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
