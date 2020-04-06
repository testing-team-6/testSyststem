package cn.cstqb.exam.testmaker.actions.questionChoice;

import cn.cstqb.exam.testmaker.entities.QuestionChoiceImage;
import cn.cstqb.exam.testmaker.services.IQuestionChoiceImageService;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class SaveOrUpdateQuestionChoiceAction extends BaseQuestionChoiceAction {

	private List<String> imagePaths;
	private IQuestionChoiceImageService questionChoiceImageService;

	public SaveOrUpdateQuestionChoiceAction() {
		super();
		this.questionChoiceImageService = injector.getInstance(IQuestionChoiceImageService.class);
	}

	@Override
	public void validateInput() {
		super.validateInput();
		if (questionChoiceService.haveSameLabel(choice)) {
			addActionError(getText("error.questionChoice.duplicate.choiceLabel", Lists.newArrayList(choice.getChoiceLabel())));
			return;
		}

		if (!questionChoiceService.correctAnswerMatchType(choice)) {
			addActionError(getText("error.questionChoice.correctAnswer.not.match.questionType"));
			return;
		}
	}

	@Override
	protected String executeImpl() throws Exception {
        logger.debug("SaveOrUpdateQuestionChoiceAction.executeImpl: saving question choice {}", choice );
        questionChoiceService.createOrUpdate(choice);
		//if the choice id is null, then the last choice id is the new choice which was just created
//		if (choice.getId() == null) {
//			choice = questionChoiceService.findLastQuestionChoice();
//		}
//		for (String path : imagePaths) {
//			String objPath = convertPath(path);
//			copy(path, objPath);
//			saveImageInDB(path);
//		}
		return null;
	}

	private void copy(String source, String objPath) throws Exception {
		byte[] buffer = new byte[1024];
		int len;
		try (FileOutputStream fos = new FileOutputStream(objPath); FileInputStream fis = new FileInputStream(source)) {
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0 ,len);
			}
		} catch (Exception e) {
			logger.error("Fail to copy the file");
			e.printStackTrace();
			throw e;
		}
	}

	private void saveImageInDB(String path) {
		QuestionChoiceImage image = new QuestionChoiceImage();
		image.setChoice(choice);
		image.setPath(path);
		questionChoiceImageService.saveOrUpdate(image);
	}

	private String convertPath(String path) {
		String returnPath[] = path.split("/");
		return "\\" + choice.getQuestion().getProject().getName() + "\\"
				+ choice.getQuestion().getId() + "\\"
				+ choice.getId() + "\\"
				+ returnPath[returnPath.length - 1];
	}

	public List<String> getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(List<String> imagePaths) {
		this.imagePaths = imagePaths;
	}
}
