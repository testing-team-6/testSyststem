package cn.cstqb.exam.testmaker.actions.question;

import com.google.common.base.Strings;

public class QueryQuestionAction extends BaseQuestionAction {

	private String author;
	private String reviewer;
	private String qualityAdmin;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getQualityAdmin() {
		return qualityAdmin;
	}

	public void setQualityAdmin(String qualityAdmin) {
		this.qualityAdmin = qualityAdmin;
	}

	@Override
	public void validateInput() {
		if (Strings.isNullOrEmpty(author) || Strings.isNullOrEmpty(reviewer) || Strings.isNullOrEmpty(qualityAdmin)) {
			addActionError("sss");
		}
	}

	@Override
	protected String executeImpl() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String queryByAdmin() throws Exception {
		return null;
	}

	public String queryByAuthor() throws Exception {
		return null;
	}

	public String queryByReviewer() throws Exception {
		return null;
	}

}
