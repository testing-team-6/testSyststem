package cn.cstqb.exam.testmaker.entities;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao
 * Date: 2015/1/3
 * Time: 15:35
 */
@Entity
public class ReviewComment extends AbstractBaseEntity {
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isFinalReview;

    @ManyToOne
    @JoinColumn(name = "reviewer_id",nullable = false)
    private User reviewer;

    public ReviewComment(Question question, String content, User commentedBy) {
        this.question = question;
        this.content = content;
        this.reviewer = commentedBy;
    }

    public ReviewComment() {
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFinalReview() {
        return isFinalReview;
    }

    public void setFinalReview(boolean isFinalReview) {
        this.isFinalReview = isFinalReview;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User commentedBy) {
        this.reviewer = commentedBy;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReviewComment that = (ReviewComment) o;

        return Objects.equal(this.question, that.question) &&
                Objects.equal(this.content, that.content) &&
                Objects.equal(this.isFinalReview, that.isFinalReview) &&
                Objects.equal(this.reviewer, that.reviewer) &&
                Objects.equal(this.id, that.id) &&
                Objects.equal(this.createdOn, that.createdOn) &&
                Objects.equal(this.updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(question, content, isFinalReview, reviewer,
                id, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("id", id)
                .add("question", question)
                .add("content", content)
                .add("isFinalReview", isFinalReview)
                .add("reviewer", reviewer)
                .add("createdOn", createdOn)
                .add("updatedOn", updatedOn)
                .toString();
    }

    /**
     * Validate required fields for this entity
     *
     * @return
     */
    @Override
    public boolean validate() {
        return question!=null && !Strings.isNullOrEmpty(content)
                && reviewer!=null;
    }
}
