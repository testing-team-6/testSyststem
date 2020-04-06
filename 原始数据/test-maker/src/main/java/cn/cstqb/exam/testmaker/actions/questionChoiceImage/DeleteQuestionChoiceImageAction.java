package cn.cstqb.exam.testmaker.actions.questionChoiceImage;

import cn.cstqb.exam.testmaker.entities.QuestionChoiceImage;
import com.google.common.collect.Lists;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class DeleteQuestionChoiceImageAction extends BaseQuestionChoiceImageAction {

	private QuestionChoiceImage choiceImage;
    private int imageId;


	@Override
	public void validateInput() {
		if (imageId < 1) {
			addActionError(getText("error.entity.invalid"));
		}
        choiceImage = choiceImageService.findQuestionChoiceImage(imageId);
        if (choiceImage == null) {
            addActionError(getText("error.entity.invalid", Lists.newArrayList(getText("label.entity.question.choice.image"))));
            return;
        }

        if (!choiceImage.isValidID()) {
            addActionError(getText("error.entity.id.invalid", Lists.newArrayList(choiceImage.getId())));
            return;
        }

        if (!choiceImage.validate() ) {
            addActionError(getText("error.entity.field.missing.required", Lists.newArrayList(getText("label.entity.image.path"))));
            return;
        }
    }
	@Override
	protected String executeImpl() throws Exception {
        logger.debug("DeleteQuestionChoiceImageAction.executeImpl: Trying to delete choice image: #0", Objects.toString(choiceImage) );

        /*
         * Deleting file from disk
         */
        try {
            deleteImageFile();
        } catch (IOException e) {
            logger.error("DeleteQuestionChoiceImageAction.executeImpl: Error when deleting files", e );
            return ERROR;
        }
        logger.debug("DeleteQuestionChoiceImageAction.executeImpl: deleting file from DB." );
        choiceImageService.delete(choiceImage);
        logger.debug("DeleteQuestionChoiceImageAction.executeImpl: Choice image deleted successfully!" );
        return null;
    }

    private void deleteImageFile() throws IOException {
        String path = choiceImage.getPath();

        //delete from working dir
        String realPath = ServletActionContext.getServletContext().getRealPath(path);
        logger.debug("DeleteQuestionChoiceImageAction.deleteImageFile: Deleting file [#0]...", realPath);
        Files.deleteIfExists(Paths.get(realPath));

        //delete from backup dir
        String relativePath = path.replace(configContext.getUploadBaseDir(),"");
        Path pathInBackupDir = Paths.get(configContext.getUploadBackUpDir(choiceImage.getChoice().getQuestion().getProject()), relativePath);

        logger.debug("DeleteQuestionChoiceImageAction.deleteImageFile: Deleting files from backup dir: #0", pathInBackupDir );
        Files.deleteIfExists(pathInBackupDir);
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
